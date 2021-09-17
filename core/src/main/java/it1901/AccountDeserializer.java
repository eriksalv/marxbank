package it1901;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it1901.Account.Type;

class AccountDeserializer extends StdDeserializer<List<Account>> {

        public AccountDeserializer() {
            this(null);
        }

        public AccountDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public List<Account> deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            
            ArrayList<Account> accounts = new ArrayList<Account>();

            JsonNode node = jp.getCodec().readTree(jp);
            node.forEach(e -> {
                Type type = Type.valueOf(e.get("type").asText());
                if(type == Type.SAVING) {
                    SavingsAccount account = new SavingsAccount();
                    account.setId(e.get("id").asText());
                    account.setInterestRate(e.get("interestRate").asDouble());
                    account.setBalance(e.get("balance").asDouble());
                    accounts.add(account);
                }
            });
            
            return accounts;
        }
    }