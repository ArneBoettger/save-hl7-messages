package org.ab;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class MainApp {

    public static void main(String[] args) {
        try {
            ApplicationContext springCtx = new ClassPathXmlApplicationContext("database-context.xml");
            CamelContext context = springCtx.getBean("patientsContext", CamelContext.class);

//            context.getStreamCachingStrategy().setSpoolDirectory("/tmp/cachedir");
//            context.getStreamCachingStrategy().setSpoolThreshold(64 * 1024);
//            context.getStreamCachingStrategy().setBufferSize(16 * 1024);
//            context.setStreamCaching(true);

            for (int i = 0; i < getExecutionDuration(); i++) {
                context.start();
                Thread.sleep(1000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            System.exit(0);
        }
    }

    private static int getExecutionDuration() throws IOException {
        int seconds = 0;
        try (InputStream input = MainApp.class.getClassLoader().getResourceAsStream("config.properties")) {
            Properties prop = new Properties();
            prop.load(input);
            seconds = Integer.valueOf(prop.getProperty("execution.time"));
        }
        return seconds;
    }
}
