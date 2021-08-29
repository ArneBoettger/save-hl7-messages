package org.ab;

import org.apache.camel.CamelContext;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MainApp {

    public static void main(String[] args) {
        try {
            ApplicationContext springCtx = new ClassPathXmlApplicationContext("database-context.xml");
            CamelContext context = springCtx.getBean("patientsContext", CamelContext.class);
            context.start();
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
