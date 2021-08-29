package org.ab;

import ca.uhn.hl7v2.model.v24.message.ORU_R01;
import ca.uhn.hl7v2.model.v24.segment.PID;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Hl7ToSqlProcessor implements Processor {

    public void process(Exchange exchange) throws Exception {
        ORU_R01 msg = exchange.getIn().getBody(ORU_R01.class);
        final PID pid = msg.getPATIENT_RESULT().getPATIENT().getPID();
        String surname = pid.getPatientName()[0].getFamilyName().getFn1_Surname().getValue();
        String name = pid.getPatientName()[0].getGivenName().getValue();
        String patientId = msg.getPATIENT_RESULT().getPATIENT().getPID().getPatientID().getCx1_ID().getValue();
        Date bd = pid.getDateTimeOfBirth().getTimeOfAnEvent().getValueAsDate();
        String birthdate = new SimpleDateFormat("uuuu-MM-dd").format(bd);

        Map<String, String> answer = new HashMap<>();
        answer.put("pid", patientId);
        answer.put("surname", surname);
        answer.put("name", name);
        answer.put("birthdate", birthdate);
        exchange.getIn().setBody(answer);

//        ORU_R01 msg = exchange.getIn().getBody(ORU_R01.class);
//        final PID pid = msg.getPATIENT_RESULT().getPATIENT().getPID();
//        String surname = pid.getPatientName()[0].getFamilyName().getFn1_Surname().getValue();
//        String name = pid.getPatientName()[0].getGivenName().getValue();
//        String patientId = msg.getPATIENT_RESULT().getPATIENT().getPID().getPatientID().getCx1_ID().getValue();
//        Date bd = pid.getDateTimeOfBirth().getTimeOfAnEvent().getValueAsDate();
//        String birthdate = new SimpleDateFormat("uuuu-MM-dd").format(bd);
//
//        String query = String.format("insert into patients (pid, surname, name, birthdate) "
//                + "values ('%s', '%s', '%s', '%s')", patientId, surname, name, birthdate);
//        exchange.getIn().setBody(query);
    }
}
