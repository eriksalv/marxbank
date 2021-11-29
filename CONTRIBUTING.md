# Utvikling og bidrag til applikasjonen

Dette prosjektet utvikles og opprettholdes av gruppe 27 i emnet IT1901 på NTNU høsten 2021. For utvikling av dette prosjektet skal gruppen bruke smidige metoder som blir beskrevet her. Alle i gruppen har signert gruppekontrakt som beskriver hvordan gruppen skal jobbe sammen og hva som skal gjøres i spesielle situasjoner.

## Prosjektplanlegging

Gruppen har skrevet en brukerhistorie til hver sprint og alle brukerhistoriene er samlet i en fil kalt [brukerhistorie.md](brukerhistorier/brukerhistorier.md/)
Brukerhistoriene beskriver funksjonalitet i appen, og kommer i denne formen:
```
Som en ___ trenger jeg ___ sånn at jeg ___.
```
Hver brukerhistorie har oppgaver med egen prioritet tildelt, og om oppgaven har blitt implementert eller ikke. For å ikke bruke ekstra ressurser på å planlegge alt som kan skje, lager vi oppgaver gjennom sprinten. Dette gjør at vi kan bedre justere oss hvis uforventete problemer oppstår.

## Sprintplanlegging

Gruppen utvikler applikasjonen ved bruk av smidige metoder og deler opp implimenteringsprossessen i sprinter. Sprintene blir planlagt annenhver uke av gruppen og har et mål. Hver sprint får en egen brukerhistorie skrevet om sprinten. Brukerhistorien er beskrevet over. I Gitlab får hver sprint en milepæle som oppgaver blir lagt under. I [milepæle vinduet](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/-/milestones) på Gitlab kan man se hvordan gruppen ligger ann med oppgavene i denne sprinten.

Oppgavene i sprintene er delt inn slik at de alle skal dekker en utviklingsbit/kodebit. Utviklingsbit/kodebit betyr at oppgaven enten skriven ny kode, skriver om gammel kode, eller skriver dokumentasjon. Oppgavene blir laget i planleggingsmøtet til sprintene og underveis i sprintene hvis det oppstår noe nytt som må lages. I oppgavene skal gruppen bruke Gitlab sin `/estimate`-kommando som lar hver oppgave få en antatt utviklingstid.

### utvikling av oppgaver

Hver sprint har flere oppgaver som utvikles. Hver oppgave skal inneholde en kodebit/dokumentasjon som blir utviklet. Når en ny oppgave startes, lager man en ny gren for denne oppgaven. Hver gren har navnet til oppgaven og ID-en til oppgaven og kommer på denne formen:
``` 
69-utvikle-løsningen-til-verdens-undergang
```
Selv om grennavnet blir langt er det viktigst at den har med ID-en til oppgaven først. Dette lar oss enkelt og raskt finne ut hvilken gren som tilhører hvilken oppgave. Gruppen følger reglene gitt av [conventional commits](https://www.conventionalcommits.org/en/v1.0.0/) når vi lager bunter. I starten av buntemeldingen skal det være en av seks forskjellige typer. Typene er:
- fix
- feat (feature)
- ci (kontinuerlig integrering)
- docs (dokumentasjon)
- style
- refractor (gjennskriving av kode)
- test

Med gren navnet over vil vi da ha en slik buntemelding
```
feat(#69): utviklet løsningen til verdens undergang
```

Hvis en bunt har ny funksjonalitet som ødelegger/breaker gammel funksjonalitet må dette dokumenteres i buntemeldingen med ```BREAKING CHANGE: <beskrivelse av forandringen>``` slik at alle kan se denne bunten ødelegger denne funksjonaliteten.

### kontrollering
Når en oppgave har blitt løst og all koden er dyttet til grenen, må grenen sammenflettes med hovedgrenen. Dette gjøres ved å lage en sammenfletnings forespørsel. Sammenfletnings forespøreselen skal inneholde en fornuftig tittel, hva slags funksjonalitet som har blitt utviklet og hvilken oppgave den er koblet til. I sammenfletnings meldingen skal det stå ```Fixes #oppgavenummer``` eller ```Closes #oppgavenummer``` som sier til gitlab at denne oppgaven skal lukkes.

Et gruppemedlem kan selv velge å sammenflette en gren hvis det er dokumentasjon eller små kodejusteringer. For store kodeforandringer skal en annen i gruppen godkjenne sammenflettingen først. 

#### CI
Gruppen har valgt å utnytte seg av gitlabs støtte for kontinuerlig integrering. Vi har laget en egen pipeline som kjøres hver gang noe kode blir dyttet til en gren. Pipelinen verifiserer koden og kjører alle testene som er koblet til hver modul med kommandoen ```mvn verify```. Hvis testene består, får gruppen en melding om det. Hvis testene feiler får gruppen vite hvor i koden det feilet.

Etter hvert som gruppen legger til Testdekningsgrad og kodekvalitetsgrad kommer dette til å bli automatisk generert i hver pipeline og lagret.
### Underveis i sprinten

Underveis i sprinten har gruppen møtt minst engang for å diskutere hvordan oppgavene går. Hvis biller (bugs) eller vanskligheter har oppstått for gruppen mens vi har jobbet, har det blitt laget en ny oppgave på det som er under kategorien Bugs. Disse oppgavene er alltid prioritert og må ha en beskrivelse av seg i oppgave teksten på hvordan den oppstå, hvilkene klasser den inneholder og hvordan å gjenskape den.

### Etter sprinten

Etter hver sprint møtes gruppen og diskuterer hvordan sprinten gikk. Her går vi gjennom vanskligheter vi har hatt med å utvikle forskjellige oppgaver og diverse annen informasjon koblet til prosjektet. Er det oppgaver igjen fra forrige sprint vil de bli delegert en viktighetsgrad og prioritert i neste sprint.

# språk

Norsk blir brukt gjennom utviklingen av applikasjonen i Gitlab og dokumentasjon. Oversettelsene fra engelsk til norsk vi har gjort er:
- issue -> **Oppgave**
- commit -> **bunt**
- branch -> **gren**
- merge -> **sammenflette**
- merge request -> **sammenfletnings forespørsel**
- milestone -> **milepæle**
- bugs -> **biller**

