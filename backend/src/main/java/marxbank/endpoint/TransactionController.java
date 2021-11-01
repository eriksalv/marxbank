package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.API.TransactionResponse;
import marxbank.model.User;
import marxbank.repository.TransactionRepository;
import marxbank.service.AuthService;
import marxbank.service.TransactionService;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;
    private final AuthService authService;
    private final TransactionService transactionService;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository, AuthService authService, TransactionService transactionService) {
        this.transactionRepository = transactionRepository;
        this.authService = authService;
        this.transactionService = transactionService;
    }

    @GetMapping
    @Transactional
    public List<TransactionResponse> findAll() {
        List<TransactionResponse> result = new ArrayList<TransactionResponse>();
        transactionRepository.findAll().forEach(u -> result.add(new TransactionResponse(u)));
        return result;
    }

    @GetMapping("/{id}")
    @Transactional
    public TransactionResponse findById(@PathVariable Long id) throws Exception{
        return new TransactionResponse(transactionRepository.findById(id).orElseThrow(Exception::new));
    }

    @GetMapping("/myTransactions")
    @Transactional
    public ResponseEntity<List<TransactionResponse>> findAllTransactionForUser(@RequestHeader(name = "Authorization", required = false) @Nullable String token) {

        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        User user = authService.getUserFromToken(token);

        if (user == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body(null);

        List<TransactionResponse> transactions = this.transactionService.getTransactionForUser(user.getId()).stream().map(t -> new TransactionResponse(t)).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(transactions);
    }

}
