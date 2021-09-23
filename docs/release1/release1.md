# Release 1

Vi har hatt 2 sprints for release 1, som er dokumentert i Sprint 1 og Sprint 2 under "Milestones".

## Sprint 1

Første sprint besto av å først definere hva hovedfunksjonaliteten til appen skulle være. Vi kom fram til at det skal være mulig å:

1) Oprette bruker og nye kontoer 
2) Overføre penger mellom to kontoer
3) Se overføringshistorikk

Opprettelse av bruker er ikke så viktig for en minimal app, så dette implementeres ferdig til en senere release.

### Brukerhistorier

Det ble laget 3 brukerhistorier som korresponderer med de tre funksjonalitetene nevnt ovenfor. Disse ligger i brukerhistorier.md under mappen brukerhistorier.

### Figma prototype

En figma prototype ble laget for å ha et "ferdig" design å referere til mens appen utvikles videre. Figma link: https://www.figma.com/file/tb5iZpKANC7WX8SFSjwr5H/Bank-app-ITP?node-id=0%3A1 

### Basic javafx app

Målet for sprint 1 var å lage en minimal javafx app med mesteparten av basic logikk og fx-design implementert, samt lage tester til logikken. Noe av fx-designet ble forskøvet til Sprint 2, men mesteparten av logikken (utenom persistens) ble implementert i Sprint 1.

## Sprint 2

Målet for sprint 2 var å gjøre ferdig den minimale appen slik at den kan kjøres, og å implementere lagring og lesing av data med JSON. I tillegg har vi implementert testdekningsgrad med jacoco.

### Funksjonalitet 

I release 1 skal det være mulig å:

1) Laste og lagre kontoer, transaksjoner og brukere fra JSON fil.
2) Lage nye kontoer
3) Overføre beløp mellom kontoer
4) Se aktivitet (transaksjoner)

### Tester

Vi har laget ferdig en rekke tester for core modulen som dekker mestparten av koden (72% testdekningsgrad). I marxbankfx modulen er det bare en test så langt, så implementasjon av flere fxtester blir prioritert høyt fremover.
