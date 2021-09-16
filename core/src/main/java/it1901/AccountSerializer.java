package it1901;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

public class AccountSerializer extends StdSerializer<List<Account>> {

    public AccountSerializer() {
        this(null);
    }

    public AccountSerializer(Class<List<Account>> t) {
        super(t);
    }

    @Override
    public void serialize(List<Account> l, JsonGenerator gen, SerializerProvider provider) throws IOException {
        List<String> ids = new ArrayList<String>();
        for (Account a : l) {
            ids.add(a.getId());
        }

        gen.writeObject(ids);
    }
    
}
