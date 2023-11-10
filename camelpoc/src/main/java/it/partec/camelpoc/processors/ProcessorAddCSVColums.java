package it.partec.camelpoc.processors;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;

import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ProcessorAddCSVColums implements Processor {
    @Override
    public void process(Exchange exchange) {
        String separator = "\n";
        Random random = new Random();
        String inputMessage = exchange.getIn().getBody(String.class);

        AtomicReference<Long> counter = new AtomicReference<>(1L);

        String processedLines = Stream.of(inputMessage.split(separator))
                .map(l -> counter.getAndUpdate( p -> p + 1 ).toString() + ";" + random.nextBoolean() + ";" + l)
                .collect(Collectors.joining(separator));

        exchange.getIn().setBody(processedLines);
    }
}