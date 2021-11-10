---
title: Backend Rest API v1.0.0
language_tabs:
  - shell: Shell
  - http: HTTP
  - javascript: JavaScript
  - ruby: Ruby
  - python: Python
  - php: PHP
  - java: Java
  - go: Go
toc_footers: []
includes: []
search: true
highlight_theme: darkula
headingLevel: 2

---

<!-- Generator: Widdershins v4.0.1 -->

<h1 id="backend-rest-api">Backend Rest API v1.0.0</h1>

> Scroll down for code samples, example requests and responses. Select a language for code samples from the tabs above or the mobile navigation menu.

Base URLs:

* <a href="localhost:8080">localhost:8080</a>

<h1 id="backend-rest-api-default">Default</h1>

## post auth login

`POST /auth/login`

*returns a token and user info on success*

> Body parameter

```json
{
  "username": "string",
  "password": "string"
}
```

<h3 id="post__auth_login-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|body|body|[LogInRequest](#schemaloginrequest)|true|none|

> Example responses

> 200 Response

```json
{
  "token": "string",
  "userResponse": {
    "Id": 0,
    "username": "string",
    "email": "string"
  }
}
```

<h3 id="post__auth_login-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|Succesful login|[LogInResponse](#schemaloginresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|Bad request with too short username or password|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|Failed login with wrong password|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|Failed login with wrong username|Inline|

<h3 id="post__auth_login-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## get auth login

`GET /auth/login`

*checks if a token is valid for a user*

<h3 id="get__auth_login-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
{
  "Id": 0,
  "username": "string",
  "email": "string"
}
```

<h3 id="get__auth_login-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|valid user login|[UserResponse](#schemauserresponse)|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|bad token|Inline|

<h3 id="get__auth_login-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## post auth signup

`POST /auth/signup`

*Signups a new user*

> Body parameter

```json
{
  "username": "string",
  "email": "user@example.com",
  "password": "string"
}
```

<h3 id="post__auth_signup-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|
|body|body|[SignUpRequest](#schemasignuprequest)|true|none|

> Example responses

> 201 Response

```json
{
  "Id": 0,
  "username": "string",
  "email": "string"
}
```

<h3 id="post__auth_signup-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|created a new user|[UserResponse](#schemauserresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|username, email or password was not valid|Inline|
|409|[Conflict](https://tools.ietf.org/html/rfc7231#section-6.5.8)|username was already taken|Inline|

<h3 id="post__auth_signup-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## post auth logout

`POST /auth/logout`

*logs a user out*

<h3 id="post__auth_logout-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
{}
```

<h3 id="post__auth_logout-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|user was logged out and token was removed|Inline|

<h3 id="post__auth_logout-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## get accounts myAccounts

`GET /accounts/myAccounts`

*Gets all accounts for the user*

