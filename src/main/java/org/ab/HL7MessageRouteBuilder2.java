package org.ab;

import org.apache.camel.builder.RouteBuilder;

public class HL7MessageRouteBuilder2 extends RouteBuilder {

    private static Hl7ToSqlProcessor2 processor = new Hl7ToSqlProcessor2();

    @Override
    public void configure() throws Exception {

        from("file:C:/messages/newMessages?move=C:/messages/archive/${file:name}")
                .process(processor)
                .to("sqlComponent:{{sql.insertPatient}}");
    }
}
