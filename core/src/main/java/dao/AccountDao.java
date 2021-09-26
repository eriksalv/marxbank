package dao;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import it1901.Account;

public class AccountDao implements Dao<Account> {

    private List<Account> accounts = new ArrayList<>();
    
    public AccountDao() {
        
    }
    
    @Override
    public Optional<Account> get(String id) {
        return accounts.stream().filter(a -> a.getId()==id).findFirst();
    }
    
    @Override
    public List<Account> getAll() {
        return new ArrayList<>(accounts);
    }
    
    @Override
    public void save(Account acc) {
        accounts.add(acc);
    }
    
    @Override
    public void update(Account acc, String[] params) {
        if (accounts.contains(acc)) {
            delete(acc);
        }
        acc.setName(params[0]);
        acc.setBalance(Double.parseDouble(params[1]));
        
        accounts.add(acc);
    }
    
    @Override
    public void delete(Account acc) {
        accounts.remove(acc);
    }
}
