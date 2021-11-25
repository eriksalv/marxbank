package marxbank.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import marxbank.wrappers.ArrayAccountWrapper;
import marxbank.model.Account;

/**
 * Here we use the ArrayAccountWrapper to be able to correctly pass 
 * the arraylist in with the correct type
 */
public class ArrayAccountSerializer extends StdSerializer<ArrayAccountWrapper> {

  private ObjectMapper objectMapper;
  private SimpleModule module;

  public ArrayAccountSerializer(ObjectMapper objectMapper, SimpleModule sm) {
    this(null, objectMapper, sm);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  public ArrayAccountSerializer(Class<ArrayAccountWrapper> t, ObjectMapper objectMapper, SimpleModule sm) {
    super(t);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  @Override
  public void serialize(ArrayAccountWrapper accounts, JsonGenerator gen,
      SerializerProvider provider) throws IOException {
    module.addSerializer(Account.class, new AccountSerializer());
    objectMapper.registerModule(module);

    gen.writeStartObject();
    gen.writeArrayFieldStart("accounts");
    for (Account a : accounts) {
      gen.writeObject(objectMapper.valueToTree(a));
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }

}
