package com.nav.speak;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class ConsumerDemoWithShutDown {
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemoWithShutDown.class.getName());
    public static void main(String[] args) {
        var groupId = "Java app group";
        var topic = "demo_java";
        // create Consumer Properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","cluster.playground.cdkt.io:9092");
        properties.setProperty("security.protocol","SASL_SSL");
        properties.setProperty("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username=\"5KM6kyCX8k94WXgMQKPrTl\" password=\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiI1S002a3lDWDhrOTRXWGdNUUtQclRsIiwib3JnYW5pemF0aW9uSWQiOjc0NDYxLCJ1c2VySWQiOjg2NjI1LCJmb3JFeHBpcmF0aW9uQ2hlY2siOiIwN2M5OTExOC02YjIwLTQyOWYtYWI2Yi0xZjAzYmFlYjFhYTUifX0.tJvYH69A9zu3hAg2_ZzgQJIdBpXMJS_iO0z9ZeJatI4\";");
        properties.setProperty("sasl.mechanism","PLAIN");
        properties.setProperty("key.deserializer", StringDeserializer.class.getName());
        properties.setProperty("value.deserializer", StringDeserializer.class.getName());

        properties.setProperty("group.id", groupId);

        //none (existing group isnt there we fail) - must set consumer group b4 starting the consumer
        // earliest - read from the beginning of the topic
        // latest - only new message
        properties.setProperty("auto.offset.reset", "earliest");

        KafkaConsumer<String, String> consumer = new KafkaConsumer<>(properties);

        Thread mainThread = Thread.currentThread();
        Runtime.getRuntime().addShutdownHook(new Thread(() ->{
            System.out.println("Detected a shutdown. Let's exit by calling consumer.wakeup()...");
            consumer.wakeup();
            try {
                mainThread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }));

        try {
            //subscribe to a topic
            consumer.subscribe(Arrays.asList(topic));
            //poll for data
            while (true){
                //System.out.println();("Polling...");
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
                records.iterator()
                        .forEachRemaining(r ->  System.out.println(
                                "\nkey " + r.key() + " val " + r.value() + "\n" +
                                        "Partition " + r.partition() + " offser " + r.offset() + "\n"
                        ));

            }
        } catch (WakeupException e) {
            System.out.println("Consumer is shutting down");
        } finally {
            consumer.close(); // also commit offset
            System.out.println("The Consumer is now shutting down");
        }



        /*
            1. Join the group
            2. Discovers the partitions
            3. No committed offset, so resets offset to 0 / if committed offset found reads from that offset
            4. Start reading from offset 0

            Receives all from one partition, then another...
             */

    }


}
/*
If we start two Consumer (in IntelliJ > Run configuration modify to run multiple instances) one will have say
Adding newly assigned partitions: demo_java-0, demo_java-1
Other
Adding newly assigned partitions: demo_java-2

Consumer Groups join or leave the group => Partition Rebalance
1. Eager Rebalance - Stop the World. Each consumer stops, give up their membership of partitions. Rejoin and gets new partition (may not get same partition Id)
2. Cooperative Rebalance (Incremental Rebalnce) - Assign small subset of partition from one consumer to another. Several iterations to reach stable assignment
partition.assignment.strategy =
Eager:
   - RangeAssignor: assigns partitions on per-topic basic (can lead to imbalance)
   - Round-robin: assign partition across all topics in round robin fashion, optimal balance
   - StickyAssignor: balance like RoundRobin, and then minimizes partition movements when consumer join / leave the group in order to minimize movement
CooperativeStickAssignor
Default = [RangeAssignor, CooperativeAssignor]
Static Group Membership => group.instance.id makes consumer static. If leaves and joins in session.timeout.ms - no rebalanceConsumerDemoWithShutDown
 */