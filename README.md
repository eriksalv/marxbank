# Marxbank

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt)


[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/badges/master/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/-/commits/master) 


[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/badges/master/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/master) 

Dette er en app utviklet i emnet IT1901 høsten 2021. Gruppen består av 4 studenter. Appen er en bank-app som har vanlige bankfunksjoner som å overføre penger, flytte penger mellom sine egne kontoer, se de forskjellige type kontoene de har og lage nye kontoer.

# Innhold 

### Kodingsprosjektet

Kodingsprosjektet består foreløpig av to moduler: core og marxbankfx. Se egne readmes i disse mappene for mer detaljer om hva de består av.

# Hvordan kjøre appen

Først kjør

``` mvn install ```

så for å starte frontenden kjør

``` mvn javafx:run -pl marxbankfx ```

# Jacoco code coverage

### Installasjon

Åpne terminalen i VSCode og kjør ` mvn install ` og ` mvn clean jacoco:prepare-agent install jacoco:report `

### For å se rapporten

Finn ` index.html ` under target/site (alle modulene har en egen target-mappe. Velg den modulen du vil ha rapport fra). Kopier stien til filen og lim den inn i nettleser.

### JSON datastruktur

For lagring lokalt av dataen til brukeren, kontoer og overføringer blir lagret i en json struktur. Strukturen til dataen er slik som vist i diagrammet under. 

Objektene lagres i en array som kalles da `users` for brukere, `accounts` for kontoer og `transactions` for overføringer.
![JSON struktur](/diagrammer/marxBankJsonStruktur.svg)

En bruker lagres med disse datene: `id`, `username`, `email`, `password` som er kryptert, og `accounts` som er et array med id-er til kontoene.

En konto lagres med disse dataene: `id`, `user` som er id-en til brukeren som eier kontoen, `accountNumber`, `name`, `transactions` som er et array med id-er til overføringene gjort på kontoen (det er da både de som er sendt til og fra), `balance`, `type`, `numberOfTransactions`.

En overføring lagres med disse dataene: `id`, `from` som er id-en kontoen overføringen er sent fra, `reciever` som er id-en til kontoen overføringer er sent til, `amount`, og `dateString`.





