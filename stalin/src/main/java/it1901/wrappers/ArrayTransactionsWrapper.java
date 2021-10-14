package it1901.wrappers;

import java.util.ArrayList;
import java.util.List;

import it1901.model.Transaction;

public class ArrayTransactionsWrapper extends ArrayList<Transaction> {
    
    public ArrayTransactionsWrapper(List<Transaction> transactions) {
        super(transactions);
    }

}
