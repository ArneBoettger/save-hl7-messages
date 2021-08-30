package org.ab;

import org.apache.camel.builder.RouteBuilder;

public class HL7FileToSqlRouteBuilder extends RouteBuilder {

    private static Hl7ToStringToSqlProcessor processor = new Hl7ToStringToSqlProcessor();

    @Override
    public void configure() throws Exception {

        from("file:C:/messages/newMessages?move=C:/messages/archive/${file:name}")
                .log("Reading patient information from hl7 file and prepare sql insert information")
                .process(processor)
                .log("Send sql insert")
                .to("sqlComponent:{{sql.insertPatient}}");
    }
}
