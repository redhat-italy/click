package org.acme.click.messaging;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.acme.click.model.Protocollo;
import io.smallrye.reactive.messaging.kafka.Record;
import org.eclipse.microprofile.reactive.messaging.Channel;
import org.eclipse.microprofile.reactive.messaging.Emitter;
import org.eclipse.microprofile.reactive.messaging.Incoming;
import org.jboss.logging.Logger;

import io.smallrye.common.annotation.Blocking;

@ApplicationScoped
public class RequestProcessor {
    
    private static final Logger LOG = Logger.getLogger("URP");

    @Inject @Channel("subscriptions")
    Emitter<Record<String, String>> emitter;

    @Incoming("validRequests") 
    @Blocking
    public void process(String cf) throws InterruptedException {
        String protocollo = new Protocollo().assegnaCodice(6);
        LOG.info("[URP] assegnato protocollo <" + protocollo + "> per richiesta da: " + cf);
        emitter.send(Record.of(cf, protocollo));
    }
}
