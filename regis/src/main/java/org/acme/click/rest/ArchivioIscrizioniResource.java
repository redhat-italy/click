package org.acme.click.rest;

import org.acme.click.model.Iscrizione;
import org.jboss.logging.Logger;

import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.transaction.Transactional;
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

    private static final Logger LOG = Logger.getLogger("COFIS");

    @GET
    public List<Iscrizione> list() {
        return Iscrizione.listAll();
    }

    @POST
    @Transactional
    public Iscrizione create(Iscrizione iscrizione) {
        return Iscrizione.safetySave(iscrizione);
    }

    @PUT
    @Transactional
    @Path("/assegna/{cp}/{premio}")
    public Iscrizione updatePremio(@PathParam("cp")String cp, @PathParam("premio")String premio) {
        Iscrizione iscrizione = Iscrizione.findByCodiceProtocollo(cp);
        if(iscrizione == null) {
            throw new NotFoundException();
        }
        iscrizione.premio = premio;
        iscrizione.persist();
        return iscrizione;
    }
    
    @DELETE
    @Transactional
    @Path("/{cp}")
    public void delete(@PathParam("cp")String cp) {
        Iscrizione iscrizione = Iscrizione.findByCodiceProtocollo(cp);
        if(iscrizione == null) {
            throw new NotFoundException();
        }
        iscrizione.delete();
    }

    @POST
    @Transactional
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

    @GET
    @Path("/count")
    public String count() {
        return Long.toString(Iscrizione.count());
    }
    
    @POST
    @Transactional
    @Path("/estrazione/{premio}/{qta}")
    public void lottery(@PathParam("premio")String premio, @PathParam("qta")String qta) {
        int numb = Integer.valueOf(qta);
        List<Iscrizione> list = Iscrizione.listAll();

        Iscrizione[] array = new Iscrizione[list.size()];
        list.toArray(array); // fill the array

        Random random = new Random();
        for(int i=0; i<numb; i++){
            int luckyNumber = random.nextInt(list.size());
            Iscrizione winner = array[luckyNumber];
            LOG.info("[REGIS] premio per: " + winner.codiceProtocollo);
            updatePremio(winner, premio);
        }
    }    

    private void updatePremio(Object iscrizione, String premio){
        Iscrizione i = (Iscrizione)iscrizione;
        i.premio = premio;
        i.persist();    
    }

}