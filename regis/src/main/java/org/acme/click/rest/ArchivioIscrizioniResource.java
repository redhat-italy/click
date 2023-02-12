package org.acme.click.rest;

import org.acme.click.model.Iscrizione;
import org.bson.types.ObjectId;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/iscrizioni")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ArchivioIscrizioniResource {

    @GET
    public List<Iscrizione> list() {
        return Iscrizione.listAll();
    }

    @POST
    public Iscrizione create(Iscrizione iscrizione) {
        return Iscrizione.safetySave(iscrizione);
    }

    @PUT
    @Path("/{id}")
    public void update(String id, Iscrizione iscrizione) {
        iscrizione.update();
    }
    
    @PUT
    @Path("/assegna/{cp}/{premio}")
    public Iscrizione updatePremio(@PathParam("cp")String cp, @PathParam("premio")String premio) {
        Iscrizione iscrizione = Iscrizione.findByCodiceProtocollo(cp);
        if(iscrizione == null) {
            throw new NotFoundException();
        }
        iscrizione.premio = premio;
        iscrizione.update();
        return iscrizione;
    }
    
    @DELETE
    @Path("/{id}")
    public void delete(@PathParam("id")String id) {
        Iscrizione iscrizione = Iscrizione.findById(new ObjectId(id));
        if(iscrizione == null) {
            throw new NotFoundException();
        }
        iscrizione.delete();
    }

    @POST
    @Path("/drop")
    public void drop() {
        Iscrizione.deleteAll();
    }

    @GET
    @Path("/cf/{cf}")
    public List<Iscrizione> searchByCF(@PathParam("cf")String cf) {
        return Iscrizione.findByCodiceFiscale(cf);
    }
    
    @GET
    @Path("/cp/{cp}")
    public Iscrizione searchByCP(@PathParam("cp")String cp) {
        return Iscrizione.findByCodiceProtocollo(cp);
    }

    @GET
    @Path("/premi/{premio}")
    public List<Iscrizione> premiati(@PathParam("premio") String premio) {
        return Iscrizione.findByPremio(premio);
    }

}