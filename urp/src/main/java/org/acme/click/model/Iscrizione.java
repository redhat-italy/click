package org.acme.click.model;

public class Iscrizione {
    public String codiceFiscale;
    public String tipo;
    public String codiceProtocollo;
    public String errore;
    public String premio;

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        buffer.append("{");
        buffer.append("\"codiceFiscale\":\"").append(codiceFiscale).append("\",");
        buffer.append("\"tipo\":\"").append(tipo).append("\",");
        buffer.append("\"codiceProtocollo\":\"").append(codiceProtocollo).append("\",");
        buffer.append("\"errore\":\"").append(errore).append("\",");
        buffer.append("\"premio\":\"").append(premio).append("\"").append("}");
        return buffer.toString();
    }
}