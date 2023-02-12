package org.acme.click.rest.client;

import javax.ws.rs.POST;
import javax.ws.rs.Path;

import org.acme.click.model.Iscrizione;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/iscrizioni")
@RegisterRestClient(configKey="regis")
public interface IscrizioneService {
    
    @POST
    Iscrizione salvaIscrizione(Iscrizione iscrizione);

}
