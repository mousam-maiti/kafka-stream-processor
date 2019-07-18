package com.mousam.kafka.services;

import org.apache.kafka.streams.processor.Processor;
import org.apache.kafka.streams.processor.ProcessorSupplier;

public class KafkaProcessorSupplier implements ProcessorSupplier<String, String>{
    @Override
    public Processor<String, String> get() {
        return new KafkaProcessor();
    }
}
