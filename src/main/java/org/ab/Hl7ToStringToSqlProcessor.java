package org.ab;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.io.File;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.Map;

public class Hl7ToStringToSqlProcessor implements Processor {

    @Override
    public void process(Exchange exchange) throws Exception {
        String msg = new String(Files.readAllBytes(exchange.getIn().getBody(File.class).toPath()));
        String[] msgInf = msg.split("\\|");
        String pid = msgInf[25];
        String name = msgInf[27].split("\\^")[1];
        String surname = msgInf[28];
        String birthdate = msgInf[29].substring(0, 4) + "-" + msgInf[29].substring(4, 6) + "-" + msgInf[29].substring(6);

        Map<String, String> answer = new HashMap<>();
        answer.put("pid", pid);
        answer.put("surname", surname);
        answer.put("name", name);
        answer.put("birthdate", birthdate);
        exchange.getIn().setBody(answer);
    }
}
