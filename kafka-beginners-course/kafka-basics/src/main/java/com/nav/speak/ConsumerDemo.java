package com.nav.speak;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
/*
offsets are committed at regular intervals
Enables at-least once reading scenario (by default)
Offsets committed by .poll or when auto.commit.interval.ms has elapsed (and enable.auto.commit=true)
NOTE:
Make sure messages are all successfully processed b4 you call poll() again Else no at-least once reading scenario
Rarely:
  enable.auto.commit=false + most processing happen in separate thread and call commitSync() or commitAsync() with correct
  offsets manually
 */
public class ConsumerDemo {
    private static final Logger log = LoggerFactory.getLogger(ConsumerDemo.class.getName());
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
        //consumer.commitAsync(); // called under hood after elasped time once poll started
        //subscribe to a topic
        consumer.subscribe(Arrays.asList(topic));

        //poll for data
        while (true){
            log.info("Polling...");
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(1000));
            records.iterator()
                    .forEachRemaining(r ->  log.info(
                            "\nkey " + r.key() + " val " + r.value() + "\n" +
                            "Partition " + r.partition() + " offser " + r.offset() + "\n"
                    ));
            /*
            1. Join the group
            2. Discovers the partitions
            3. No committed offset, so resets offset to 0 / if committed offset found reads from that offset
            4. Start reading from offset 0

            Receives all from one partition, then another...
             */



        }



    }
}
