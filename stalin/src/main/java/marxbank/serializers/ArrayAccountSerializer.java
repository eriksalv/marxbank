package marxbank.serializers;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.wrappers.ArrayAccountWrapper;
import marxbank.model.Account;

public class ArrayAccountSerializer extends StdSerializer<ArrayAccountWrapper> {

    public ArrayAccountSerializer() {
        this(null);
    }

    public ArrayAccountSerializer(Class<ArrayAccountWrapper> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayAccountWrapper accounts, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Account.class, new AccountSerializer());
        objectMapper.registerModule(module);

        gen.writeStartObject();
        gen.writeArrayFieldStart("accounts");
        for(Account a : accounts) {
            gen.writeObject(objectMapper.valueToTree(a));
        }
        gen.writeEndArray();
        gen.writeEndObject();   
    }
    
}
