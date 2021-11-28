package marxbank.serializers;

import java.io.IOException;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import marxbank.wrappers.ArrayUserWrapper;
import marxbank.model.User;

/**
 * Here we use the ArrayAccountWrapper to be able to correctly pass 
 * the arraylist in with the correct type
 */
public class ArrayUserSerializer extends StdSerializer<ArrayUserWrapper> {

  private ObjectMapper objectMapper;
  private SimpleModule module;

  public ArrayUserSerializer(ObjectMapper objectMapper, SimpleModule sm) {
    this(null, objectMapper, sm);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  public ArrayUserSerializer(Class<ArrayUserWrapper> t, ObjectMapper objectMapper, SimpleModule sm) {
    super(t);
    this.objectMapper = objectMapper;
    this.module = sm;
  }

  @Override
  public void serialize(ArrayUserWrapper users, JsonGenerator gen, SerializerProvider provider)
      throws IOException {
    module.addSerializer(User.class, new UserSerializer());
    objectMapper.registerModule(module);

    gen.writeStartObject();
    gen.writeArrayFieldStart("users");
    for (User u : users) {
      gen.writeObject(objectMapper.valueToTree(u));
    }
    gen.writeEndArray();
    gen.writeEndObject();
  }

}
