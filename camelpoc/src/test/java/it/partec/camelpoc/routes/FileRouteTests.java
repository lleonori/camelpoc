package it.partec.camelpoc.routes;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = FileRoute.class)
public class FileRouteTests {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Test
    public void FileRouteTestCase() {
        producerTemplate.sendBody("file:contacts/outgoing?fileName=processedContactsFile.csv");

        String formatted = consumerTemplate.receiveBody(
                "file:contacts/incoming?noop=true",
                String.class
        );

        assertEquals("3.14", formatted);
    }
}
