package it.partec.camelpoc.routes;

import it.partec.camelpoc.OrderRouter;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class FileRoute extends RouteBuilder {

    @Override
    public void configure() {
        from("file:orders?noop=true")
                .routeId("fileRoute")
                .log("File: ${header.CamelFileName}")
                .log("json body: ${body}")
                .split().tokenizeXML("regola")
//                .split(xpath("/configurazione/regole/regola1"))
                .recipientList().method(OrderRouter.class, "routeOrder");

        from("direct:onlineOrder")
                .log("Processing online order: ${body}");

        from("direct:phoneOrder")
                .log("Processing phone order: ${body}");

        from("direct:vipOrder")
                .log("Processing VIP order: ${body}");

        from("direct:errorOrder")
                .log("Error: Unrecognized channel - ${header.channel}. Order: ${body}");
    }
}
