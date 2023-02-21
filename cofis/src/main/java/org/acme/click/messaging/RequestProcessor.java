package org.acme.click.messaging;

import javax.enterprise.context.ApplicationScoped;

import org.acme.click.model.CodiceFiscale;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.eclipse.microprofile.reactive.messaging.Outgoing;
import org.jboss.logging.Logger;

import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger("COFIS Processor");

    @Incoming("requests") 
    @Outgoing("validRequests")   
    @Blocking
    public String process(String theRequest) throws InterruptedException {
        LOG.info("[COFIS] Nuova richiesta iscrizione da: " + theRequest);
        CodiceFiscale codiceFiscale = new CodiceFiscale().setCodiceFiscale(theRequest);
        if(codiceFiscale.isValid()){
            LOG.info("[COFIS] codice fiscale valido: " + codiceFiscale.codice);
            return theRequest;
        }
        LOG.info("[COFIS] codice fiscale non valido: " + theRequest);
        return null;
    }
}
