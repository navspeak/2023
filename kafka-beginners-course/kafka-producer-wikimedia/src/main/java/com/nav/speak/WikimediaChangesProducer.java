package com.nav.speak;

import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class WikimediaChangesProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers","cluster.playground.cdkt.io:9092");
        properties.setProperty("security.protocol","SASL_SSL");
        properties.setProperty("sasl.jaas.config","org.apache.kafka.common.security.plain.PlainLoginModule required username=\"5KM6kyCX8k94WXgMQKPrTl\" password=\"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJodHRwczovL2F1dGguY29uZHVrdG9yLmlvIiwic291cmNlQXBwbGljYXRpb24iOiJhZG1pbiIsInVzZXJNYWlsIjpudWxsLCJwYXlsb2FkIjp7InZhbGlkRm9yVXNlcm5hbWUiOiI1S002a3lDWDhrOTRXWGdNUUtQclRsIiwib3JnYW5pemF0aW9uSWQiOjc0NDYxLCJ1c2VySWQiOjg2NjI1LCJmb3JFeHBpcmF0aW9uQ2hlY2siOiIwN2M5OTExOC02YjIwLTQyOWYtYWI2Yi0xZjAzYmFlYjFhYTUifX0.tJvYH69A9zu3hAg2_ZzgQJIdBpXMJS_iO0z9ZeJatI4\";");
        properties.setProperty("sasl.mechanism","PLAIN");
        // set Producer properties
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
    }
}
