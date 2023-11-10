package it.partec.camelpoc.aggregations;

import it.partec.camelpoc.dto.ContactDto;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

public class HttpAggregationStrategy implements AggregationStrategy {

    public static final String HEADER = "TITLE_PRINT";

    public Exchange aggregate(Exchange original, Exchange resource) {
        ContactDto originalBody = original.getIn().getBody(ContactDto.class);
        String resourceResponse = resource.getIn().getBody(String.class);
            original.getIn().setHeader(HEADER, resourceResponse);
        return original;
    }
}