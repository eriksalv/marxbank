package it1901;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it1901.Account.Type;

class AccountDeserializer extends StdDeserializer<Account> {

        public AccountDeserializer() {
            this(null);
        }

        public AccountDeserializer(Class<?> vc) {
            super(vc);
        }

        @Override
        public Account deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
            JsonNode node = jp.getCodec().readTree(jp);
            Type type = Type.valueOf(node.get("Type").asText());


            if(type == Type.SAVING) {
                SavingsAccount account = null;
            }

            

            // JsonNode node = jp.getCodec().readTree(jp);
            // String id = node.get("id").asText();
            // double balance = node.get("balance").asDouble();
            // double interestRate = node.get("interestRate").asDouble();

            return null;
        }
    }