package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import marxbank.API.TransactionRequest;
import marxbank.API.TransactionResponse;
import marxbank.model.Account;
import marxbank.repository.AccountRepository;
import marxbank.repository.TransactionRepository;
import marxbank.model.User;
import marxbank.service.AccountService;
import marxbank.service.AuthService;
import marxbank.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

  private final TransactionRepository transactionRepository;
  private final AuthService authService;
  private final TransactionService transactionService;
  private final AccountRepository accountRepository;
  private final AccountService accountService;

  @Autowired
  public TransactionController(TransactionRepository transactionRepository, AuthService authService,
      TransactionService transactionService, AccountRepository accountRepository,
      AccountService accountService) {
    this.transactionRepository = transactionRepository;
    this.authService = authService;
    this.transactionService = transactionService;
    this.accountRepository = accountRepository;
    this.accountService = accountService;
  }

  /**
   * finds all transactions for account that sent money
   * 
   * @param fromId id of account
   * @param token of user
   * @return a response with body of a list of transaction and code of 200
   * @return a response with null body and code 401 if token is invalid or account is not the users
   * @return a resposne with null body and code 404 if account is not found
   */
  @GetMapping("/from/{fromId}")
  @Transactional
  public ResponseEntity<ArrayList<TransactionResponse>> findByFrom_Id(@PathVariable Long fromId,
      @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

    if (token == null || authService.getUserFromToken(token) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    User user = this.authService.getUserFromToken(token);

    Optional<Account> optionalAccount = accountRepository.findById(fromId);
    if (!optionalAccount.isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");
    Account account = optionalAccount.get();

    if (user.getId() != account.getUser().getId())
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

    ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();
    transactionRepository.findByFrom_Id(fromId).get()
        .forEach(e -> transactions.add(new TransactionResponse(e)));
    return ResponseEntity.status(HttpStatus.OK).body(transactions);
  }

  /**
   * finds all transactions for account that recieved money
   * 
   * @param recieverId id of account
   * @param token of user
   * @return a response with body of a list of transaction and code of 200
   * @return a response with null body and code 401 if token is invalid or account is not the users
   * @return a resposne with null body and code 404 if account is not found
   */
  @GetMapping("/reciever/{recieverId}")
  @Transactional
  public ResponseEntity<ArrayList<TransactionResponse>> findByReciever_Id(
      @PathVariable Long recieverId,
      @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

    if (token == null || authService.getUserFromToken(token) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    User user = this.authService.getUserFromToken(token);

    if (!accountRepository.findById(recieverId).isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");

    if (!accountService.checkIfUserOwnsAccount(user.getId(), recieverId))
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

    ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();

    transactionRepository.findByReciever_Id(recieverId).get()
        .forEach(e -> transactions.add(new TransactionResponse(e)));
    return ResponseEntity.status(HttpStatus.OK).body(transactions);
  }

  /**
   * finds all transactions an account has made
   * 
   * @param accountId id of account
   * @param token for user
   * @return a response with body of a list of transaction and code of 200
   * @return a response with null body and code 401 if token is invalid or account is not the users
   * @return a resposne with null body and code 404 if account is not found
   */
  @GetMapping("/myTransactions/{accountId}")
  @Transactional
  public ResponseEntity<ArrayList<TransactionResponse>> findByAccount_Id(
      @PathVariable Long accountId,
      @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

    if (token == null || authService.getUserFromToken(token) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    User user = this.authService.getUserFromToken(token);

    Optional<Account> OptionalAccount = accountRepository.findById(accountId);
    if (!OptionalAccount.isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");
    Account account = OptionalAccount.get();

    if (account == null)
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Account doesn't exist");

    if (user.getId() != account.getUser().getId())
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

    ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();

    transactionService.getTransactionsForAccount(account.getId())
        .forEach(e -> transactions.add(new TransactionResponse(e)));
    return ResponseEntity.status(HttpStatus.OK).body(transactions);
  }

  /**
   * Gets all transactions for user without duplicates
   * 
   * @param token for user
   * @return a response with body of a list of transaction and code of 200
   * @return a response with null body and code 401 if token is invalid or account is not the users
   */
  @GetMapping("/myTransactions")
  @Transactional
  public ResponseEntity<List<TransactionResponse>> findAllTransactionForUser(
      @RequestHeader(name = "Authorization", required = false) @Nullable String token) {

    if (token == null || authService.getUserFromToken(token) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    User user = authService.getUserFromToken(token);

    List<TransactionResponse> transactions =
        this.transactionService.getTransactionForUser(user.getId()).stream()
            .map(t -> new TransactionResponse(t)).collect(Collectors.toList());

    return ResponseEntity.status(HttpStatus.OK).body(transactions);
  }

  /**
   * Transfers money between accounts
   * 
   * @param token for user
   * @param request transfer request data
   * @return a response with body of a transaction and a status code of 200
   * @return a response with null body and code 401 if token is invalid
   * @return a resposne with null body and code 400 the request is not valid
   */
  @PostMapping("/transfer")
  @Transactional
  public ResponseEntity<TransactionResponse> transferBetweenAccounts(
      @RequestHeader(name = "Authorization", required = false) @Nullable String token,
      @RequestBody TransactionRequest request) {

    if (request == null || request.getFrom() == null || request.getTo() == null) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error: Must fill out sender and reciever accounts");
    }

    if (token == null || authService.getUserFromToken(token) == null) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");
    }

    User user = this.authService.getUserFromToken(token);

    if (!this.accountRepository.findById(request.getFrom()).isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Sender account doesn't exist");
    if (!this.accountRepository.findById(request.getTo()).isPresent())
      throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Reciever account doesn't exist");

    if (!this.accountService.checkIfUserOwnsAccount(user.getId(), request.getFrom()))
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized access");

    TransactionResponse t;
    try {
      t = this.transactionService.resolveTransactionRequest(request);
    } catch (IllegalArgumentException | IllegalStateException e) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage());
    }

    return ResponseEntity.status(HttpStatus.OK).body(t);
  }

}
