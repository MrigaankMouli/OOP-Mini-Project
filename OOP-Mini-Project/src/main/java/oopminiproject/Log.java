package oopminiproject;

public class Log {
    private String action;
    private String timestamp;
    private String username;

    public Log(String action, String timestamp, String username) {
        this.action = action;
        this.timestamp = timestamp;
        this.username = username;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
