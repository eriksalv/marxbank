package marxbank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.model.Transaction;

public class TransactionSerializer extends StdSerializer<Transaction> {
    public TransactionSerializer() {
        this(null);
    }

    public TransactionSerializer(Class<Transaction> t) {
        super(t);
    }

    @Override
    public void serialize(Transaction transaction, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", transaction.getId());
        gen.writeNumberField("amount", transaction.getAmount());
        gen.writeObjectFieldStart("from");
        gen.writeStringField("id", transaction.getFrom().getId());
        gen.writeEndObject();
        gen.writeObjectFieldStart("reciever");
        gen.writeStringField("id", transaction.getReciever().getId());
        gen.writeEndObject();
        gen.writeStringField("dateString", transaction.getDateString());
        gen.writeEndObject();
    }
}
