package org.acme.click.messaging;

import javax.enterprise.context.ApplicationScoped;

import org.acme.click.model.Iscrizione;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

@ApplicationScoped
public class IscrizioneConsumer {

    private static final Logger LOG = Logger.getLogger("REGIS");

    @Incoming("iscrizioni")
    public void consume(Iscrizione iscrizione) {
        LOG.info("[Iscrizione Consumer] nuova iscrizione con codice protocollo: " + iscrizione.codiceProtocollo);
    }
}
