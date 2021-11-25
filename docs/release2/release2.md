# Release 2

I release 2 var målet å fullføre alt av nødvendig funksjonalitet slik at appen teoretisk sett kunne tas i bruk som en ordentlig app. I tillegg ble det prioritert høyt å lage flere tester (spesielt for fx-modulen), siden dette var noe som manglet fra første release.  
Vi hadde 1 sprint denne gangen, som er dokumentert som `Release 2` under `Milestones`.

## Ny funksjonalitet
Det meste av nødvendig funksjonalitet ble ferdig implementert i `Release 1`, men det var et par ting som manglet som er ferdig nå:
- Lagt til flere kontotyper (MarxAccount, CheckingAccount, CreditAccount)
- Kan nå logge inn med eksisterende bruker og opprette ny bruker
- To brukere kan ikke ha samme brukernavn
- Kan logge ut under profil-fanen
- Laget en sparekalkulator med en egen fane i hovedmenyen
### Nye klasser
- Laget en AccountFactory klasse for å gjøre det lettere å opprette kontoer av en bestemt type 
- Laget en SavingsCalc klasse for å håndtere logikk som brukes av sparekalkulatoren
### Modularisering
Når vi implementerte lagring med JSON i forrige release, la vi til logikken for dette i core modulen. Vi tenkte etterhvert at det var mer hensiktsmessig å lage en egen modul for dette, som er gjort til `Release 2`. Den nye modulen for JSON lagring heter `storage`. Mer info om modulen ligger i egen readme fil i modulen.
## Implisitt lagring 
Vi har valgt å basere appen vår på en "implisitt lagring"-metafor, der alt lagres automatisk etter å ha blitt opprettet av bruker

## Brukerhistorier

Vi har laget 2 nye brukerhistorier for bruk av sparekalkulator og å logge ut.

## Diagrammer

### Klasse Diagrammer

- Oppdatert klassediagrammet fra release 1 slik at det skal være mer informativt og lesevennlig, og laget noen nye diagrammer som viser klassehierarkiet i mer detalj (spesielt med tanke på hvilke klasser som arver fra Account klassen). Disse finnes under readme fila i core modulen. 

### Sekvens Diagram

- Laget et sekvensdiagram av en bruker som overfører penger mellom to kontoer. Finnes i readme fila i `marxbankfx` modulen.

### JSON format

- Laget et diagram som viser formatet på JSON data. Dette ligger under readme fila i rotmappen.

## Testing
Vi har nå fått dekt det aller meste av koden med tester (86.33%).

## Byggeoppsett

Byggeoppsettet har blitt utvidet med Checkstyle og Spotbugs
