package it.partec.camelpoc.routes;

import it.partec.camelpoc.aggregations.ArrayListAggregationStrategy;
import it.partec.camelpoc.dto.ContactDto;
import it.partec.camelpoc.dto.PostDto;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jackson.JacksonDataFormat;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class HttpRoute extends RouteBuilder {
    @Override
    public void configure() {
        from("file:contacts/filtering?noop=true&fileName=filteredContactsFileTrue.csv")
                .routeId("httpRoute")
                .unmarshal(new BindyCsvDataFormat(ContactDto.class))
                .split(body())
                .setHeader(Exchange.HTTP_QUERY, constant("GET"))
                .setHeader("id", simple("${body.id}"))
                .setBody(constant(null))
                .toD("http4://jsonplaceholder.typicode.com/posts/${header.id}")
                .unmarshal(new JacksonDataFormat(PostDto.class))
                .aggregate(body(), new ArrayListAggregationStrategy())
                .completionTimeout(10000)
                .marshal(new BindyCsvDataFormat(PostDto.class))
                .log("Test ${body}")
                .to("file:contacts/post?fileName=post.csv");
    }
}
