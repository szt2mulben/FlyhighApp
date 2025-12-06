package hu.bence.flyhigh.session;

public class SessionManager {

    private static SessionManager instance;

    private String token;
    private int userId;
    private String name;
    private String permission;

    private SessionManager() {}

    public static SessionManager getInstance() {
        if (instance == null) instance = new SessionManager();
        return instance;
    }

    public void setUser(String token, int userId, String name, String permission) {
        this.token = token;
        this.userId = userId;
        this.name = name;
        this.permission = permission;
    }

    public String getToken() { return token; }
    public int getUserId() { return userId; }
    public String getName() { return name; }
    public String getPermission() { return permission; }

    public boolean isAdmin() {
        return permission != null && permission.equalsIgnoreCase("admin");
    }
    public void clear() {
        token = null;
        userId = 0;
        name = null;
        permission = null;
    }

}
