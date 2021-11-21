package marxbank.API;

public class EditUserRequest {
    private String username;
    private String password;
    private String oldPassword;
    private String email;

    protected EditUserRequest() {
    }

    public EditUserRequest(String username, String password, String oldPassword, String email) {
        this.username = username;
        this.password = password;
        this.oldPassword = oldPassword;
        this.email = email;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return this.username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setOldPassword(String password) {
        this.oldPassword = password;
    }

    public String getOldPassword() {
        return this.oldPassword;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return this.email;
    }

    @Override
    public String toString() {
        return "username: " + username;
    }
}
