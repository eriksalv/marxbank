# Marxbank

[![Gitpod Ready-to-Code](https://img.shields.io/badge/Gitpod-Ready--to--Code-blue?logo=gitpod)](https://gitpod.stud.ntnu.no/#https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/gr2127)


[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/gr2127/badges/master/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/-/commits/master) 


[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/gr2127/badges/master/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/gr2127/master) 

Dette er en app utviklet i emnet IT1901 høsten 2021. Gruppen består av 4 studenter. Appen er en bank-app som har vanlige bankfunksjoner som å overføre penger, flytte penger mellom sine egne kontoer, se de forskjellige type kontoene de har og lage nye kontoer.

> Class struggle is inevitable in Object-Oriented Programming
>
> -- <cite>Karl Marx</cite>

## Pakkediagram av løsning

![pakkediagram av løsning](diagrammer/pakkediagram.svg)

## Innhold

### Kodingsprosjektet

Kodingsprosjektet består av fem moduler: core, backend, storage, marxbankfx og marxbankvue. Se egne readmes i disse mappene for mer detaljer om hva de består av.

## Hvordan kjøre appen

Først kjør

``` mvn install ```

Så start Rest-serveren med

``` mvn spring-boot:run -pl backend ``` 

så kan du enten starte webapplikasjonen med

```mvn frontend:install-node-and-npm frontend:npm@"npm install" frontend:npm@"npm serve" -pl marxbankvue```

eller starte javafx med

``` mvn javafx:run -pl marxbankfx ```

Alternativt kan du starte både Rest-serveren og web-klienten samtidig ved å kjøre `./serve-app.sh` i rotmappa

## Jacoco code coverage

### Installasjon

Åpne terminalen i VSCode og kjør ` mvn install ` og ` mvn clean jacoco:prepare-agent install jacoco:report `

### For å se rapporten

Finn ` index.html ` under target/site (alle modulene har en egen target-mappe. Velg den modulen du vil ha rapport fra). Kopier stien til filen og lim den inn i nettleser.

## JSON datastruktur

For å se hvordan vi lagrer dataen vår lokalt med json, se [Readme i storage](storage/Readme.md#lagring-i-Json)
