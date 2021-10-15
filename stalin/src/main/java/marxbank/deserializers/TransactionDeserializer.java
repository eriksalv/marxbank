package marxbank.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import marxbank.DataManager;
import marxbank.model.Account;
import marxbank.model.Transaction;

public class TransactionDeserializer extends StdDeserializer<Transaction> {

    private Account from;
    private Account reciever;

    public TransactionDeserializer() {
        this(null);
    }

    public TransactionDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Transaction deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        JsonNode node = jp.getCodec().readTree(jp);
        try {
            if(!DataManager.manager().checkIfAccountExists(node.get("from").get("id").asText()) || !DataManager.manager().checkIfAccountExists(node.get("reciever").get("id").asText())) throw new IllegalStateException("Accounts doesn't exist");
            from = DataManager.manager().getAccount(node.get("from").get("id").asText());
            reciever = DataManager.manager().getAccount(node.get("reciever").get("id").asText());
        } catch (NullPointerException e) {
            if(!DataManager.manager().checkIfAccountExists(node.get("from").asText()) || !DataManager.manager().checkIfAccountExists(node.get("reciever").asText())) throw new IllegalStateException("Accounts doesn't exist");
            from = DataManager.manager().getAccount(node.get("from").asText());
            reciever = DataManager.manager().getAccount(node.get("reciever").asText());
        }
        return new Transaction(node.get("id").asText(), from, reciever, node.get("amount").asDouble(), node.get("dateString").asText(), false, true);
        
    }
    
}
