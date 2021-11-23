# Backend

Backend er modulet vårt som er backenden vår. Backend er bygd på spring-boot som rammeverk og h2 til datalagring. Backend er bygd på representational state transfer (REST) og har derfor en rekke forskjellige kall du kan gjøre til den. Du kan se alle kallene [her](docs/fullAPI.md)
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

Du finner rapporten i `backend/target/site/jacoco/index.html`
