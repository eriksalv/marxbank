# Backend

Backend er modulet vårt som håndterer databehandling i applikasjonen vår. Den er bygget på Spring-boot og bruker H2 databasen til datalagring. 

## Kjøring og testing

### Installering

Før du kan kjøre appen må du installere alt den trenger, dette gjør du med:
```bash 
mvn clean install
```
For å installere uten tester legger du til `-DskipTests etter install`

### Kjøring
Etter det kan du kjøre applikasjonen med:
```bash
mvn spring-boot:run
```

hvis du er i backend mappen, ellers kjører du den med:
```bash
mvn spring-boot:run -pl backend
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
mvn jacoco:prepare-agent test -pl backend jacoco:report
```

Også finner du rapporten i `backend/target/site/jacoco/index.html`

## 