# Core
Core er modulen i applikasjonen vår, Marx bank, for delt kode og logikk som både frontend og backenden skal bruke.

## Installasjon
For å installere core kjører du `mvn install`

## Testing av Core
Testene er skrevet i Junit5 som er et rammeverk for testing av Java
For å teste core kjører du `mvn test -pl core`

## Testdekningsgrad med JaCoCo
For testdekningsgraden til Core bruker vi JaCoCo.
For å finne ut testdekningsgraden kjører du `mvn clean jacoco:prepare-agent test -pl core jacoco:report` også finner du rapport filen som heter `index.html` under `core/target/site`.

# Klassediagrammer

## Kort om de forskjellige kontotypene

I applikasjonen har vi en del forskjellige kontoer du kan ha. Vi har 4 forskjellige typer i Release 2. Det er: Brukskonto, Sparekonto, Kredittkonto og Marxkonto.

Disse fire forskjellige kontoene har alle de samme base funksjonalitetene, som å ta ut penger, sette inn penger, og overføre penger mellom kontoen. Men 3 ut av 4 kontoer har ekstra funksjonalitet som går utover de vanlige.

Oversikt over funskjonalitet:
* Brukskontoer er den vanlige kontoen vår og har ikke noe ekstra funksjonalitet utover å sette inn, overføre og ta ut penger.
* Sparekontoen er en konto for sparing og har derfor en bedre rente som gjør det gunnstig å spare penger på denne kontoen.
* Kredittkontoen har en kredittgrense som lar deg ta ut over den egentlige mengden penger du har på kontoen. Alle har en standard kredittgrense på 300kr i Release 2.
* Marxkonto er en konto som bygger på prinsippene av marxisme. Her skal ikke noen ha alt for masse penger og derfor deles da alt av innskudd du setter inn som gjør at du har over 500kr totalt på kontoen med andre Marxkontoer. Er det kun en marxkonto i bruk, så er det mulig å ha over 500kr på kontoen.

## Generell klassestruktur

![](diagrammer/KlassediagramMedDM(nytt).svg)

## Arv fra Account klassen

![](diagrammer/klassediagram_konto_arv.png)
