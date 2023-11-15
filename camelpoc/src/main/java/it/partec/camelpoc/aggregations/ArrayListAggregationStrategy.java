package it.partec.camelpoc.aggregations;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class ArrayListAggregationStrategy implements AggregationStrategy {

    public Exchange aggregate(Exchange original, Exchange resource) {
        Object newBody = resource.getIn().getBody();
        ArrayList<Object> list;
        if (original == null) {
            list = new ArrayList<>();
            list.add(newBody);
            resource.getIn().setBody(list);
            return resource;
        } else {
            list = original.getIn().getBody(ArrayList.class);
            list.add(newBody);
            return original;
        }
    }
}