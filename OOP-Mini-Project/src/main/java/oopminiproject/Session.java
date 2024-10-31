package oopminiproject;

public class Session {
    private static Session instance;
    private String username;
    private String userType;  // "ADMIN" or "FARMER"
    //TODO: userType would probably work better as an enum (like insurance!) but fuck it

    private Session() {
        // private constructor for singleton pattern
    }
    
    public static Session getInstance() {
        if (instance == null) {
            instance = new Session();
        }
        return instance;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUserType(String userType) {
        this.userType = userType;
    }
    
    public String getUserType() {
        return userType;
    }
    
    public boolean isAdmin() {
        return "ADMIN".equals(userType);
    }
    
    public void clearSession() {
        username = null;
        userType = null;
    }
}