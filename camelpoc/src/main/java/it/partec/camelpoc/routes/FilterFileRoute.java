package it.partec.camelpoc.routes;

import it.partec.camelpoc.dto.ContactDto;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.dataformat.bindy.csv.BindyCsvDataFormat;
import org.springframework.stereotype.Component;

@Component
public class FilterFileRoute extends RouteBuilder {
    // esercitazione leggere da un csv, processare i record, filtrare e interrogare un end point
    @Override
    public void configure() {
        from("file:contacts/outgoing?noop=true")
                .routeId("filterFileRoute")
                .log("File: ${header.CamelFileName}")
                .unmarshal(new BindyCsvDataFormat(ContactDto.class))
                .split(body())
                .choice()
                .when().simple("${body.flagContact} == true")
                .marshal(new BindyCsvDataFormat(ContactDto.class))
                .to("file:contacts/filtering?fileName=filteredContactsFileTrue.csv&fileExist=Append")
                .otherwise()
                .marshal(new BindyCsvDataFormat(ContactDto.class))
                .to("file:contacts/filtering?fileName=filteredContactsFileFalse.csv&fileExist=Append");
    }
}