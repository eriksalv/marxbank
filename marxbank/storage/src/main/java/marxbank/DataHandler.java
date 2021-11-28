package marxbank;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
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

  private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
  private static final SimpleModule SIMPLE_MODULE = new SimpleModule();

  public static boolean save(ArrayList<User> userList, ArrayList<Account> accountList, 
                              ArrayList<Transaction> transactionList, String path) {
    if (path == null || path.isEmpty() || path.isBlank())
      throw new IllegalArgumentException("Path cannot be null, empty or blank");
    File dataFile = new File(String.format("%s/data.json", path));
    DataManagerWrapper d = new DataManagerWrapper(userList, accountList, transactionList);

    if (!dataFile.exists()) {
      try {
        if (!dataFile.createNewFile()) {
          return false;
        }
      } catch (IOException e) {
        return false;
      }
    }

    FileWriter fw;
    SIMPLE_MODULE.addSerializer(DataManagerWrapper.class, new DataManagerSerializer(OBJECT_MAPPER, SIMPLE_MODULE));
    OBJECT_MAPPER.registerModule(SIMPLE_MODULE);

    try {
      fw = new FileWriter(dataFile, Charset.defaultCharset());
      fw.write(OBJECT_MAPPER.writeValueAsString(d));
      fw.close();
    } catch (IOException e1) {
      return false;
    }

    return true;
  }

  public static boolean parse(String path) {
    SIMPLE_MODULE.addDeserializer(User.class, new UserDeserializer(User.class));
    SIMPLE_MODULE.addDeserializer(Account.class, new AccountDeserializer(Account.class));
    SIMPLE_MODULE.addDeserializer(Transaction.class, new TransactionDeserializer(Transaction.class));
    OBJECT_MAPPER.registerModule(SIMPLE_MODULE);

    File dataFile = new File(String.format("%s/data.json", path));

    if (!dataFile.exists())
      return false;

    JsonNode masterNode;

    try {
      masterNode = OBJECT_MAPPER.readTree(dataFile);
    } catch (IOException e) {
      return false;
    }

    // handle users
    JsonNode node = masterNode.get("users");
    for (JsonNode n : node) {
      User u;
      try {
        u = OBJECT_MAPPER.treeToValue(n, User.class);
        // check if user with id exists
        if (!DataManager.checkIfUserExists(u.getId())) {
          DataManager.addUser(u);
          continue;
        } else if (!DataManager.checkIfUserExists(u.getId())) {
          // delete user with id and replace it with this user
          DataManager.deleteUser(DataManager.getUser(u.getId()));
          DataManager.addUser(u);
        }
      } catch (JsonProcessingException e) {
        return false;
      }
    }
    // handle accounts
    node = masterNode.get("accounts");

    for (JsonNode a : node) {
      try {
        Account acc = OBJECT_MAPPER.treeToValue(a, Account.class);
        if (!DataManager.checkIfAccountExists(acc.getId())) {
          DataManager.addAccount(acc);
          continue;
        } else if (!DataManager.checkIfAccountExists(acc)) {
          // delete account that exists witht the same id and replace it with new one
          DataManager.deleteAccount(DataManager.getAccount(acc.getId()));
          DataManager.addAccount(acc);
        }
      } catch (JsonProcessingException e) {
        return false;
      }
    }

    // handle transactions
    node = masterNode.get("transactions");

    for (JsonNode t : node) {
      try {
        Transaction transaction = OBJECT_MAPPER.treeToValue(t, Transaction.class);
        if (!DataManager.checkIfTransactionExists(transaction.getId())) {
          DataManager.addTransaction(transaction);
          continue;
        }
      } catch (JsonProcessingException e) {
        return false;
      }
    }

    return true;
  }
}
