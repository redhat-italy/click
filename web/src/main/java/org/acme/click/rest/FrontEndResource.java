package org.acme.click.rest;

import org.acme.click.model.Iscrizione;
import org.acme.click.rest.client.IscrizioneService;
import org.acme.click.rest.client.ProtocolloService;
import org.acme.click.rest.client.VerificaCodiceFiscaleService;
import org.eclipse.microprofile.rest.client.inject.RestClient;
import org.jboss.logging.Logger;

import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class FrontEndResource {

    private static final Logger LOG = Logger.getLogger("FRONTEND");

    @RestClient 
    VerificaCodiceFiscaleService cofis;
    @RestClient
    ProtocolloService urp;
    @RestClient
    IscrizioneService regis;

    @POST
    @Path("/subscribe/{cf}")
    public Iscrizione subscribe(@PathParam("cf") String cf) {
        LOG.info("Richiesta iscrizione da: " + cf);
        Iscrizione iscrizione = new Iscrizione();
        iscrizione.codiceFiscale = cf;
        iscrizione.errore = "nessuno";
        iscrizione.premio = "nessuno";
        iscrizione.codiceProtocollo = "non protocollato";
        iscrizione.tipo = "imperative";
        boolean isValid = cofis.checkCodiceFiscale(cf);
        if(isValid){
            LOG.info("Codice fiscale: " + cf + " corretto");
            iscrizione.codiceProtocollo = urp.richiediCodiceProtocollo();
            Iscrizione archivioRegis = regis.salvaIscrizione(iscrizione);
            if(archivioRegis == null){
                LOG.info("iscrizione per: " + cf + " non eseguita - richiesta presente in archivio");
                iscrizione.codiceProtocollo = "";
                iscrizione.errore = "richiesta presente in archivio";
                return iscrizione;
            }
            return archivioRegis;
        } else {
            LOG.info("Codice fiscale: " + cf + " non valido");
            iscrizione.errore = "Codice Fiscale non valido";
            return iscrizione;            
        }
    }

    @GET
    @Path("/check/{cf}")
    public List<Iscrizione> checkSubscriptions(@PathParam("cf") String cf) {
        return regis.listaIscrizioni(cf);
    }

}