package marxbank.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import marxbank.wrappers.ArrayTransactionsWrapper;
import marxbank.model.Transaction;

/**
 * Here we use the ArrayAccountWrapper to be able to correctly pass 
 * the arraylist in with the correct type
 */
public class ArrayTransactionSerializer extends StdSerializer<ArrayTransactionsWrapper> {

  private ObjectMapper objectMapper;
  private SimpleModule module;

  public ArrayTransactionSerializer(ObjectMapper objectMapper, SimpleModule sm) {
    this(null, objectMapper, sm);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  public ArrayTransactionSerializer(Class<ArrayTransactionsWrapper> t, ObjectMapper objectMapper, SimpleModule sm) {
    super(t);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  @Override
  public void serialize(ArrayTransactionsWrapper transactions, JsonGenerator gen,
      SerializerProvider provider) throws IOException {
    module.addSerializer(Transaction.class, new TransactionSerializer());
    objectMapper.registerModule(module);

    gen.writeStartObject();
    gen.writeArrayFieldStart("transactions");
    for (Transaction t : transactions) {
      gen.writeObject(objectMapper.valueToTree(t));
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }



}
