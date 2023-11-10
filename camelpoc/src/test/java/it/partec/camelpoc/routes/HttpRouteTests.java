package it.partec.camelpoc.routes;

import org.apache.camel.ConsumerTemplate;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;

import static org.junit.Assert.assertTrue;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest(classes = FileRoute.class)
public class HttpRouteTests {
    @Autowired
    private ProducerTemplate producerTemplate;

    @Autowired
    private ConsumerTemplate consumerTemplate;

    @Test
    public void HttpRouteTestsCase() {
        producerTemplate.sendBody("file:contacts/outgoing?fileName=processedContactsFile.csv");

        File file = new File("out/processedContactsFile.csv");
        assertTrue("out/processedContactsFile.csv", file.exists());
    }
}
