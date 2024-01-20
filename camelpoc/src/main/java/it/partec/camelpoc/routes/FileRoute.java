package it.partec.camelpoc.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() {
        onException(FileNotFoundException.class)
                .to("file:log")
                .handled(true)
                .maximumRedeliveries(3);

//        from("file:contacts/incoming?noop=true")
//                .choice()
//                .when(header("CamelFileName").endsWith(".json")).to("seda:b")
//                .when(header("CamelFileName").endsWith(".csv")).to("seda:c")
//                .otherwise().to("seda:d");
//
//        from("seda:b")
//                .log("File: ${header.CamelFileName}");
//
//        from("seda:c")
//                .log("File: ${header.CamelFileName}");
//
//        from("seda:d")
//                .log("File: ${header.CamelFileName}");

        from("file:contacts/incoming?noop=true")
                .routeId("fileRoute")
                .log("File: ${header.CamelFileName}")
                .doTry()
                .process(new it.partec.camelpoc.processors.ProcessorAddCSVColums())
                .doCatch(FileNotFoundException.class)
                .doFinally()
                .to("file:contacts/outgoing?fileName=processedContactsFile.csv")
                .end();
    }
}
