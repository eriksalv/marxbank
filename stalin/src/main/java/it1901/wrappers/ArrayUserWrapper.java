package it1901.wrappers;

import java.util.ArrayList;
import java.util.List;

import it1901.model.User;

public class ArrayUserWrapper extends ArrayList<User> {

    public ArrayUserWrapper(List<User> users) {
        super(users);
    }
}
