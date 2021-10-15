package marxbank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import marxbank.deserializers.AccountDeserializer;
import marxbank.deserializers.TransactionDeserializer;
import marxbank.deserializers.UserDeserializer;
import marxbank.serializers.DataManagerSerializer;
import marxbank.wrappers.DataManagerWrapper;
import marxbank.model.Account;
import marxbank.model.Transaction;
import marxbank.model.User;

public class DataHandler {

    public static boolean save(DataManager dm, String path) {
        if(path == null || path == "") throw new IllegalArgumentException("Path cannot be null or empty");
        DataManagerWrapper d = new DataManagerWrapper(dm);
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
            fw.write(objectMapper.writeValueAsString(d));
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
        module.addDeserializer(User.class, new UserDeserializer(User.class));
        module.addDeserializer(Account.class, new AccountDeserializer(Account.class));
        module.addDeserializer(Transaction.class, new TransactionDeserializer(Transaction.class));
        objectMapper.registerModule(module);

        ArrayList<User> uList = new ArrayList<User>();
        ArrayList<Account> aList = new ArrayList<Account>();
        ArrayList<Transaction> tList = new ArrayList<Transaction>();

        File dataFile = new File(String.format("%s/data.json", path));

        if(!dataFile.exists()) return false;

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
                uList.add(u);
                // check if user with id exists
                if(!dm.checkIfUserExists(u.getId())) {
                    dm.addUser(u);
                    continue;
                } else if(!dm.checkIfUserExists(u.getId())) {
                    // delete user with id and replace it with this user
                    dm.deleteUser(dm.getUser(u.getId()));
                    dm.addUser(u);
                }
            } catch (JsonProcessingException e) {
                // TODO legg til egne exceptions
                return false;
            }
        }
        // handle accounts
        node = masterNode.get("accounts");

        for(JsonNode a : node) {
            try {
                Account acc = objectMapper.treeToValue(a, Account.class);
                aList.add(acc);
                if(!dm.checkIfAccountExists(acc.getId())) {
                    dm.addAccount(acc);
                    continue;
                } else if(!dm.checkIfAccountExists(acc)) {
                    // delete account that exists witht the same id and replace it with new one
                    dm.deleteAccount(dm.getAccount(acc.getId()));
                    dm.addAccount(acc);
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
                tList.add(transaction);
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
}
