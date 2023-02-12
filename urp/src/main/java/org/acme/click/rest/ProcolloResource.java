package org.acme.click.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.click.model.Protocollo;

@Path("/urp")
public class ProcolloResource {

    @GET
    @Path("/protocollo/{size}")
    @Produces(MediaType.TEXT_PLAIN)
    public String richiediProtocollo(@PathParam("size") int size) {
        return new Protocollo().assegnaCodice(size);
    }

}
