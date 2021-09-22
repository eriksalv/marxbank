# Marxbank

[![pipeline status](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/badges/43-legge-til-code-coverage-report-til-main-readme/pipeline.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/-/commits/master)


[![coverage report](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/badges/master/coverage.svg)](https://gitlab.stud.idi.ntnu.no/it1901/groups-2021/gr2127/it1901-prosjekt/-/commits/master) 

Dette er en app utviklet i emnet IT1901 høsten 2021. Gruppen består av 4 studenter. Appen er en bank-app som har vanlige bankfunksjoner som å overføre penger, sette inn penger, flytte penger mellom sine egne kontoer, se de forskjellige type kontoene de har og lage nye kontoer.

# Hvordan å kjøre appen

Først kjør

``` mvn install ```

så for å starte frontenden kjør

``` mvn javafx:run -pl marxbankfx ```

# Jacoco code coverage

### Installasjon

Åpne terminalen i VSCode og kjør ` mvn install ` og ` mvn clean jacoco:prepare-agent install jacoco:report `

### For å se rapporten

Finn ` index.html ` under target/site (alle modulene har en egen target-mappe. Velg den modulen du vil ha rapport fra). Kopier stien til filen og lim den inn i nettleser.





