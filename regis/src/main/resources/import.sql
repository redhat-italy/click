drop table IF EXISTS Iscrizione;

create table ISCRIZIONE (
       id bigint auto_increment,
       codiceFiscale varchar(16), 
       codiceProtocollo varchar(6),
       tipo varchar(12),
       errore varchar(50),
       premio varchar(20),
       primary key (id)
    );

INSERT INTO ISCRIZIONE (codiceFiscale, codiceProtocollo, tipo) VALUES ('FRNLLD99M15H501U', 'JH6GC7', 'imperative');
INSERT INTO ISCRIZIONE (codiceFiscale, codiceProtocollo, tipo) VALUES ('PSKSTL82R49F205N', 'NB446X', 'imperative');
INSERT INTO ISCRIZIONE (codiceFiscale, codiceProtocollo, tipo) VALUES ('CCMLGO90E68F839M', '8MK10D', 'imperative');