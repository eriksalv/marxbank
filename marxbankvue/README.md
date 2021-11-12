# Marxbankvue

Dette er modulen for den alternative frontenden til marxbank, bygget som en web-app med Vue 3 og Typescript. I tillegg til forskjeller i GUI og funksjonalitet fra FX-appen, vil Vue-appen være koblet opp med backend APIet. FX-appen vil derimot fortsette å bruke lokallagring. 
# Innhold
1. [Setup](#setup)
2. [Bygging](#bygging)
3. [Komponenter](#komponenter)
4. [Fourth Example](#komponenter)

## Setup

```
npm install
```

### Dev-kompilering
```
npm run serve
```

### Production-kompilering
```
npm run build
```

### Linting (kodekvalitet)
```
npm run lint
```

### Formattering
```
npx prettier --write --bracket-same-line src/
```

## Bygging

Prosjektet er satt opp med **vue-cli** og bygges med **npm**. I tillegg brukes **maven-frontend-plugin** slik at man kan kjøre npm kommandoer gjennom maven.  

### Bygging med maven fra rotmappe

```
mvn install -pl marxbankvue
```

## Komponenter

## Tilstandshåndtering (state management)

Vi har tatt i bruk tilstandshåndtering med **vuex 4**. Tilstanden til appen ligger under `src/store` mappen, og er delt i 4 moduler: accounts, transactions, users og auth. Dette tilsvarer de ulike endpointene i backenden.

## Routing

Vue router brukes for å ha en "single-page-application". De ulike rutene er konfigurert under `src/router` og fanene de fører til ligger under `src/views`. I tillegg er routingen konfigerert med autentisering, slik at man ikke får tilgang til faner med api data uten å først logge inn.

## Testing 

For enhetstesting av vue komponenter og typescript filer brukes **jest**. Alle tester ligger under `src/tests`. 

### Kjøre alle tester

```
npm test
```
### Kjøre én test

```
npm test navn-på-test
```
### Komponenttesting

For å teste vue komponenter bruker vi **vue-test-utils** i tillegg til jest. Alle komponenttester ligger under `src/teste/components` og har filnavn som ender med `.spec.ts`

### Mocking

**axios-mock-adapter** brukes for å mocke api-kall med axios.

