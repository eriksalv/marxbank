# Release 3

For den siste releasen bestemte vi oss for å lage en ny frontend klient som tar i bruk REST-API-et vi skal lage. Den nye klienten er en web-app, laget med Vue.js, og ligger i `marxbankvue` modulen. REST-API-et er laget med Spring Boot og ligger i `backend` modulen. I tillegg har vi en `api`-modul som inneholder alle requests/responses som sendes til/fra backenden. FX-appen bruker fortsatt lokallagring og er ellers stort sett uforandret siden release2. Alle issues for release3 ligger under `Release 3` milestonen.

## innholdsfortegnelse

1. [Vue](#vue)
2. [Ny funksjonalitet](#ny-funksjonalitet)
3. [Backend](#backend)
4. [Arbeidsvaner](#arbeidsvaner)
5. [Bygging av system](#bygging-av-system)
6. [Testing](#testing)
7. [Shippable produkt](#shippable-produkt)

## Vue

Den nye web-klienten er laget med Vue 3 og TypeScript. Målet var å få denne klienten til å støtte omtrent samme funksjonalitet som FX-klienten, men med et bedre design. Vi endte også opp med å legge til noe ny funksjonalitet, f.eks å kunne sette inn og ta ut penger fra konto, men ingen store forandringer. For mer info om Vue-appen, les readme fila i `marxbankvue`-modulen.

## Ny funksjonalitet

Det meste av funksjonaliteten i marxbank har blitt kopiert over og fått et nytt design i vue applikasjonen vår. De nye funksjonalitetene er utviklet i vue-appen vår og ikke i fx-appen vår.
- mulighet for innskudd og uttak
- redigering av bruker utover forandring av passord (funksjonalitet for forandring av passord ble lagt til i `release 2`.)
- Søke etter andre kontoer enn dine egne gjennom deres id.
- Flyttet datalagringen fra lokalt til server lagring i `backend` gjennom REST (fx-appen vår bruker fortsatt lokal lagring siden vi valgte å utvikle en ny klient.)

### Kryptering av passord

For brukerens sikkerhet har vi lagt til kryptering av passordene. Krypteringen skjer i `backend` modulet og utføres gjennom bruk av bcrypt kryptering. Vi valgte å bruke bcrypt siden det er en kjent og veldig sikker krypteringsalgoritme som kan konfigureres til å ha ønskede styrke gjennom bruk av salt. Vi valgte å bruke en salt på 12 som gjør at krypteringstiden på passordene ikke blir for lange, men fortsatt sikre.

### Autentisering med token

En ny klasse, Token.java, er laget i `core`-modulen, som er tilknyttet en User, og brukes for autentisering i `backend`-modulen. Token har et felt som angir ekspirasjonstiden dens, og brukes av web-klienten til å logge ut automatisk etter at ekspirasjonstiden er nådd. Token brukes også til å logge inn automatisk ved at den lagres i localStorage. 

## Backend

Backend-modulen vår er bygd på Springboot og bruker springboot web til å lage endpoints på serveren vår. Vi har 4 kontrollere: AuthController, UserController, AccountController, og TransactionController. Hver av disse kontrollerne har en egen endepunkter som sier til kontrolleren hva den skal gjør. Hvert endepunkt og dataen den trenger og sender tilbake er definert [her](/backend/docs/fullAPI.md). Dokumentasjonen til backend er skrevet i openapi 3 formatet og vi har generert dokumentasjon med [widdershins](https://github.com/Mermade/widdershins).

## Arbeidsvaner

Vi har hatt de samme arbeidsvanene i release3 som de forrige releasene. Dokumentasjon på dette ligger i `CONTRIBUTING.md`, men kort oppsummert så har vi:
- brukt oppgaver (issues) som utgangspunkt for alt arbeid
- jobbet i branches knyttet til en bestemt oppgave
- brukt conventional commits
- brukt sammenfletningsforespørsler og kodegjennomgang før noe sammenfletninger til master branchen

## Bygging av system

Npm brukes for å bygge den nye web-klienten, og vi har tatt i bruk `maven-frontend-plugin` slik at npm kommandoer kan kjøres gjennom maven.

Bygging av de gjenstående modulene gjøres ved bruk av `maven`.
## Testing

For testing av alle java modulene har vi brukt junit og testfx til testingen. Vi har siktet for å få all kjernefunksjonalitet (all funksjonalitet som har noe logikk i seg) testet og har endt opp med en testdekningsgrad på 84.65% totalt. For testdekningsgrad til hver kan du se på den nyeste pipelinen som har kjørt grønt (bestått alle jobbene) og se graden til hver jobb der.
### backend

Backend testes ved bruk av Springboot test konfigurert lik at den bruker tilfeldige porter slik at ingen andre applikasjoner som bruker porter blir påvirket. Det brukes også en `DirtiestContext` i `BeforeEachTestMethod` som tilbake stiller testdatabasen før hver test.

### marxbankvue

For testing i marxbankvue bruker vi jest, sammen med vue-test-utils for å teste Vue komponenter. Code-coverage genereres også av jest.

### api

Api-modulen testes ikke, siden den bare består av klasser med standard gettere og settere som er uten logikk. All logikk som brukes i api blir importert fra andre moduler og er testet i sitt respektive modul.

## Kodekvalitet og formattering

For kodekvalitet i java klassene har gruppen tatt i bruk en automatisk formaterer basert på gruppens satte kodekvalitets konfigurasjon. Formateringen kjøres ved `mvn formatter:format`. 

### marxbankvue

Kodekvalitet skjekkes av ESLint, og prettier brukes for å formattere kode. Maven er satt opp via maven-frontend-plugin til å skjekke kodekvalitet og formattere kode i marxbankvue gjennom `mvn verify`.

## Shippable produkt

MarxbankFX ble gjort shippable gjennom bruk av javafx jlink og jpackage. Installerbar versjon finner dere i rotmappa, og heter `marxbankFX.msi`. Den installerbare versjonen er FX versjonen av applikasjonen og støtter derfor ikke de nyeste funksjonalitetene som datalagring gjennom REST serveren og redesignet UI. Den versjonen lagrer data lokalt i hjem lokasjonen til brukeren i en mappe kalt `data`. 




