package org.acme.click.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.click.model.CodiceFiscale;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CodiceFiscaleResource {

    @GET
    @Path("isValid/{cf}")
    public Boolean check(@PathParam("cf") String cf) {
        return new CodiceFiscale().setCodiceFiscale(cf).isValid();
    }

}
