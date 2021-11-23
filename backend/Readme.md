# Backend

Backend er modulet vårt som er Backenden/REST-api serveren vår. Serveren er bygd på spring-boot og bruker h2 til datalagring. 

## Kjøring og testing

### Installering

Før du kan kjøre appen må du installere alt den trenger, dette gjør du med:
```bash 
mvn clean install
```
For å installere uten å teste serveren legger du til `-DskipTests` etter install.

### Kjøring
Etter det kan du kjøre applikasjonen med hvis du er i backend mappen:
```bash
mvn spring-boot:run
```


### Testing
For å teste backend kjører du dette hvis du er i backend mappen:
```bash
mvn test 
```

Hvis du her i hovedmappen kjører du dette istedenfor

```bash
mvn test -pl backend
```

Hvis du ønsker å finne testdekningsgraden til backend, så kjører du jacoco med testen slik:
```
mvn jacoco:prepare-agent test jacoco:report
```

Også finner du rapporten i `backend/target/site/jacoco/index.html`

## API kall
Serveren er bygd på Representation State Transfert (REST) arkitekturen og har derfor visse kall man kan gjøre til serveren. Under kan du se noen av kallene.

