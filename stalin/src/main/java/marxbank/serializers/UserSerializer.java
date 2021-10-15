package marxbank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.model.Account;
import marxbank.model.User;

public class UserSerializer extends StdSerializer<User> {

    public UserSerializer() {
        this(null);
    }

    public UserSerializer(Class<User> t) {
        super(t);   
    }

    @Override
    public void serialize(User u, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", u.getId());
        gen.writeStringField("username", u.getUsername());
        gen.writeStringField("email", u.getEmail());
        gen.writeStringField("password", u.getPassword());
        gen.writeFieldName("accounts");
        gen.writeStartArray();
        for(Account a : u.getAccounts()) {
            gen.writeStartObject();
            gen.writeStringField("id", a.getId());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
    
}
