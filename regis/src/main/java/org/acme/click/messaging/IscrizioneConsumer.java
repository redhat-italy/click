package org.acme.click.messaging;

import javax.enterprise.context.ApplicationScoped;

import org.acme.click.model.Iscrizione;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.reactive.messaging.annotations.Blocking;

@ApplicationScoped
public class IscrizioneConsumer {

    private static final Logger LOG = Logger.getLogger("REGIS");

    @Incoming("subscriptions")
    @Blocking
    public void consume(Record<String, String> record) {
        Iscrizione iscrizione = new Iscrizione();
        iscrizione.codiceFiscale = record.key();
        iscrizione.codiceProtocollo = record.value();
        iscrizione.tipo = "reactive";
        iscrizione.errore = "";
        iscrizione.premio = "";
        Iscrizione result = Iscrizione.safetySave(iscrizione);
        if(result == null){
            LOG.info("[Iscrizione Consumer] iscrizione con codice protocollo: " + record.value() + " scartata.");
        }else{
            LOG.info("[Iscrizione Consumer] creata nuova iscrizione con codice protocollo: " + record.value());
        }
    }
}
