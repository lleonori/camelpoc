package it.partec.camelpoc.routes;

import it.partec.camelpoc.aggregators.AggregationStrategyRandomNumber;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class SchedulerRoute extends RouteBuilder {
    @Override
    public void configure() {
//        Certamente! Ecco un esercizio che puoi utilizzare per praticare l'Aggregator Pattern con Apache Camel:
//
//        Scenario: Sistema di Monitoraggio Sensori
//
//        Immagina di dover implementare un sistema di monitoraggio dei sensori che inviano dati in tempo reale. Ogni sensore invia dati periodicamente,
//        e il sistema deve aggregare i dati provenienti da più sensori in un'unica risorsa.
//
//        Creazione di un Endpoint per i Sensori:
//
//        Crea un endpoint Camel che simuli l'arrivo periodico di dati da parte di diversi sensori (puoi utilizzare un generatore casuale per i dati).
//        Implementazione dell'Aggregator:
//
//        Utilizza l'Aggregator Pattern per raccogliere i dati provenienti dai vari sensori in un'unica struttura dati.
//                L'aggregazione dovrebbe avvenire in base a un criterio temporale (ad esempio, aggrega i dati ricevuti nell'ultimo minuto).
//        Invio dei Dati Aggregati:
//
//        Dopo l'aggregazione, invia i dati aggregati a un endpoint di destinazione (può essere un log, un database, o un endpoint fittizio).
//        Gestione degli Errori:
//
//        Gestisci eventuali errori che possono verificarsi durante il processo di aggregazione o invio dei dati.
//                Configurazione del Timer:
//
//        Utilizza un timer per simulare l'arrivo periodico dei dati dai sensori.
//        Questa esercitazione ti darà l'opportunità di applicare l'Aggregator Pattern in un contesto pratico e acquisire familiarità con l'implementazione di soluzioni di integrazione usando Apache Camel. Puoi personalizzare i dettagli dell'esercizio in base alle tue esigenze e aggiungere ulteriori complessità se lo desideri. Buona pratica!

        errorHandler(deadLetterChannel("direct:deadLetterChannelRoute")
                .disableRedelivery());

        from("scheduler://foo?delay=1000")
                .setBody().simple("${random(100)}")
                .setHeader("randomicNumber", bodyAs(Integer.class))
                .aggregate(constant(true), new AggregationStrategyRandomNumber())
                .completionInterval(3000)
                .log("${body}")
                .to("mock:testRoute");
    }
}
