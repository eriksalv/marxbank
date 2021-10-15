package marxbank.serializers;

import java.io.IOException;
import java.util.ArrayList;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.wrappers.ArrayTransactionsWrapper;
import marxbank.model.Transaction;

public class ArrayTransactionSerializer extends StdSerializer<ArrayTransactionsWrapper>{
    
    public ArrayTransactionSerializer() {
        this(null);
    }

    public ArrayTransactionSerializer(Class<ArrayTransactionsWrapper> t) {
        super(t);
    }

    @Override
    public void serialize(ArrayTransactionsWrapper transactions, JsonGenerator gen, SerializerProvider provider) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        SimpleModule module = new SimpleModule();
        module.addSerializer(Transaction.class, new TransactionSerializer());
        objectMapper.registerModule(module);

        gen.writeStartObject();
        gen.writeArrayFieldStart("transactions");
        for(Transaction t : transactions) {
            gen.writeObject(objectMapper.valueToTree(t));
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }

    

}
