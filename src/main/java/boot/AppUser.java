package boot;

import java.util.ArrayList;

public class AppUser {
    private String username;
    private String password;
    private ArrayList<String> roles;

    public AppUser() {
    }

    public AppUser(String username) {
        this.username = username;
    }

    public AppUser(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // getter needed for JSON
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public ArrayList<String> getRoles() {
        return this.roles;
    }

    public void setRoles(ArrayList<String> roles) {
        this.roles = roles;
    }
}
