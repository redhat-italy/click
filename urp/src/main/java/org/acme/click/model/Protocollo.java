package org.acme.click.model;

public class Protocollo {

    public String assegnaCodice(int howMany) {
        String toBeUsed = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        StringBuilder builder = new StringBuilder(howMany);
        
        for (int i = 0; i < howMany; i++) {

            int ndx = (int)(toBeUsed.length() * Math.random()); 
            builder.append(toBeUsed.charAt(ndx)); 
        }
        return builder.toString(); 
    }

}