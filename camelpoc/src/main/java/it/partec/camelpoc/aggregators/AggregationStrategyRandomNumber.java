package it.partec.camelpoc.aggregators;

import org.apache.camel.Exchange;
import org.apache.camel.processor.aggregate.AggregationStrategy;

import java.util.ArrayList;

public class AggregationStrategyRandomNumber implements AggregationStrategy {
    //    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
//        if (oldExchange == null) {
//            return newExchange;
//        }
//
//        String oldBody = oldExchange.getIn().getBody(String.class);
//        String newBody = newExchange.getIn().getBody(String.class);
//        oldExchange.getIn().setBody(oldBody + "" + newBody);
//        return oldExchange;
//    }
    public Exchange aggregate(Exchange oldExchange, Exchange newExchange) {
        Object newBody = newExchange.getIn().getBody();
        ArrayList<Object> list = null;
        if (oldExchange == null) {
            list = new ArrayList<Object>();
            list.add(newBody);
            newExchange.getIn().setBody(list);
            return newExchange;
        } else {
            list = oldExchange.getIn().getBody(ArrayList.class);
            if (newBody.equals(46)) {
                throw new NullPointerException("The number is 46");
            } else {
                list.add(newBody);
                return oldExchange;
            }

        }
    }
}
