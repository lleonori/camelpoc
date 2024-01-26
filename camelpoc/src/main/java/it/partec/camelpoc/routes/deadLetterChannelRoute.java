package it.partec.camelpoc.routes;

import it.partec.camelpoc.aggregators.AggregationStrategyRandomNumber;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class deadLetterChannelRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("direct:deadLetterChannelRoute")
                .log("Dead-Letter ${body}");
    }
}
