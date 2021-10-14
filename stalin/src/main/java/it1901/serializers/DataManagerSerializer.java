package it1901.serializers;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import it1901.DataManager;
import it1901.wrappers.ArrayAccountWrapper;
import it1901.wrappers.ArrayUserWrapper;
import it1901.wrappers.DataManagerWrapper;
import it1901.wrappers.ArrayTransactionsWrapper;

public class DataManagerSerializer extends StdSerializer<DataManagerWrapper> {
    
    public DataManagerSerializer() {
        this(null);
    }

    public DataManagerSerializer(Class<DataManagerWrapper> t) {
        super(t);
    }

    @Override
    public void serialize(DataManagerWrapper dm, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(ArrayUserWrapper.class, new ArrayUserSerializer());
        module.addSerializer(ArrayAccountWrapper.class, new ArrayAccountSerializer());
        module.addSerializer(ArrayTransactionsWrapper.class, new ArrayTransactionSerializer());
        objectMapper.registerModule(module);
        
        gen.writeStartObject();
        gen.writeObjectField("users", objectMapper.valueToTree(new ArrayUserWrapper(dm.users)).get("users"));
        gen.writeObjectField("accounts", objectMapper.valueToTree(new ArrayAccountWrapper(dm.accounts)).get("accounts"));
        gen.writeObjectField("transactions", objectMapper.valueToTree(new ArrayTransactionsWrapper(dm.transactions)).get("transactions"));
        gen.writeEndObject();
    }

    
}
