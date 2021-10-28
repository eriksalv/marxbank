package marxbank.endpoint;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import marxbank.API.TransactionResponse;
import marxbank.repository.TransactionRepository;


@RestController
@RequestMapping("/transactions")
public class TransactionController {

    private final TransactionRepository transactionRepository;

    @Autowired
    public TransactionController(TransactionRepository transactionRepository) {
        this.transactionRepository = transactionRepository;
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

}
