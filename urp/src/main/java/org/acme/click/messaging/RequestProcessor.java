package org.acme.click.messaging;

import javax.enterprise.context.ApplicationScoped;

import org.acme.click.model.Iscrizione;
import org.acme.click.model.Protocollo;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger("URP Processor");

    @Incoming("validRequests") 
    @Outgoing("subscriptions")   
    @Blocking
    public Iscrizione process(String theRequest) throws InterruptedException {
        Iscrizione iscrizione = new Iscrizione();
        iscrizione.codiceFiscale = theRequest;
        iscrizione.errore = "nessuno";
        iscrizione.premio = "nessuno";
        iscrizione.codiceProtocollo = new Protocollo().assegnaCodice(6);
        iscrizione.tipo = "reactive";
        LOG.info("Nuova richiesta iscrizione da: " + iscrizione.codiceFiscale);
        return iscrizione;
    }
}
