package it1901;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import it1901.deserializers.AccountDeserializer;
import it1901.deserializers.TransactionDeserializer;
import it1901.deserializers.UserDeserializer;
import it1901.serializers.DataManagerSerializer;
import it1901.wrappers.DataManagerWrapper;
import it1901.model.Account;
import it1901.model.Transaction;
import it1901.model.User;

public class DataHandler {

    public static boolean save(List<User> u, List<Account> a, List<Transaction> t, String path) {
        if(path == null || path == "") throw new IllegalArgumentException("Path cannot be null or empty");
        DataManagerWrapper dm = new DataManagerWrapper(u, a, t);
        File dataFile = new File(String.format("%s/data.json", path));
        if(!dataFile.exists()) {
            try {
                if(!dataFile.createNewFile()) {
                    return false;
                }
            } catch (IOException e) {
                // TODO legg til egne exceptions
                return false;
            }
        }
        FileWriter fw;
        try {
            fw = new FileWriter(dataFile);
        } catch (IOException e1) {
            // TODO legg til egne exceptions
            return false;
        }
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(DataManagerWrapper.class, new DataManagerSerializer());
        objectMapper.registerModule(module);

        try {
            fw.write(objectMapper.writeValueAsString(dm));
            fw.close();
        } catch (JsonProcessingException e) {
            // TODO legge til egne exceptions
            return false;
        } catch (IOException e) {
            // TODO legge til egne exceptions
            return false;
        }

        return true;
    }

    public static boolean parse(DataManager dm, String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addDeserializer(User.class, new UserDeserializer(User.class, dm));
        module.addDeserializer(Account.class, new AccountDeserializer(Account.class));
        module.addDeserializer(Transaction.class, new TransactionDeserializer(Transaction.class, dm));
        objectMapper.registerModule(module);

        File dataFile = new File(String.format("%s/data.sjon", path));

        if(!dataFile.exists()) return true;

        JsonNode masterNode;

        try {
            masterNode = objectMapper.readTree(dataFile);
        } catch (IOException e) {
            // TODO legg til egne exceptions
            return false;
        }

        // handle users
        JsonNode node = masterNode.get("users");
        for(JsonNode n : node) {
            User u;
            try {
                u = objectMapper.treeToValue(n, User.class);
            } catch (JsonProcessingException e) {
                // TODO legg til egne exceptions
                return false;
            }
            // check if user with id exists
            if(!dm.checkIfUserExists(u.getId())) {
                dm.addUser(u);
                continue;
            } else if(!dm.checkIfUserExists(u.getId())) {
                dm.updateUser(u.getId(), u);
            }
        }
        // handle accounts
        node = masterNode.get("accounts");

        for(JsonNode a : node) {
            try {
                Account acc = objectMapper.treeToValue(a, Account.class);
                if(!dm.checkIfAccountExists(acc.getId())) {
                    dm.addAccount(acc);
                    continue;
                } else if(!dm.checkIfAccountExists(acc)) {
                    dm.updateAccount(acc.getId(), acc);
                }
            } catch (JsonProcessingException e) {
                // TODO legg til egne exceptions her
                return false;
            }
        }

        // handle transactions
        node = masterNode.get("transactions");

        for(JsonNode t : node) {
            try {
                Transaction transaction = objectMapper.treeToValue(t, Transaction.class);
                if(!dm.checkIfTransactionExists(transaction.getId())) {
                    dm.addTransaction(transaction);
                    continue;
                }
            } catch (JsonProcessingException e) {
                // TODO legg til egne exceptions her
                return false;
            }
        }

        return true;
    }

    public static void main(String... args) {
        // DataManager dm = new DataManager("data");
        // User user = new User("uniqueId", "testUsername", "test@email.com", "password", dm);
        // Account a = new SavingsAccount("test1", user, 5.0, dm);
        // a.deposit(5000.0);
        // Account a2 = new SavingsAccount("test2", user, 10.0, dm);
        // Transaction t = new Transaction("test", a, a2, 500, dm, true);
        
        // DataHandler.save(dm.getUsers(), dm.getAccounts(), dm.getTransactions(), "data");
    }
}
