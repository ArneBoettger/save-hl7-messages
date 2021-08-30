package org.ab;

import ca.uhn.hl7v2.HL7Exception;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;

public class Hl7MessageRouteBuilder extends RouteBuilder {

    private static Hl7ToSqlProcessor processor = new Hl7ToSqlProcessor();

    @Override
    public void configure() {
        // Turn off validation because of problematic phone information
        HL7DataFormat hl7 = new HL7DataFormat();
        hl7.setValidate(false);

        from("file:C:/messages/newMessages?move=C:/messages/archive/${file:name}")
//                .streamCaching()
                .onException(HL7Exception.class)
                    .handled(true)
                    .log(LoggingLevel.ERROR, "Error unmarshalling ${file:name} ${exception.message}")
                    .end()
                .log("Catch patient information")
                // unmarshall file to hl7 message
                .unmarshal(hl7)
//                .validate(messageConforms())
                .log("Prepare data for sql insertion")
                // map HLV2 patient to patient information map
                .process(processor)
                .log("Insert data in sql table")
                .to("sqlComponent:{{sql.insertPatient}}");
    }

}
