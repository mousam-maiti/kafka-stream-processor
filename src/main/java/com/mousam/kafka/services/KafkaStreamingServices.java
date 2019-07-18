package com.mousam.kafka.services;

import com.mousam.kafka.Application;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;
import org.apache.log4j.Logger;

import java.util.Properties;

public class KafkaStreamingServices implements Runnable, StreamProcessController{

    private static Logger logger = Logger.getLogger(Application.class);

    Properties properties;

    public KafkaStreamingServices(Properties properties){
        this.properties = properties;
    }
    @Override
    public void run() {
        Topology topology = new Topology();
        topology.addSource("SOURCE", Serdes.String().deserializer(), Serdes.String().deserializer(), properties.getProperty("kafka.topic.name"))
                .addProcessor("PROCESS", () ->
                        new KafkaProcessorSupplier().get(), "SOURCE");
        KafkaStreams streaming = new KafkaStreams(topology, getKafkaProperties());
        streaming.cleanUp();
        logger.info("Starting kafka streaming");
        streaming.start();
        //streaming.close();
    }

    @Override
    public Properties getKafkaProperties() {

        Properties props = new Properties();
        props.put(StreamsConfig.CLIENT_ID_CONFIG, properties.getProperty("kafka.client.id.config"));
        props.put("group.id", properties.getProperty("kafka.group.id"));
        props.put(StreamsConfig.APPLICATION_ID_CONFIG, properties.getProperty("kafka.application.id"));
        props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, properties.getProperty("kafka.bootstrap.servers"));
        props.put(StreamsConfig.NUM_STREAM_THREADS_CONFIG, properties.getProperty("kafka.num.streams.threads"));
        props.put(StreamsConfig.REPLICATION_FACTOR_CONFIG, properties.getProperty("kafka.replication.factor"));
        props.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
        props.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());

        return props;
    }
}
