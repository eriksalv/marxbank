package marxbank.wrappers;

import java.util.ArrayList;
import java.util.List;

import marxbank.model.Account;

public class ArrayAccountWrapper extends ArrayList<Account> {
    
    public ArrayAccountWrapper(List<Account> accounts) {
        super(accounts);
    }

}
