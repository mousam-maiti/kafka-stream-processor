package com.mousam.kafka.core;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class Runner {

    public static boolean DEBUG = false;

    public static int run(Settings settings, String[] args)
            throws Exception{
        return run(settings.getAppSettings(), settings, args);
    }

    public static int run(Properties properties , Settings settings, String[] args)
            throws Exception{
        Properties appSettings = new Properties();
        try
        {
            String configFileName = "src/main/resources/config.properties";
            if (args.length > 0) {
                configFileName = args[0];
            }
            FileInputStream fis = new FileInputStream(configFileName); //put config properties file to buffer
            appSettings.load(fis);
            DEBUG = Boolean.parseBoolean((String)appSettings.get("DEBUG"));
            fis.close();
            if(DEBUG) System.out.println("Settings file successfuly loaded");
        }
        catch(IOException e)
        {
            System.out.println("Could not load settings file.");
            System.out.println(e.getMessage());
        }
        return settings.run(appSettings, args);
    }
}
