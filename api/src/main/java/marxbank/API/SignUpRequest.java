package marxbank.API;

import marxbank.model.User;

/**
 * Klassen holder på informasjon en bruker må gi for å opprette en ny profil hos banken.
 */

public class SignUpRequest {
    
    private String username;
    private String password;
    private String email;
    
    protected SignUpRequest() {}

    public SignUpRequest(String username, String password, String email) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setUsername(String newUsername) {
        this.username = newUsername;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String newPassword) {
        this.password = newPassword;
    }

    public String getPassword() {
        return this.password;
    }

    public void setEmail(String newEmail) {
        this.email = newEmail;
    }

    public String getEmail() {
        return this.email;
    }

    public User createUser() {
        User user = new User();

        user.setUsername(this.username);
        user.setEmail(this.email);
        user.setPassword(this.password);

        return user;
    }
}
