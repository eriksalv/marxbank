# Marxbankvue

Dette er modulen for den alternative frontenden til marxbank, bygget som en web-app med Vue 3 og Typescript. I tillegg til forskjeller i GUI og funksjonalitet fra FX-appen, vil Vue-appen være koblet opp med backend APIet. FX-appen vil derimot fortsette å bruke lokallagring. 

1. [Forskjeller og likheter fra FX-app](#forskjeller-og-likheter-fra-fx-app)
2. [Setup](#setup)
3. [Bygging](#bygging)
4. [Komponenter](#komponenter)
5. [Tilstandshåndtering](#tilstandshåndtering)
6. [Routing](#routing)
7. [Styling](#styling)
8. [Testing](#testing)
9. [Kodekvalitet og formattering](#kodekvalitet-og-formattering)

## Forskjeller og likheter fra FX-app

GUI-et til den nye web-appen er strukturert på omtrent samme måte som FX-appen, så se dokumentasjon i `marxbankfx` for mer info om hvordan de ulike fanene fungerer.  
Funksjonalitetmessig er den eneste forskjellen mellom de to appene at man i Vue-appen kan sette inn og trekke ut penger fra en konto, mens man i FX-appen får en konto med et gitt antall penger når man først registrerer seg.

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

## Bygging

Prosjektet er satt opp med **vue-cli** og bygges med **npm**. I tillegg brukes **maven-frontend-plugin** slik at man kan kjøre npm kommandoer gjennom maven.  

### Bygging med maven fra rotmappe

```
mvn install -pl marxbankvue
```

## Komponenter

Alle komponenter som brukes av ulike faner ligger under `src/components`. `Header.vue` og `SideBar.vue` er globale komponenter. Sidebar komponenten brukes for navigering mellom forskjellige faner gjennom "router-links".

## Tilstandshåndtering

Vi har tatt i bruk tilstandshåndtering med **vuex 4**. Tilstanden til appen ligger under `src/store` mappen, og er delt i 4 moduler: accounts, transactions, users og auth. Dette tilsvarer de ulike endpointene i backenden.

### State

Tilstanden til en bestemt modul er definert av grensesnitt i `types.ts` som brukes i **state**-variabelen i `index.ts` i den gitte modulen. F.eks er tilstanden til transactions modulen definert av **TransactionState**- og **Transaction**-grensesnittene under `src/store/modules/transactions/types.ts`.

```typescript
//Transaction-grensesnitt
export interface Transaction {
  readonly id: number;
  from: number;
  to: number;
  amount: number;
  date: string;
}

//TransactionState-grensesnitt
export interface TransactionState {
  transactionStatus: Status;
  transactions: Array<Transaction>;
}
```

### Getters

Man kan få tilgang til tilstanden i en modul gjennom **getters**. F.eks kan man få tak i alle transaksjoner i transactions-modul med **allTransactions**-getteren under `src/store/modules/transactions/getters.ts`.

```typescript
//Får tak i alle transaksjoner ved å returnere state.transactions
allTransactions: (state): Array<Transaction> => state.transactions,
```

For å bruke denne getteren i en vue-komponent, kan man enten kalle på `this.$store.getters.allTransactions()`, eller bruke hjelperen **mapGetters** fra vuex i **computed-properities**.

```typescript
//mapGetters lager en ny property med innholdet
//som ble returnert av allTransactions
computed: {
    ...mapGetters(["allTransactions"]),
  },
```

### Mutations

Mutations brukes for å gjøre bestemte endringer til tilstanden i en modul. Poenget er at de skal være så enkle som mulig, slik at de kan kombineres av en **action**, for mer komplisert logikk. Man kan f.eks bruke **setTransactions**-mutation for å overskrive hele transactions-delen av tilstanden i transactions-modulen. 

```typescript
//Tar inn en ny liste med transaksjoner og overskriver state.transactions
setTransactions: (state, transactions: Array<Transaction>) => {
    state.transactions = transactions;
  },
```

### Actions

For å gjøre kall til backenden bruker vi **axios**. REST-kall gjøres gjennom **actions** i vuex store. F.eks finnes det en action for å få tak i alle transaksjoner til en bruker (fetchTransactions) i `src/store/modules/transactions/actions.ts`.

```typescript
//Eksempel på action
async fetchTransactions({ commit, rootGetters }) {
    commit("setTransactionStatus", "loading");
    await axios
        //Gjør en get-request med axios
      .get(BASE_URL + "/myTransactions", rootGetters.getToken)
      .then((response) => {
        let transactions: Array<Transaction> = [];
        response.data.forEach((element: any) => {
          const newTransaction: Transaction = {
            id: element.id,
            from: element.fromId,
            to: element.recieverId,
            amount: element.amount,
            date: element.transactionDate,
          };
          transactions = [...transactions, newTransaction];
        });
        //Kaller på "setTransactions"-mutation for å mutere tilstand
        commit("setTransactions", transactions);
        commit("setTransactionStatus", "success");
      })
      .catch((err) => {
        commit("setTransactionStatus", "error");
      });
  },
```

## Routing

Vue router brukes for å ha en "single-page-application". De ulike rutene er konfigurert under `src/router` og fanene de fører til ligger under `src/views`. I tillegg er routingen konfigerert med autentisering, slik at man ikke får tilgang til faner med api data uten å først logge inn.

## Styling

Til styling av appen brukes **Tailwind CSS**. Det meste av stylingen til gjenbrukbare klasser ligger i `index.css` i `src` mappa.

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

### Code coverage

Kjør først kommandoen
```
npx jest --coverage
```
Coverage-rapporten finnes under `coverage/Icov-report/index.html`

## Kodekvalitet og formattering

ESlint brukes for kodekvalitet, og Prettier for formattering

### ESlint

**ESlint** er konfigurert gjennom vue-cli-service for skjekking av kodekvalitet.

```
npm run lint
```

### Prettier

**prettier** brukes for formattering av kode. **eslint-config-prettier** brukes for å overstyre formatteringen til ESlint med prettier, slik at ESlint kun har ansvar for kodekvalitet.

```
mvn verify
```

