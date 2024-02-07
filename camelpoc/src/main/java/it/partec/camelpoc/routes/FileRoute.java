package it.partec.camelpoc.routes;

import it.partec.camelpoc.aggregations.StringAggregationStrategy;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.dataformat.JsonLibrary;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class FileRoute extends RouteBuilder {
    @Override
    public void configure() {

        onException(Exception.class)
                .handled(true)
                .setHeader("CamelError", constant("CamelError"))
                .wireTap("direct:test3");

        from("file:recipient?noop=true")
                .routeId("fileRoute")
                .log("File: ${header.CamelFileName}")
                .unmarshal().json(JsonLibrary.Jackson, List.class)
                .split(body())
                .marshal().json(JsonLibrary.Jackson)
                .log("${body}")
                .aggregate(jsonpath("$.userId"), new StringAggregationStrategy())
                .completionSize(3)
                .log("${body}")
                .setHeader("recipientListHeader", body())
                .recipientList(header("recipientListHeader").tokenize(","));

        from("direct:test1")
                .log("Here1: ${body}");

        from("direct:test2")
                .log("Here2");

        from("direct:test3")
                .log("Here3");
    }
}