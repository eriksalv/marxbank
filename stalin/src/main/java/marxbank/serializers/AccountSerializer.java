package marxbank.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import marxbank.model.Account;
import marxbank.model.Transaction;

public class AccountSerializer extends  StdSerializer<Account> {

    public AccountSerializer() {
        this(null);
    }

    public AccountSerializer(Class<Account> t) {
        super(t);
    }

    @Override
    public void serialize(Account account, JsonGenerator gen, SerializerProvider provider) throws IOException {
        gen.writeStartObject();
        gen.writeStringField("id", account.getId());
        gen.writeObjectFieldStart("user");
        gen.writeStringField("id", account.getUser().getId());
        gen.writeEndObject();
        gen.writeNumberField("accountNumber", account.getAccountNumber());
        gen.writeStringField("name", account.getName());
        gen.writeNumberField("balance", account.getBalance());
        gen.writeNumberField("interestRate", account.getInterestRate());
        gen.writeStringField("type", account.getType().toString());
        gen.writeFieldName("transactions");
        gen.writeStartArray();
        for(Transaction t : account.getTransactions()) {
            gen.writeStartObject();
            gen.writeStringField("id", t.getId());
            gen.writeEndObject();
        }
        gen.writeEndArray();
        gen.writeEndObject();
    }
    
}
