package com.nav.speak;

import org.apache.kafka.clients.producer.*;
import org.apache.kafka.common.serialization.StringSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.concurrent.TimeUnit;

public class ProducerDemoKeys {
    private static final Logger log = LoggerFactory.getLogger(ProducerDemoKeys.class.getName());
    private static void sleep(int timeout) {
        try {
                TimeUnit.MILLISECONDS.sleep(timeout);
        } catch (InterruptedException e) {
                throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
       // create Producer Properties
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","cluster.playground.cdkt.io:9092");
        properties.setProperty("security.protocol","SASL_SSL");
        properties.setProperty("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username=\"5KM6kyCX8k94WXgMQKPrTl\" password=\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiI1S002a3lDWDhrOTRXWGdNUUtQclRsIiwib3JnYW5pemF0aW9uSWQiOjc0NDYxLCJ1c2VySWQiOjg2NjI1LCJmb3JFeHBpcmF0aW9uQ2hlY2siOiIwN2M5OTExOC02YjIwLTQyOWYtYWI2Yi0xZjAzYmFlYjFhYTUifX0.tJvYH69A9zu3hAg2_ZzgQJIdBpXMJS_iO0z9ZeJatI4\";");
        properties.setProperty("sasl.mechanism","PLAIN");
       // set Producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        properties.setProperty("batch.size", "400");
        properties.setProperty("partitioner.class", RoundRobinPartitioner.class.getName()); // inefficient

       // create Producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // create producer record
       for (int i = 0; i <= 2; i++) {
            for (int j=0; j<10;j++) {
                // partionId is same becasue of sticky parttioner - for batching (default)
                String topic = "demo_java";
                String key = "id_"+j;
                String val = "hello world "+j;
                ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic,key, val);
                // send data
                producer.send(producerRecord, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata metadata, Exception e) {
                        if (e == null) {
                            log.info(
                                    "Key: " + key + "\t" +
                                    "Partition: " + metadata.partition()); // same keu => same partition
                        } else {
                            log.error("Error while producing");
                        }
                    }
                });
            }
            sleep(500);
        }


       // flush & close producer
        producer.flush(); // synchronous - blocks till all sent
        producer.close(); // actually close implicitly calls flush as well
        // Go to console and create topic with 3 partition
        // run this producer
        //kafka-console-consumer.sh --consumer.config playground.config --bootstrap-server cluster.playground.cdkt.io:9092 --topic demo_java
    }
}
