package marxbank.API;

import marxbank.model.User;


/**
 * Klassen inneholder det brukeren trenger å vite om sin egen profil.
 */
public class UserResponse {

    private long Id;
    private String username;
    private String email;

    public UserResponse(){
    }

    public UserResponse(User user){
        this.Id = user.getId();
        this.username = user.getUsername();
        this.email = user.getEmail();
    }

    public UserResponse(long Id, String username, String email){
        this.Id = Id;
        this.username = username;
        this.email = email;
    }

    public void setId(long newId){
        this.Id = newId;
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
    public void setEmail(String newEmail){
        this.email = newEmail;
    }
    public String getEmail(){
        return this.email;
    }
}
