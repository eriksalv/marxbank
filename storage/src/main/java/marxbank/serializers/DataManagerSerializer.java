package marxbank.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.DataManager;
import marxbank.wrappers.ArrayAccountWrapper;
import marxbank.wrappers.ArrayUserWrapper;
import marxbank.wrappers.DataManagerWrapper;
import marxbank.wrappers.ArrayTransactionsWrapper;

public class DataManagerSerializer extends StdSerializer<DataManagerWrapper> {

  private ObjectMapper objectMapper;
  private SimpleModule module;

  public DataManagerSerializer(ObjectMapper objectMapper, SimpleModule sm) {
    this(null, objectMapper, sm);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  public DataManagerSerializer(Class<DataManagerWrapper> t, ObjectMapper objectMapper, SimpleModule sm) {
    super(t);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  @Override
  public void serialize(DataManagerWrapper dm, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    module.addSerializer(ArrayUserWrapper.class, new ArrayUserSerializer(objectMapper, module));
    module.addSerializer(ArrayAccountWrapper.class, new ArrayAccountSerializer(objectMapper, module));
    module.addSerializer(ArrayTransactionsWrapper.class, new ArrayTransactionSerializer(objectMapper, module));
    objectMapper.registerModule(module);

    gen.writeStartObject();
    gen.writeObjectField("users",
        objectMapper.valueToTree(new ArrayUserWrapper(dm.getUsers())).get("users"));
    gen.writeObjectField("accounts",
        objectMapper.valueToTree(new ArrayAccountWrapper(dm.getAccounts())).get("accounts"));
    gen.writeObjectField("transactions", objectMapper
        .valueToTree(new ArrayTransactionsWrapper(dm.getTransactions())).get("transactions"));
    gen.writeEndObject();
  }


}
