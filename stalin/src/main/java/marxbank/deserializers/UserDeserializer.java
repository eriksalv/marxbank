package marxbank.deserializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;

import marxbank.model.User;

public class UserDeserializer extends StdDeserializer<User> {


    public UserDeserializer() {
        this(null);
    }

    public UserDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public User deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        
        JsonNode node = jp.getCodec().readTree(jp);
        
        return new User(node.get("id").asLong(), node.get("username").asText(), node.get("email").asText(), node.get("password").asText());
    }
    
}
