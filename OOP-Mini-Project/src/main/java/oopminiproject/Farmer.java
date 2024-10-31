package oopminiproject;

public class Farmer {
    private String username;
    private String fullName;
    private String farmAddress;

    public Farmer(String username, String fullName, String farmAddress) {
        this.username = username;
        this.fullName = fullName;
        this.farmAddress = farmAddress;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getFarmAddress() {
        return farmAddress;
    }

    public void setFarmAddress(String farmAddress) {
        this.farmAddress = farmAddress;
    }
}
