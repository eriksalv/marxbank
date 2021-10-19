package marxbank.API;

public class SignUpRequest {
    
    private long Id;
    private String username;
    private String password;
    private String email;
    
    public SignUpRequest(){

    }
    public SignUpRequest(long Id, String username, String password, String email){
        this.Id = Id;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public void setId(long NewId) {
        this.Id = NewId;
    }
    public long getId(){
        return this.Id;
    }
    public void setUsername(String newUsername){
        this.username = newUsername;
    }
    public String getUsername(){
        return this.username;
    }
    public void setPassword(String newPassword){
        this.password = newPassword;
    }
    public String getPassword(){
        return this.password;
    }
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public String getEmail(){
        return this.email;
    }
}
