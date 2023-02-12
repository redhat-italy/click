package org.acme.click.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/")
@RegisterRestClient(configKey="cofis")
public interface VerificaCodiceFiscaleService {
    
    @GET
    @Path("/isValid/{cf}")
    Boolean checkCodiceFiscale(@PathParam("cf") String cf);
}
