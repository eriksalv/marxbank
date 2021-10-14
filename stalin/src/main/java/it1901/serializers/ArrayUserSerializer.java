package it1901.serializers;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import it1901.model.User;
import it1901.wrappers.ArrayUserWrapper;

public class ArrayUserSerializer extends StdSerializer<ArrayUserWrapper>{

    public ArrayUserSerializer() {
        this(null);
    }

    public ArrayUserSerializer(Class<ArrayUserWrapper> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayUserWrapper users, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(User.class, new UserSerializer());
        objectMapper.registerModule(module);

        gen.writeStartObject();
        gen.writeArrayFieldStart("users");
        for(User u : users) {
            gen.writeObject(objectMapper.valueToTree(u));
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
    
}
