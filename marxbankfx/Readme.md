# Marxbankfx
Dette er frontend modulen til applikasjonen vår, Marx bank. Den er bygget på Javafx og Testfx

## starting av frontend
For å starte frontend modulen kan du kjøre ```mvn javafx:run``` i marxbankfx mappen eller ```mvn javafx:run -pl marxbankfx``` fra hovedmappen

## testing av frontend
Frontenden testes av Testfx, som er et eget rammeverk for å skrive tester til Javafx.
For å kjøre tester skriver du ```mvn test -pl marxbankfx```

Modulen genererer også en testdeksningsgrad ved hjelp av JaCoCo og kan kjøres slik `mvn clean jacoco:prepare-agent install jacoco:report`.

Rapporten vil ende opp som en egen fil som heter `index.html` som ligger i `marxbankfx/target/site`