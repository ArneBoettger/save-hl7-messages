package archive;

import org.ab.Hl7MessageRouteBuilder;
import org.apache.camel.main.Main;

public class MainApp {

    public static void main(String... args) throws Exception {
        Main main = new Main();
        main.configure().addRoutesBuilder(new Hl7MessageRouteBuilder());
        main.run(args);
    }

}

