package org.acme.click.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.transaction.Transactional;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;

@Entity
public class Iscrizione extends PanacheEntityBase {
    @Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Long id;
    public String codiceFiscale;
    public String tipo;
    public String codiceProtocollo;
    public String errore;
    public String premio;

    @Transactional
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
            Iscrizione.update("premio = ?1 where codiceProtocollo = ?2", cp, premio);
        }
        return iscrizione;
    }

}
