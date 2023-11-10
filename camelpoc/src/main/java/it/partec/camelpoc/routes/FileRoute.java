package it.partec.camelpoc.routes;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("file:contacts/incoming?noop=true")
                .routeId("fileRoute")
                .log("File: ${header.CamelFileName}")
                .process(new it.partec.camelpoc.processors.ProcessorAddCSVColums())
                .to("file:contacts/outgoing?fileName=processedContactsFile.csv");
    }
}
