package marxbank.wrappers;

import java.util.ArrayList;
import java.util.List;
import marxbank.model.User;

/**
 * This class is a wrapper class for an ArrayList to give it the specified type
 * parameter such that it can be registered as a module to be used by Jacksons simple module.
 * It cannot take in ArrayList with type parameter as argument as all ArrayList objects are treated
 * as the same type, thus this wrapper is needed to make it behave correctly.
 */
public class ArrayUserWrapper extends ArrayList<User> {

  public ArrayUserWrapper(List<User> users) {
    super(users);
  }
}
