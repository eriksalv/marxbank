# storage
storage er vårt modul som styrer alt som har med lokallagring og datahåndtering (før vi implementerer RestAPI'en vår) i appen. Den hovedsaklig av en klasse, `DataManager` som styrer alt som har med data å gjøre. Vi designet den på en slik måte at den er tilgjengelig fra overalt gjennom å kalle:
```java
DataManager.manager()
```
Dette lar oss lett sende og hente data til og fra vår lokale database. Vi lagrer dataen vår i en egen fil kalt data.json med en slik struktur som vist under.

## Installasjon og testing

### Installasjon
For å installere JSON kjører du `mvn install -DskipTests`

### Testing av storage
Testene er skrevet i Junit5 som er et rammeverk for testing av Java
For å teste JSON kjører du `mvn test -pl storage`

### Testdekningsgrad med JaCoCo
For testdekningsgraden til Core bruker vi JaCoCo.
For å finne ut testdekningsgraden kjører du `mvn clean jacoco:prepare-agent test jacoco:report` også finner du rapport filen som heter `index.html` under `storage/target/site`.
Du kan også finne Testdekningsgrad på hovedsiden av Readme.

## Struktur av storage

Hele modulen er bygd rundt det å kunne lagre data lokalt til en json fil og lese data tilbake fra den json filen. Dette skjer gjennom bruk av flere Serializere og Deserializere som serialiserer og deserialserer dataen vår. Alle er egendefinerte og omhandler klasser fra Core. Under ser du hvordan funksjonskallet lagringen skjer i storage.

![Lagrings kall](../diagrammer/storageSerializeDiagram.svg)

## Lagring i Json

### struktur

![JSON struktur](/diagrammer/marxBankJsonStruktur.svg)

### Forklaring

For lagring lokalt av dataen til brukeren, kontoer og overføringer blir lagret i en json struktur. Strukturen til dataen er slik som vist i diagrammet under. 

Objektene lagres i en array som kalles da `users` for brukere, `accounts` for kontoer og `transactions` for overføringer.

En bruker lagres med disse datene: `id`, `username`, `email`, `password` som er kryptert, og `accounts` som er et array med id-er til kontoene.

En konto lagres med disse dataene: `id`, `user` som er id-en til brukeren som eier kontoen, `accountNumber`, `name`, `transactions` som er et array med id-er til overføringene gjort på kontoen (det er da både de som er sendt til og fra), `balance`, `type`, `numberOfTransactions`.

En overføring lagres med disse dataene: `id`, `from` som er id-en kontoen overføringen er sent fra, `reciever` som er id-en til kontoen overføringer er sent til, `amount`, og `dateString`.
