package com.mousam.kafka;

import com.mousam.kafka.core.Runner;
import com.mousam.kafka.core.Settings;
import com.mousam.kafka.services.KafkaStreamingServices;
import org.apache.log4j.Logger;

import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Application implements Settings {
    private static Logger logger = Logger.getLogger(Application.class);

    Properties properties;

    public static void main(String[] args) throws Exception {
        int returnCode = Runner.run(new Application(), args);
        if(returnCode==1){
            logger.error("Application failed !!");
        }
    }

    @Override
    public int run(Properties properties, String[] args) throws InterruptedException {
        this.properties = properties;
        ExecutorService streamingPool = Executors.newCachedThreadPool();
        streamingPool.submit(new KafkaStreamingServices(properties));
        streamingPool.shutdown();
        streamingPool.awaitTermination(10, TimeUnit.SECONDS);
        return 0;
    }

    @Override
    public Properties getAppSettings() {
        return properties ;
    }

}
