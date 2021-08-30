package archive;

import org.ab.Hl7PidToSqlRouteBuilder;
import org.apache.camel.main.Main;

public class MainApp {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new Hl7PidToSqlRouteBuilder());
        main.run(args);
    }

}

