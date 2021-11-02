package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.API.TransactionResponse;
import marxbank.model.Account;
import marxbank.repository.AccountRepository;
import marxbank.repository.TransactionRepository;
import marxbank.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AccountRepository accountRepository;
    private final TransactionService transactionService;
    @Autowired
    public TransactionController(TransactionRepository transactionRepository, TransactionService transactionService, AccountRepository accountRepository){
        this.transactionRepository = transactionRepository;
        this.transactionService = transactionService;
        this.accountRepository = accountRepository;
    }

    @GetMapping("/")
    @Transactional
    public List<TransactionResponse> findAll() {
        List<TransactionResponse> result = new ArrayList<TransactionResponse>();
        transactionRepository.findAll().forEach(u -> result.add(new TransactionResponse(u)));
        return result;
    }

    @GetMapping("/transaction/{id}")
    @Transactional
    public TransactionResponse findById(@PathVariable Long id) throws Exception{
        return new TransactionResponse(transactionRepository.findById(id).orElseThrow(Exception::new));
    }

    @GetMapping("/from/{fromId}")
    @Transactional
    public ResponseEntity<ArrayList<TransactionResponse>> findByFrom_Id(@PathVariable Long fromId){
        
        Optional<Account> OptionalAccount = accountRepository.findById(fromId);
        if (!OptionalAccount.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Account account = OptionalAccount.get();
    
        if (account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();
        transactionRepository.findByFrom_Id(fromId).get().forEach(e -> transactions.add(new TransactionResponse(e)));
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("/reciever/{recieverId}")
    @Transactional
    public ResponseEntity<ArrayList<TransactionResponse>> findByReciever_Id(@PathVariable Long recieverId){
        
        Optional<Account> OptionalAccount = accountRepository.findById(recieverId);
        if (!OptionalAccount.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Account account = OptionalAccount.get();
    
        if (account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();

        transactionRepository.findByReciever_Id(recieverId).get().forEach(e -> transactions.add(new TransactionResponse(e)));
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

    @GetMapping("/myTransactions/{accountId}")
    @Transactional
    public ResponseEntity<ArrayList<TransactionResponse>> findByAccount_Id(@PathVariable Long accountId){
       
        Optional<Account> OptionalAccount = accountRepository.findById(accountId);
        if (!OptionalAccount.isPresent()) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        Account account = OptionalAccount.get();

        if (account == null) return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);

        ArrayList<TransactionResponse> transactions = new ArrayList<TransactionResponse>();

        transactionService.getTransactionsForAccount(account.getId()).forEach(e -> transactions.add(new TransactionResponse(e)));
        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }


}
