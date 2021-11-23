# api
API-modulen er en modul for programmeringsgrensesnittet vårt(Application Programming Interface).

## Testing av API
Modulen for API er ment for å være et bindeledd mellom backenden og frontenden, og skal holde på informasjon frontenden trenger for å gi tilfredsstillende respons til brukeren. Den inneholder ikke logikk utover 'getters', 'setters' og omfattende testing av denne koden ansees som unødvendig.

## Kort om de forskjellige Response-klassene
Det er 4 forskjellige response-klasser, som holder på den mest nødvendige informasjonen brukeren må få som respons i forskjellige tilfeller.

* AccountResponse: En klasse som inneholder informasjonen som er umiddelbart nødvendig for brukeren å se når de ser over en eller flere kontoer, nemlig: kontoens id, kontonummer, kontonavn, type, brukerens id og saldo. I tillegg kommer settere og gettere.

* LogInResponse: En klasse som inneholder den nødvendige informasjonen for responsen brukeren får se når de logger inn. Det handler om godkjenning, som token holder på informasjon om, og inneholder en UserResponse for brukeren om forespørselen om å logge inn blir godkjent.

* TransactionResponse: En klasse som holder den viktigste informasjonen om overføringer, som overføringens unike id, hvem som har sendt penger, hvem som har mottatt penger, beløpet og datoen overføringen ble gjennomført.

* UserResponse: En klasse som innholder den helt nødvendige informasjonen om en bruker, nemlig den unike id-en, brukernavnet og emailen.

## Kort om de forskjellige Request-klassene
Det er også 3 requestklasser, som holder informasjonen som må fomidles fra bruker til backenden for å kunne utføre forskjellige oppgaver.

* AccountRequest: En klasse som holder på informasjonen som kreves av brukeren for å opprette en ny konto, nemlig type konto og kontonavn. 

* LogInRequest: En klasse som holder på den nødvendige informasjonen fra brukeren i en forespørsel om å logge inn i banken og få tilgang til en brukers informasjon. Feltene er brukernavn og passord.

* SignUpRequest: En klasse som inneholder den viktigste informasjonen en bruker må oppgi om en bruker ønsker å opprette en bruker i banken. Disse er brukernavn, passord og email.