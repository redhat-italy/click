package org.acme.click.model;

import java.util.List;

import io.quarkus.mongodb.panache.PanacheMongoEntity;
import io.quarkus.mongodb.panache.common.MongoEntity;

@MongoEntity(collection="iscrizioni")
public class Iscrizione extends PanacheMongoEntity {
    public String codiceFiscale;
    public String tipo;
    public String codiceProtocollo;
    public String errore;
    public String premio;

    public static Iscrizione safetySave(Iscrizione iscrizione){
        Iscrizione localIscrizione = find("codiceFiscale = ?1 and tipo = ?2", iscrizione.codiceFiscale, iscrizione.tipo).firstResult();
        if(localIscrizione == null){
            iscrizione.persist();
            return iscrizione;
        } else {
            return null;
        }
    }

    public static List<Iscrizione> findByCodiceFiscale(String cf){
        return Iscrizione.list("codiceFiscale", cf);
    }

    public static Iscrizione findByCodiceProtocollo(String cp){
        return find("codiceProtocollo", cp).firstResult();
    }

    public static List<Iscrizione> findByPremio(String premio){
        return Iscrizione.list("premio", premio);
    }

    public static Iscrizione assegnaPremioByCodiceProtocollo(String cp, String premio){
        Iscrizione iscrizione = find("codiceProtocollo", cp).firstResult();
        if(iscrizione != null){
            iscrizione.premio = premio;
            iscrizione.update();
        }
        return iscrizione;
    }

}
