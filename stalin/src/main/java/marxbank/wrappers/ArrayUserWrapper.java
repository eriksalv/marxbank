package marxbank.wrappers;

import java.util.ArrayList;
import java.util.List;

import marxbank.model.User;

public class ArrayUserWrapper extends ArrayList<User> {

    public ArrayUserWrapper(List<User> users) {
        super(users);
    }
}
