package it.partec.camelpoc.routes;

import org.apache.camel.CamelContext;
import org.apache.camel.EndpointInject;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.builder.AdviceWithRouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.camel.test.spring.UseAdviceWith;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;

import java.io.IOException;

@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@UseAdviceWith
public class FileRouteTests {
    private static final String MOCK_RESULT_TEST1 = "mock:test1";
    private static final String MOCK_RESULT_TEST2 = "mock:test2";
    private static final String MOCK_RESULT_TEST3 = "mock:test3";
    @Autowired
    private CamelContext context;
    @EndpointInject(uri = MOCK_RESULT_TEST1)
    private MockEndpoint resultEndpoint1;
    @EndpointInject(uri = MOCK_RESULT_TEST2)
    private MockEndpoint resultEndpoint2;
    @EndpointInject(uri = MOCK_RESULT_TEST3)
    private MockEndpoint resultEndpoint3;
    @Autowired
    private ProducerTemplate producerTemplate;
    @Value("classpath:recipient.json")
    Resource recipient;

    @Before
    public void setUp() throws Exception {
        context
                .getRouteDefinition("fileRoute")
                .adviceWith(context, new AdviceWithRouteBuilder() {
                    @Override
                    public void configure() {
                        // replica il mio from con direct:file
                        replaceFromWith("direct:file");

                        // intercetta tutto quello che arriva sulla rotta direct:test1 e mandalo su mock:test1
                        interceptSendToEndpoint("direct:test1")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_TEST1);

                        interceptSendToEndpoint("direct:test2")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_TEST2);

                        interceptSendToEndpoint("direct:test3")
                                .skipSendToOriginalEndpoint()
                                .to(MOCK_RESULT_TEST3);
                    }
                });

        context.start();
    }

    @After
    public void tearDown() throws Exception {
        context.stop();
    }

    @Test
    public void testMatchBody() throws InterruptedException {
        // verico che il json inviato dal template sia uguale a [{"id":5,"userId":12,"title":false,"body":"Test Order"}]
        resultEndpoint1.message(0).body().isEqualTo("[{\"id\":5,\"userId\":12,\"title\":false,\"body\":\"Test Order\"}]");
        // invio il json [{"id":5,"userId":12,"title":false,"body":"Test Order"}] sulla rotta direct:test1
        producerTemplate.sendBody("direct:test1", "[{\"id\":5,\"userId\":12,\"title\":false,\"body\":\"Test Order\"}]");
        // verifico l'assertion
        resultEndpoint1.assertIsSatisfied();
    }

    @Test
    public void testExecption() throws InterruptedException, IOException {
        String recipientJson = new String(recipient.getInputStream().readAllBytes());

        producerTemplate.sendBody("direct:file", recipientJson);

        Thread.sleep(2);

        resultEndpoint3.expectedHeaderReceived("CamelError", "CamelError");
        resultEndpoint3.assertIsSatisfied();
    }
}