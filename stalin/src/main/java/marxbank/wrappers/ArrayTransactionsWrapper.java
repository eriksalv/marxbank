package marxbank.wrappers;

import java.util.ArrayList;
import java.util.List;

import marxbank.model.Transaction;

public class ArrayTransactionsWrapper extends ArrayList<Transaction> {
    
    public ArrayTransactionsWrapper(List<Transaction> transactions) {
        super(transactions);
    }

}
