# JSON
JSON er vår modul som styrer alt som har med lokallagring med JSON

## Installasjon
For å installere JSON kjører du `mvn install`

## Testing av Core
Testene er skrevet i Junit5 som er et rammeverk for testing av Java
For å teste JSON kjører du `mvn test -pl json`

## Testdekningsgrad med JaCoCo
For testdekningsgraden til Core bruker vi JaCoCo.
For å finne ut testdekningsgraden kjører du `mvn clean jacoco:prepare-agent test jacoco:report` også finner du rapport filen som heter `index.html` under `json/target/site`.
