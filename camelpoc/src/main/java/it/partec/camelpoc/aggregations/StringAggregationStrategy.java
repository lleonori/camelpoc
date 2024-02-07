package it.partec.camelpoc.aggregations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.partec.camelpoc.dto.RecipientDto;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.io.IOException;

public class StringAggregationStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        ObjectMapper objectMapper = new ObjectMapper();

        if (oldExchange == null) {
            try {
                RecipientDto newBody = objectMapper.readValue(newExchange.getIn().getBody(String.class), RecipientDto.class);
                newExchange.getIn().setBody("direct:" + newBody.getTitle());
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
            return newExchange;
        }

        try {
            String oldBody = oldExchange.getIn().getBody(String.class);
            RecipientDto newBody = objectMapper.readValue(newExchange.getIn().getBody(String.class), RecipientDto.class);
            oldExchange.getIn().setBody(oldBody + "," + "direct:" + newBody.getTitle());
            return oldExchange;

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
