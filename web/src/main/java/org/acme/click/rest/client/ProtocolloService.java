package org.acme.click.rest.client;

import javax.ws.rs.GET;
import javax.ws.rs.Path;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@Path("/urp")
@RegisterRestClient(configKey="urp")
public interface ProtocolloService {
    
    @GET
    @Path("/protocollo/6")
    String richiediCodiceProtocollo();
}
