package org.ab;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        try {
            ApplicationContext springCtx = new ClassPathXmlApplicationContext("database-context.xml");
            CamelContext context = springCtx.getBean("patientsContext", CamelContext.class);

//            context.getStreamCachingStrategy().setSpoolDirectory("/tmp/cachedir");
//            context.getStreamCachingStrategy().setSpoolThreshold(64 * 1024);
//            context.getStreamCachingStrategy().setBufferSize(16 * 1024);
//            context.setStreamCaching(true);

            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
