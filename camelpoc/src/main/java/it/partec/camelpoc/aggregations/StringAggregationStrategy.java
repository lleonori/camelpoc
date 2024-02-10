package it.partec.camelpoc.aggregations;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import it.partec.camelpoc.dto.RecipientDto;
import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringAggregationStrategy implements AggregationStrategy {
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Pattern pattern = Pattern.compile("title=(\\w+)");

        if (oldExchange == null) {
            String newBody = newExchange.getIn().getBody(String.class);
            Matcher matcher = pattern.matcher(newBody);
            if (matcher.find()) {
                String title = matcher.group(1);
                newExchange.getIn().setBody("direct:" + title);
            } else {
                newExchange.getIn().setBody("direct:unknown");
            }
            return newExchange;
        }

        String title;
        String oldBody = oldExchange.getIn().getBody(String.class);
        String newBody = newExchange.getIn().getBody(String.class);
        Matcher matcherNewBody = pattern.matcher(newBody);
        if (matcherNewBody.find()) {
            title = oldBody + ",direct:" + matcherNewBody.group(1);
        } else {
            title = "direct:unknown";
        }
        oldExchange.getIn().setBody(title);
        return oldExchange;
    }
//    se lavoro con un ogetto Java
//    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
//        ObjectMapper objectMapper = new ObjectMapper();
//
//        if (oldExchange == null) {
//            try {
//                RecipientDto newBody = objectMapper.readValue(newExchange.getIn().getBody(String.class), RecipientDto.class);
//                newExchange.getIn().setBody("direct:" + newBody.getTitle());
//            } catch (JsonProcessingException e) {
//                throw new RuntimeException(e);
//            }
//            return newExchange;
//        }
//
//        try {
//            String oldBody = oldExchange.getIn().getBody(String.class);
//            RecipientDto newBody = objectMapper.readValue(newExchange.getIn().getBody(String.class), RecipientDto.class);
//            oldExchange.getIn().setBody(oldBody + "," + "direct:" + newBody.getTitle());
//            return oldExchange;
//
//        } catch (IOException e) {
//            throw new RuntimeException(e);
//        }
//    }
}