<h3 id="get__accounts_myaccounts-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
[
  {
    "id": 0,
    "accountNumber": 0,
    "type": "string",
    "userId": 0,
    "name": "string",
    "balance": 0,
    "interestRate": 0
  }
]
```

<h3 id="get__accounts_myaccounts-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|gets all accounts for the user|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="get__accounts_myaccounts-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[AccountResponse](#schemaaccountresponse)]|false|none|none|
|» id|integer(int64)|false|none|id of account|
|» accountNumber|integer(int32)|false|none|unique accountNumber for each account|
|» type|string|false|none|type of account|
|» userId|integer(int64)|false|none|id of user that owns the account|
|» name|string|false|none|name of the account|
|» balance|number|false|none|amount of money in the account|
|» interestRate|number|false|none|interestRate of the account|

<aside class="success">
This operation does not require authentication
</aside>

## post accounts createAccount

`POST /accounts/createAccount`

*creates an account for the user*

> Body parameter

```json
{
  "name": "string",
  "type": "string"
}
```

<h3 id="post__accounts_createaccount-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|
|body|body|[AccountRequest](#schemaaccountrequest)|true|none|

> Example responses

> 201 Response

```json
{
  "id": 0,
  "accountNumber": 0,
  "type": "string",
  "userId": 0,
  "name": "string",
  "balance": 0,
  "interestRate": 0
}
```

<h3 id="post__accounts_createaccount-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|201|[Created](https://tools.ietf.org/html/rfc7231#section-6.3.2)|created a new account|[AccountResponse](#schemaaccountresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|name or type not valid|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="post__accounts_createaccount-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## post accounts deposit

`POST /accounts/deposit`

*deposits money into specified account*

> Body parameter

```json
{
  "amount": 0,
  "accountId": 0
}
```

<h3 id="post__accounts_deposit-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|
|body|body|[DepositWithdrawRequest](#schemadepositwithdrawrequest)|true|none|

> Example responses

> 200 Response

```json
{
  "id": 0,
  "accountNumber": 0,
  "type": "string",
  "userId": 0,
  "name": "string",
  "balance": 0,
  "interestRate": 0
}
```

<h3 id="post__accounts_deposit-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|the money has been deposited|[AccountResponse](#schemaaccountresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|either account doesn't exist or amount is not valid|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="post__accounts_deposit-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## post accounts withdraw

`POST /accounts/withdraw`

*Withdraws money from an account*

> Body parameter

```json
{
  "amount": 0,
  "accountId": 0
}
```

<h3 id="post__accounts_withdraw-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|
|body|body|[DepositWithdrawRequest](#schemadepositwithdrawrequest)|true|none|

> Example responses

> 200 Response

```json
{
  "id": 0,
  "accountNumber": 0,
  "type": "string",
  "userId": 0,
  "name": "string",
  "balance": 0,
  "interestRate": 0
}
```

<h3 id="post__accounts_withdraw-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|the money has been withdrawn from the account|[AccountResponse](#schemaaccountresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|account doesn't exist or amount is not valid|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="post__accounts_withdraw-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

## get transactions myTransactions

`GET /transactions/myTransactions`

*gets all transaction for all of the users accounts*

<h3 id="get__transactions_mytransactions-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
[
  {
    "Id": 0,
    "fromId": 0,
    "reciverId": 0,
    "amount": 0,
    "transactionDate": "2019-08-24"
  }
]
```

