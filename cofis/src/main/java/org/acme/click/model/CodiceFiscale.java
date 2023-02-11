package org.acme.click.model;

public class CodiceFiscale {

    public String codice;
    public String error = "none";

    public CodiceFiscale setCodiceFiscale(String cf){
        this.codice = normalize(cf);
        return this;
    }

    public String getValue(){
        return this.codice;
    }

    public String getError(){
        return this.error;
    }

    private String normalize(String cf)	{
		cf = cf.replaceAll("[ \t\r\n]", "");
		cf = cf.toUpperCase();
		return cf;
	}

    public boolean isValid(){
        if( codice.length() == 0 ){
            this.error = "stringa vuota";
            return false;
        }
        if( codice.length() == 11 ){
            return validateShort();
        }
        if( codice.length() == 16 ){
            return validate();
        }
        this.error = "lunghezza stringa non corretta: " + codice.length();
        return false;
	}

    private boolean validateShort(){
		if( ! codice.matches("^[0-9]{11}$") ){
            this.error = "Caratteri non validi";
            return false;
        }
		int s = 0;
		for(int i = 0; i < 11; i++){
			int n = codice.charAt(i) - '0';
			if( (i & 1) == 1 ){
				n *= 2;
				if( n > 9 )
					n -= 9;
			}
			s += n;
		}
		if( s % 10 != 0 ){
            this.error = "Checksum non valido";
            return false;
        }
		return true;
	}

    private boolean validate(){
		if( ! codice.matches("^[0-9A-Z]{16}$") ){
            this.error = "Caratteri non validi";
            return false;
        }
		int s = 0;
		String even_map = "BAFHJNPRTVCESULDGIMOQKWZYX";
		for(int i = 0; i < 15; i++){
			int c = codice.charAt(i);
			int n;
			if( '0' <= c && c <= '9' )
				n = c - '0';
			else
				n = c - 'A';
			if( (i & 1) == 0 )
				n = even_map.charAt(n) - 'A';
			s += n;
		}
		if( s%26 + 'A' != codice.charAt(15) ){
            this.error = "Checksum non valido";
            return false;
        }
		return true;
    }
    
}
