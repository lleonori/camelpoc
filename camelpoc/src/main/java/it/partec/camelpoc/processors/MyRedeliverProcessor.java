package it.partec.camelpoc.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

public class MyRedeliverProcessor implements Processor {

    public void process(Exchange exchange) {

        int count = exchange.getIn().getHeader(Exchange.REDELIVERY_COUNTER, Integer.class);
        System.out.println("count: " + count);

        exchange.getIn().setBody("{\"userId\":1,\"id\":3,\"title\":\"test3\",\"body\":\"et iusto sed quo iure\\\\nvoluptatem occaecati omnis eligendi aut ad\\\\nvoluptatem doloribus vel accusantium quis pariatur\\\\nmolestiae porro eius odio et labore et velit aut\"}");

    }
}