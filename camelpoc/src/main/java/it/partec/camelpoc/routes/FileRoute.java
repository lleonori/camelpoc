package it.partec.camelpoc.routes;

import it.partec.camelpoc.aggregations.StringAggregationStrategy;
import it.partec.camelpoc.processors.MyRedeliverProcessor;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() {

//        errorHandler(deadLetterChannel("direct:error").maximumRedeliveries(5)
//                .onRedelivery(new MyRedeliverProcessor())
//                .redeliveryDelay(0L));

        from("file:recipient?noop=true")
                .routeId("fileRoute")
                .unmarshal().json(JsonLibrary.Jackson, List.class)
                .split(body())
                // .marshal().json(JsonLibrary.Jackson)
                .aggregate(jsonpath("$.userId"), new StringAggregationStrategy())
                .completionSize(3)
                .log("${body}")
                .setHeader("recipientListHeader", body())
                .recipientList(header("recipientListHeader").tokenize(","));

        from("direct:test1")
                .log("Here1");

        from("direct:test2")
                .log("Here2");

        from("direct:test3")
                .log("Here3");

        from("direct:error")
                .log("Here4");
    }

}