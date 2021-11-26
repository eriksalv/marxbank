# Release 3

For den siste releasen bestemte vi oss for å lage en ny frontend klient som tar i bruk REST-API-et vi skal lage. Den nye klienten er en web-app, laget med Vue.js, og ligger i `marxbankvue` modulen. REST-API-et er laget med Spring Boot og ligger i `backend` modulen. I tillegg har vi en `api`-modul som inneholder alle requests/responses som sendes til/fra backenden. FX-appen bruker fortsatt lokallagring og er ellers stort sett uforandret siden release2. Alle issues for release3 ligger under `Release 3` milestonen.

## Vue

Den nye web-klienten er laget med Vue 3 og TypeScript. Målet var å få denne klienten til å støtte omtrent samme funksjonalitet som FX-klienten, men med et bedre design. Vi endte også opp med å legge til noe ny funksjonalitet, f.eks å kunne sette inn og ta ut penger fra konto, men ingen store forandringer. For mer info om Vue-appen, les readme fila i `marxbankvue`-modulen.

## Ny funksjonalitet

### Kryptering av passord

### Autentisering med token

En ny klasse, Token.java, er laget i `core`-modulen, som er tilknyttet en User, og brukes for autentisering i `backend`-modulen. Token har et felt som angir ekspirasjonstiden dens, og brukes av web-klienten til å logge ut automatisk etter at ekspirasjonstiden er nådd. Token brukes også til å logge inn automatisk ved at den lagres i localStorage. 

## Backend

Backend-modulen består av 4 endpoints: AuthController, UserController, AccountController, og TransactionController

## Arbeidsvaner

Vi har hatt de samme arbeidsvanene i release3 som de forrige releasene. Dokumentasjon på dette ligger i `CONTRIBUTING.md`, men kort oppsummert så har vi:
- brukt issues (utviklingsoppgaver) som utgangspunkt for alt arbeid
- jobbet i branches knyttet til en bestemt issue
- brukt conventional commits
- brukt merge requests og code reviews før noe merges til master branchen

## Bygging av system

Npm brukes for å bygge den nye web-klienten, og vi har tatt i bruk `maven-frontend-plugin` slik at npm kommandoer kan kjøres gjennom maven.

## Testing

### backend

(Skriv om hvordan backend testes her)

### marxbankvue

For testing i marxbankvue bruker vi jest, sammen med vue-test-utils for å teste Vue komponenter. Code-coverage genereres også av jest.

### api

Api-modulen testes ikke, siden den bare består av klasser med standard gettere og settere.

## Kodekvalitet og formattering

### marxbankvue

Kodekvalitet skjekkes av ESLint, og prettier brukes for å formattere kode. Maven er satt opp via maven-frontend-plugin til å skjekke kodekvalitet og formattere kode i marxbankvue gjennom `mvn verify`.

## Shippable produkt

(hmmm)




