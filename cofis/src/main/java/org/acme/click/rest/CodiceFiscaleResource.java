package org.acme.click.rest;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.acme.click.model.CodiceFiscale;
import org.jboss.logging.Logger;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class CodiceFiscaleResource {

    private static final Logger LOG = Logger.getLogger("COFIS");
    
    @GET
    @Path("isValid/{cf}")
    public Boolean check(@PathParam("cf") String cf) {
        LOG.info("Verifica codice fiscale: " + cf);
        return new CodiceFiscale().setCodiceFiscale(cf).isValid();
    }

}
