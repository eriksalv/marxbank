package marxbank.API;

import marxbank.model.User;

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
        return new User(this.username, this.email, this.password, true);
    }
}
