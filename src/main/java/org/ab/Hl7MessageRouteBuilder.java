package org.ab;

import ca.uhn.hl7v2.HL7Exception;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;

public class Hl7MessageRouteBuilder extends RouteBuilder {

    private static Hl7ToSqlProcessor processor = new Hl7ToSqlProcessor();

    @Override
    public void configure() {

        from("file:{{input}}?move={{file-archive}}/${file:name")
                .onException(HL7Exception.class)
                    .handled(true)
                    .log(LoggingLevel.ERROR, "Error unmarshalling ${file:name} ${exception.message}")
                    .end()
                .log("Catch patient information")
                // unmarshall file to hl7 message
                .unmarshal().hl7()
                .log("Prepare data for sql insertion")
                // map HLV2 patient to patient information map
                .process(processor)
                .log("Insert data in sql table")
                .to("sqlComponent:{{sql.insertPatient}}");
    }

}