<h3 id="get__transactions_mytransactions-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|gets all transactions for users accounts|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="get__transactions_mytransactions-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TransactionResponse](#schematransactionresponse)]|false|none|none|
|» Id|integer(int64)|false|none|id of transaction|
|» fromId|integer(int64)|false|none|id of account that sent money|
|» reciverId|integer(int64)|false|none|id of account that recived money|
|» amount|number|false|none|amount of money transfered|
|» transactionDate|string(date)|false|none|date of the transaction|

<aside class="success">
This operation does not require authentication
</aside>

## get transactions myTransactions {accountId}

`GET /transactions/myTransactions/{accountId}`

*gets transaction for given account*

<h3 id="get__transactions_mytransactions_{accountid}-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|accountId|path|integer(int64)|true|none|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
[
  {
    "Id": 0,
    "fromId": 0,
    "reciverId": 0,
    "amount": 0,
    "transactionDate": "2019-08-24"
  }
]
```

<h3 id="get__transactions_mytransactions_{accountid}-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|found all transactions for account|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|account not found|Inline|

<h3 id="get__transactions_mytransactions_{accountid}-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TransactionResponse](#schematransactionresponse)]|false|none|none|
|» Id|integer(int64)|false|none|id of transaction|
|» fromId|integer(int64)|false|none|id of account that sent money|
|» reciverId|integer(int64)|false|none|id of account that recived money|
|» amount|number|false|none|amount of money transfered|
|» transactionDate|string(date)|false|none|date of the transaction|

<aside class="success">
This operation does not require authentication
</aside>

## get transactions from {accountId}

`GET /transactions/from/{accountId}`

*gets all transactions where given account sent it*

<h3 id="get__transactions_from_{accountid}-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|accountId|path|integer(int64)|true|none|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
[
  {
    "Id": 0,
    "fromId": 0,
    "reciverId": 0,
    "amount": 0,
    "transactionDate": "2019-08-24"
  }
]
```

<h3 id="get__transactions_from_{accountid}-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|found all transactions where account sent them|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|account was not found|Inline|

<h3 id="get__transactions_from_{accountid}-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TransactionResponse](#schematransactionresponse)]|false|none|none|
|» Id|integer(int64)|false|none|id of transaction|
|» fromId|integer(int64)|false|none|id of account that sent money|
|» reciverId|integer(int64)|false|none|id of account that recived money|
|» amount|number|false|none|amount of money transfered|
|» transactionDate|string(date)|false|none|date of the transaction|

<aside class="success">
This operation does not require authentication
</aside>

## get transactions reciever {accountId}

`GET /transactions/reciever/{accountId}`

*gets all transactions where given account reciever it*

<h3 id="get__transactions_reciever_{accountid}-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|accountId|path|integer(int64)|true|none|
|Authorization|header|string(uuid)|false|none|

> Example responses

> 200 Response

```json
[
  {
    "Id": 0,
    "fromId": 0,
    "reciverId": 0,
    "amount": 0,
    "transactionDate": "2019-08-24"
  }
]
```

<h3 id="get__transactions_reciever_{accountid}-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|found all transactions where account recieved them|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|
|404|[Not Found](https://tools.ietf.org/html/rfc7231#section-6.5.4)|account was not found|Inline|

<h3 id="get__transactions_reciever_{accountid}-responseschema">Response Schema</h3>

Status Code **200**

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|*anonymous*|[[TransactionResponse](#schematransactionresponse)]|false|none|none|
|» Id|integer(int64)|false|none|id of transaction|
|» fromId|integer(int64)|false|none|id of account that sent money|
|» reciverId|integer(int64)|false|none|id of account that recived money|
|» amount|number|false|none|amount of money transfered|
|» transactionDate|string(date)|false|none|date of the transaction|

<aside class="success">
This operation does not require authentication
</aside>

## post transactions transfer

`POST /transactions/transfer`

*transfers money between accounts*

> Body parameter

```json
{
  "from": 0,
  "to": 0,
  "amount": 0
}
```

<h3 id="post__transactions_transfer-parameters">Parameters</h3>

|Name|In|Type|Required|Description|
|---|---|---|---|---|
|Authorization|header|string(uuid)|false|none|
|body|body|[TransactionRequest](#schematransactionrequest)|true|none|

> Example responses

> 200 Response

```json
{
  "Id": 0,
  "fromId": 0,
  "reciverId": 0,
  "amount": 0,
  "transactionDate": "2019-08-24"
}
```

<h3 id="post__transactions_transfer-responses">Responses</h3>

|Status|Meaning|Description|Schema|
|---|---|---|---|
|200|[OK](https://tools.ietf.org/html/rfc7231#section-6.3.1)|transfer was successful|[TransactionResponse](#schematransactionresponse)|
|400|[Bad Request](https://tools.ietf.org/html/rfc7231#section-6.5.1)|from account, to account or amount was not valid|Inline|
|403|[Forbidden](https://tools.ietf.org/html/rfc7231#section-6.5.3)|token not valid|Inline|

<h3 id="post__transactions_transfer-responseschema">Response Schema</h3>

<aside class="success">
This operation does not require authentication
</aside>

# Schemas

<h2 id="tocS_LogInRequest">LogInRequest</h2>
<!-- backwards compatibility -->
<a id="schemaloginrequest"></a>
<a id="schema_LogInRequest"></a>
<a id="tocSloginrequest"></a>
<a id="tocsloginrequest"></a>

```json
{
  "username": "string",
  "password": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|username|string|false|none|username of user to be logged in|
|password|string|false|none|password of user to be logged in|

<h2 id="tocS_UserResponse">UserResponse</h2>
<!-- backwards compatibility -->
<a id="schemauserresponse"></a>
<a id="schema_UserResponse"></a>
<a id="tocSuserresponse"></a>
<a id="tocsuserresponse"></a>

```json
{
  "Id": 0,
  "username": "string",
  "email": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|Id|integer(int64)|false|none|id of user that is logged in|
|username|string|false|none|username of user that is logged in|
|email|string|false|none|email of user that is logged in|

<h2 id="tocS_LogInResponse">LogInResponse</h2>
<!-- backwards compatibility -->
<a id="schemaloginresponse"></a>
<a id="schema_LogInResponse"></a>
<a id="tocSloginresponse"></a>
<a id="tocsloginresponse"></a>

```json
{
  "token": "string",
  "userResponse": {
    "Id": 0,
    "username": "string",
    "email": "string"
  }
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|token|string|false|none|autogenerated uuid token to follow the logged in user|
|userResponse|[UserResponse](#schemauserresponse)|false|none|UserResponse that shows the user info|

<h2 id="tocS_SignUpRequest">SignUpRequest</h2>
<!-- backwards compatibility -->
<a id="schemasignuprequest"></a>
<a id="schema_SignUpRequest"></a>
<a id="tocSsignuprequest"></a>
<a id="tocssignuprequest"></a>

```json
{
  "username": "string",
  "email": "user@example.com",
  "password": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|username|string|false|none|username of user|
|email|string(email)|false|none|email of user|
|password|string|false|none|password of user|

<h2 id="tocS_AccountRequest">AccountRequest</h2>
<!-- backwards compatibility -->
<a id="schemaaccountrequest"></a>
<a id="schema_AccountRequest"></a>
<a id="tocSaccountrequest"></a>
<a id="tocsaccountrequest"></a>

```json
{
  "name": "string",
  "type": "string"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|name|string|false|none|name of account to be made|
|type|string|false|none|type of account to be made.|

<h2 id="tocS_AccountResponse">AccountResponse</h2>
<!-- backwards compatibility -->
<a id="schemaaccountresponse"></a>
<a id="schema_AccountResponse"></a>
<a id="tocSaccountresponse"></a>
<a id="tocsaccountresponse"></a>

```json
{
  "id": 0,
  "accountNumber": 0,
  "type": "string",
  "userId": 0,
  "name": "string",
  "balance": 0,
  "interestRate": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|id|integer(int64)|false|none|id of account|
|accountNumber|integer(int32)|false|none|unique accountNumber for each account|
|type|string|false|none|type of account|
|userId|integer(int64)|false|none|id of user that owns the account|
|name|string|false|none|name of the account|
|balance|number|false|none|amount of money in the account|
|interestRate|number|false|none|interestRate of the account|

<h2 id="tocS_DepositWithdrawRequest">DepositWithdrawRequest</h2>
<!-- backwards compatibility -->
<a id="schemadepositwithdrawrequest"></a>
<a id="schema_DepositWithdrawRequest"></a>
<a id="tocSdepositwithdrawrequest"></a>
<a id="tocsdepositwithdrawrequest"></a>

```json
{
  "amount": 0,
  "accountId": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|amount|number|false|none|amount of money to be withdrawn or deposited|
|accountId|integer(int64)|false|none|id of account to either put money into or take it out from|

<h2 id="tocS_TransactionResponse">TransactionResponse</h2>
<!-- backwards compatibility -->
<a id="schematransactionresponse"></a>
<a id="schema_TransactionResponse"></a>
<a id="tocStransactionresponse"></a>
<a id="tocstransactionresponse"></a>

```json
{
  "Id": 0,
  "fromId": 0,
  "reciverId": 0,
  "amount": 0,
  "transactionDate": "2019-08-24"
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|Id|integer(int64)|false|none|id of transaction|
|fromId|integer(int64)|false|none|id of account that sent money|
|reciverId|integer(int64)|false|none|id of account that recived money|
|amount|number|false|none|amount of money transfered|
|transactionDate|string(date)|false|none|date of the transaction|

<h2 id="tocS_TransactionRequest">TransactionRequest</h2>
<!-- backwards compatibility -->
<a id="schematransactionrequest"></a>
<a id="schema_TransactionRequest"></a>
<a id="tocStransactionrequest"></a>
<a id="tocstransactionrequest"></a>

```json
{
  "from": 0,
  "to": 0,
  "amount": 0
}

```

### Properties

|Name|Type|Required|Restrictions|Description|
|---|---|---|---|---|
|from|integer(int64)|false|none|id of account to send money|
|to|integer(int64)|false|none|id of account to recieve money|
|amount|number|false|none|amount of money to be sent|

