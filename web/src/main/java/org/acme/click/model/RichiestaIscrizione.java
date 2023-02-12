package org.acme.click.model;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class RichiestaIscrizione {
    
    public String codiceFiscale;
    public String numeroDiProtocollo;
    public String paradigma;

    @POST
    @Path("/{cf}")
    public Boolean check(@PathParam("cf") String cf) {
        return true;
    }

}
