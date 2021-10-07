package it1901.util;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import it1901.DataManager;
import it1901.User;

public class UserDeserializer extends StdDeserializer<User> {

    private DataManager dm;

    public UserDeserializer(DataManager dm) {
        this(null, dm);
    }

    public UserDeserializer(Class<?> vc, DataManager dm) {
        super(vc);
        this.dm = dm;
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        return new User(node.get("id").asText(), node.get("username").asText(), node.get("email").asText() ,node.get("password").asText(), dm, false);
    }
    
}
