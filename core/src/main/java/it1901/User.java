package it1901;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.ObjectMapper;

@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
class User {

    private String id;
    private String username;
    private String email;
    private String password;
    @JsonManagedReference
    private ArrayList<Account> accounts = new ArrayList<Account>();

    public User(String id, String username, String email, String password) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
    }

    public User() {}

    public void setId(String newId) {
        this.id = newId;
    }

    public String getId() {
        return id;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUsername() {
        return this.username;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getEmail() {
        return this.email;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setAccounts(ArrayList<Account> newAccountsList) {
        this.accounts = newAccountsList;
    }

    public ArrayList<Account> getAccounts() {
        return this.accounts;
    }

    public void addAccount(Account newAccount) {
        if (!accounts.contains(newAccount)) {
            accounts.add(newAccount);
        } else {
            throw new IllegalArgumentException("This account is already added.");
        }
    }

    public void removeAccount(Account unwantedAccount) {
        if (accounts.contains(unwantedAccount)) {
            accounts.remove(unwantedAccount);
        } else {
            throw new IllegalArgumentException("This account does not exist in your accounts.");
        }
    }

    /**
     * saves a user to a jsonfile in data/users/
     * The filename is structured like this: id.username.json
     * @param user to be saved
     * @throws IllegalArgumentException if user is null, or some of its fields are null
     */
    public static void saveUser(User user) {
        // check if user is null
        if(user == null) throw new IllegalArgumentException("User cannot be null");
        // check if user fields are null
        if(user.getId() == null || user.getUsername() == null || user.getEmail() == null || user.getPassword() == null) throw new IllegalArgumentException("User fields cannot be null");

        ObjectMapper objectMapper = new ObjectMapper();

        File userFile = new File(String.format("../data/users/%s.%s.json", user.getId(), user.getUsername()));
        try {
            objectMapper.writeValue(userFile, user);
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                try {
                    userFile = new File(String.format("data/users/%s.%s.json", user.getId(), user.getUsername()));
                    objectMapper.writeValue(userFile, user);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }

    }

    /**
     * saves user to local storage
     */
    public void saveUser() {
        if(this.id == null || this.username == null || this.email == null || this.password == null) throw new IllegalStateException("User fields can not be null.");
        ObjectMapper objectMapper = new ObjectMapper();

        File userFile = new File(String.format("../data/users/%s.%s.json", this.id, this.username));
        try {
            objectMapper.writeValue(userFile, this);
        } catch (Exception e) {
            if(e instanceof FileNotFoundException) {
                try {
                    userFile = new File(String.format("data/users/%s.%s.json", this.id, this.username));
                    objectMapper.writeValue(userFile, this);
                } catch (Exception a) {
                    System.out.println(String.format("Error: %s", a));
                }
            } else {
                System.out.println(String.format("Error: %s", e));
            }
        }
    }

    /**
     * Reads a user from a given path to a jsonfile
     * @param path to user
     * @return the user at the given location or null if there is an error
     * @throws FileNotFoundException if no userfile was found at path
     * @throws IllegalArgumentException if path is not a valid path
     */
    public static User readUser(String path) throws FileNotFoundException {
        // check if path is valid
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("path is not valid");
        // check if file exists
        if(!new File(path).exists()) throw new FileNotFoundException("No File with that path exists.");

        ObjectMapper objectMapper = new ObjectMapper();

        File userFile = new File(path);

        try {
            User user = objectMapper.readValue(userFile, User.class);
            return user;
        } catch (Exception e) {
            System.out.println(String.format("Error: %s", e));
        }

        return null;
    }

    /**
     * reads user file to this user object
     * @param path to user file
     * @throws IllegalArgumentException if path is not valid
     * @throws FileNotFoundException if file was not found
     * @throws IllegalStateException if there was an error reading the user correctly
     */
    public void readUserToUser(String path) throws FileNotFoundException {
        // check if path is valid
        if(!ValidPath.isValidPath(path)) throw new IllegalArgumentException("path is not valid");
        // check if file exists
        if(!new File(path).exists()) throw new FileNotFoundException("No File with that path exists.");

        ObjectMapper objectMapper = new ObjectMapper();
        User user;
        File userFile = new File(path);

        try {
            user = objectMapper.readValue(userFile, User.class);
        } catch (Exception e) {
            user = null;
        }

        if(user == null) throw new IllegalStateException("user file could not be read correctly");

        this.setId(user.getId());
        this.setUsername(user.getUsername());
        this.setEmail(user.getEmail());
        this.setPassword(user.getPassword());
        this.setAccounts(user.getAccounts());
    }

    @Override
    public String toString() {
        return String.format("id:%s, username:%s", this.getId(), this.getUsername());
    }

    public static void main(String... args) {
        User u = new User("2", "testUsername", "test@email.com", "password");
        u.addAccount(new SavingsAccount("1", u, 5.0));
        u.addAccount(new SavingsAccount("2", u, 8.0));
        u.saveUser();

        //System.out.println(new File("data/users/2.testUsername.json").exists());

        try {
            User b;
            b = User.readUser("data/users/2.testUsername.json");
            System.out.println(b.getId());
            System.out.println(b.getUsername());
            System.out.println(b.getAccounts().size());
        } catch (FileNotFoundException e) {
            System.out.println(String.format("Error: %s", e));
        }
        
        
    }

}