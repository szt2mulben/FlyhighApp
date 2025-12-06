package hu.bence.flyhigh.model;

public class UserModel {
    private int id;
    private String name;
    private String password;
    private String email;
    private String permission;

    public int getId() { return id; }
    public String getName() { return name; }
    public String getPassword() { return password; }
    public String getEmail() { return email; }
    public String getPermission() { return permission; }

    public void setPermission(String permission) {
        this.permission = permission;
    }


}
