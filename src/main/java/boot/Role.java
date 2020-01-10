package boot;

public class Role {
    private String username;
    private String role;

    public Role() {
    }

    public Role(String username, String role) {
        this.username = username;
        this.role = role;
    }

    // getter needed for JSON
    public String getUsername() {
        return username;
    }

    public String getRole() {
        return role;
    }
}
