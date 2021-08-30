package org.ab;

import ca.uhn.hl7v2.HL7Exception;
import org.apache.camel.LoggingLevel;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.hl7.HL7DataFormat;

public class Hl7PidToSqlRouteBuilder extends RouteBuilder {

    private static Hl7ToPidToSqlProcessor processor = new Hl7ToPidToSqlProcessor();

    @Override
    public void configure() {
        // Turn off validation because of phone informations throwing exceptions
        HL7DataFormat hl7 = new HL7DataFormat();
        hl7.setValidate(false);

        from("file:{{file.input}}?move={{file.archive}}/${file:name}")
//                .streamCaching()
                .onException(HL7Exception.class)
                    .handled(true)
                    .log(LoggingLevel.ERROR, "Error unmarshalling ${file:name} ${exception.message}")
                    .end()
                .log("Catch patient information")
                .unmarshal(hl7)
//                .validate(messageConforms())
                .log("Prepare data for sql insertion")
                .process(processor)
                .log("Insert data in sql table")
                .to("sqlComponent:{{sql.insertPatient}}");
    }

}
