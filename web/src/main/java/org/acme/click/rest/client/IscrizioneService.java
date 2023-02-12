package org.acme.click.rest.client;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.acme.click.model.Iscrizione;
import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/iscrizioni")
@RegisterRestClient(configKey="regis")
public interface IscrizioneService {
    
    @POST
    Iscrizione salvaIscrizione(Iscrizione iscrizione);

    @GET
    @Path("/cf/{cf}")
    List<Iscrizione> listaIscrizioni(@PathParam("cf") String cf);

}
