package it1901.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it1901.Account;
import it1901.AccountFactory;
import it1901.DataManager;
import it1901.User;
import it1901.SavingsAccount;

public class AccountDeserializer extends StdDeserializer<Account> {

    private DataManager dm;

    public AccountDeserializer(DataManager dm) {
        this(null, dm);
    }

    public AccountDeserializer(Class<?> vc, DataManager dm) {
        super(vc);
        this.dm = dm;
    }

    @Override
    public Account deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        AccountType type = AccountType.valueOf(node.get("type").asText());
        if(!dm.checkIfUserExists(node.get("user").get("id").asText())) throw new IllegalStateException("user doesn't exist");
        User owner = dm.getUser(node.get("user").get("id").asText());
        Account account = AccountFactory.createFrom(type.getTypeString(), node.get("id").asText(), owner, dm, node.get("name").asText(), node.get("accountNumber").asInt());
        if (account==null) {
            return null;
        }
        account.setBalance(node.get("balance").asDouble());
        dm.updateUser(owner.getId(), owner);
        return account;
    }
    
}
