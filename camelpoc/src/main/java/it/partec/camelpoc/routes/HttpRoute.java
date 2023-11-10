package it.partec.camelpoc.routes;

import it.partec.camelpoc.dto.ContactDto;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
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
                .setBody(constant(null))
                .toD("http4://jsonplaceholder.typicode.com/posts/1")
                .log("${body}");
    }
}
