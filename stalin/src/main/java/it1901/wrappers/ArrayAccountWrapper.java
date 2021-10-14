package it1901.wrappers;

import java.util.ArrayList;
import java.util.List;

import it1901.model.Account;

public class ArrayAccountWrapper extends ArrayList<Account> {
    
    public ArrayAccountWrapper(List<Account> accounts) {
        super(accounts);
    }

}
