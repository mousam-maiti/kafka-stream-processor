package com.mousam.kafka.services;

import org.apache.kafka.streams.processor.AbstractProcessor;
import org.apache.kafka.streams.processor.TaskId;
import org.apache.log4j.Logger;

public class KafkaProcessor extends AbstractProcessor<String, String> {
    private static Logger logger = Logger.getLogger(KafkaProcessor.class);
    TaskId task;
    @Override
    public void process(String key, String value) {
        this.task = this.context().taskId();
        logger.info("key : " + key + " , value : "+value);
        this.context().commit();
    }
}
