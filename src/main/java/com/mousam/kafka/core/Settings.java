package com.mousam.kafka.core;

import java.util.Properties;

public interface Settings extends ApplicationConfigurations{

    int run(Properties properties, String [] args) throws Exception;

}
