# click
Una applicazione demo che simula un sistema di gestione di un ipotetico Click Day.
Utilizzabile anche per una gestione lotterie.
Praticamente l'applicazione è composta da alcuni micro servizi che registrano le richieste di partecipazione. Tra le richieste posso poi essere sorteggiate alcune di esse per assegnarre un reward o premio. vediamo più in dettaglio i moduli.

### REGIS
E' il modulo che registra le richieste di partecipazione. Espone un end-point ReST che accetta l'invio di un oggetto **Iscrizione** e lo salva su un database relazionale per poter essere poi riutilizzato da altre applicazioni. Nel caso specifico della lotteria alcune delle iscrizioni possono essere sorteggiate. In questa implementazione in realta' il database è una istanza *effimera* di un DB H2 (ovvero volatile, quindi se si riavvia il POD si perdono i contenuti) che altri non è che un in-memory-db contenuto nel servizio stesso.

### URP
E' il modulo che simula la gestione delle relazioni pubbliche **URP** appunto, ed espone un end-point ReST per il rilascio del numero di protocollo di una istanza di richiesta. Di fatto l'EP se invocato ritorna una stringa di caratteri di una certa lunghezza generati casualmente.

### COFIS
E' il modulo che simula l'ufficio preposto alla gestione dei codici fiscali (quello che nella realtà, per l'Italia, potrebbe essere l'Agenzia delle Entrate che appunto si occupa del rilascio e dell'archivio e la gestione dei CF) ed espone un end-point ReST che prende in input un codice fiscale e ritorna un booleano in base alla validità o meno del codice inviato. Anche in questo caso l'algoritmo di controllo è molto semplice e non gestisce tutta una serie di casistiche ed eccezioni che andrebbero invece considerate. Serve solo a simulare un controllo sulla richiesta.

### WEB
E' il modulo di front-end che espone alcune pagine web (basate su HTML + Bootstrap + JQuery) ed un servizio ReST di front end per l'orchestrazione delle chiamate ai micro servizi di back end.

### Stream di Eventi
Tutti i moduli descritti sopra hanno anche la possibilità di interagire con uno stream di eventi (reagendo ad un evento o creando un evento). Lo stream di eventi è basato su una istanza cloud Kafka di Red Hat ovvero **Red Hat OpenShift Streams for Apache Kafka**.

### Deploy su OpenShift
Ognuno dei moduli può essere installato sul cluster di laboratorio attraverso l'estensione OpenShift di quarkus nel seguente modo:

>
> quarkus build -Dquarkus.kubernetes.deploy=true 

oppure

> 
> ./mvnw clean package -Dquarkus.container-image.build=true

## Installazione
L'applicazione è installata su un cluster di laboratorio **OpenShift** del team di prevendita di Red Hat Italia mentre il broker degli eventi è una istanza **Kafka** attivata sul [Servizio Cloud di Red Hat](https://developers.redhat.com/products/red-hat-openshift-streams-for-apache-kafka/getting-started?extIdCarryOver=true&intcmp=7013a000002D1gVAAS&sc_cid=701f2000001OH7TAAW).