package com.ifi.omct17.Classes.Common;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FJLogger {


    public static class MyLogger{

        public static Logger GetLogger()
        {
//            File file = new File("C:\\logs\\log4j2.xml");
//
//            LoggerContext context = (LoggerContext) LogManager.getContext(false);
//            context.setConfigLocation(file.toURI());
            return LogManager.getLogger();
        }
    }

    public static void Error(String message){
        MyLogger.GetLogger().error(message);
    }

    public static void Info(String message){
        MyLogger.GetLogger().info(message);
    }
}
