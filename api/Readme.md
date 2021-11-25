# api
API-modulen er en modul for programmeringsgrensesnittet vårt(Application Programming Interface). Modulen for API er ment for å være et bindeledd mellom backenden og frontenden, og skal holde på informasjon frontenden trenger for å gi tilfredsstillende respons til brukeren. Klassene, response- og request-klasser, innholder felter, konstruktører og gettere/settere. Alle klassene inneholder en tom konstruktør ettersom SpringBoot krever dette for riktig funksjonalitet.

## Testing av API
Klassene i modulen inneholder ikke logikk utover 'getters', 'setters' og 'toString' i metodene, og omfattende testing av denne koden ansees derfor som unødvendig.

## Kort om de forskjellige Response-klassene
Det er 4 forskjellige response-klasser, som holder på den mest nødvendige informasjonen brukeren må få som respons i forskjellige tilfeller.

* AccountResponse: En klasse som inneholder informasjonen som er umiddelbart nødvendig for brukeren å se når de ser over en eller flere kontoer, nemlig: kontoens id, kontonummer, kontonavn, type, brukerens id og saldo. I tillegg kommer settere og gettere.

* LogInResponse: En klasse som inneholder den nødvendige informasjonen for responsen brukeren får se når de logger inn. Det handler om godkjenning, som token holder på informasjon om, og inneholder en UserResponse for brukeren om forespørselen om å logge inn blir godkjent.

* PublicAccountResponse: En klasse som kan brukes som et alternativ til AccountResponse, som er litt mer privat, ved å vise bare id, kontonummer, brukerId, kontotype og kontonavn.

* TransactionResponse: En klasse som holder den viktigste informasjonen om overføringer, som overføringens unike id, hvem som har sendt penger, hvem som har mottatt penger, beløpet og datoen overføringen ble gjennomført.

* UserResponse: En klasse som innholder den helt nødvendige informasjonen om en bruker, nemlig den unike id-en, brukernavnet og emailen.

## Kort om de forskjellige Request-klassene
Det er også 3 requestklasser, som holder informasjonen som må fomidles fra bruker til backenden for å kunne utføre forskjellige oppgaver.

* AccountRequest: En klasse som holder på informasjonen som kreves av brukeren for å opprette en ny konto, nemlig type konto og kontonavn. 

<<<<<<< HEAD
* DepositWithdrawRequest: En klasse som holder på informasjonen fra brukeren om de ønsker å ta ut eller sette inn penger på en konto, og dermed har felter som omhandler beløp og id-en til kontoen.

* EditUserRequest: En klasse som holder på informasjon fra brukeren om de ønsker å redigere profilen sin i banken. Feltene er brukernavn, passord, gammelt passord og email. 

=======
>>>>>>> master
* LogInRequest: En klasse som holder på den nødvendige informasjonen fra brukeren i en forespørsel om å logge inn i banken og få tilgang til en brukers informasjon. Feltene er brukernavn og passord.

* SignUpRequest: En klasse som inneholder den viktigste informasjonen en bruker må oppgi om en bruker ønsker å opprette en bruker i banken. Disse er brukernavn, passord og email.