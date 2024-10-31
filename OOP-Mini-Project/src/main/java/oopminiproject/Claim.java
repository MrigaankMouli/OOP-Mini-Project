package oopminiproject;

import java.time.LocalDate;

public class Claim {
    private int claimID;
    private int cowID;
    private String insurance;
    private LocalDate incidentDate;
    private LocalDate claimDate;
    private String username;
    private String incidentType;
    private String incidentDescription;
    private String status; //can take state "PENDING", "APPROVED", "REJECTED"
    //AGAIN!! ENUM!!!

    public Claim(int claimID, int cowID, String insurance, String incidentType, String incidentDescription,
                 LocalDate incidentDate, LocalDate claimDate, String username, String status) {
        this.claimID = claimID;
        this.cowID = cowID;
        this.insurance = insurance;
        this.incidentDate = incidentDate;
        this.claimDate = claimDate;
        this.username = username;
        this.incidentType = incidentType;
        this.incidentDescription = incidentDescription;
        this.status = status;
    }

    public int getClaimID() { return claimID; }
    public void setClaimID(int claimID) { this.claimID = claimID; }

    public int getCowID() { return cowID; }
    public void setCowID(int cowID) { this.cowID = cowID; }

    public String getInsurance() { return insurance; }
    public void setInsurance(String insurance) { this.insurance = insurance; }

    public LocalDate getIncidentDate() { return incidentDate; }
    public void setIncidentDate(LocalDate incidentDate) { this.incidentDate = incidentDate; }

    public LocalDate getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public String getIncidentType() { return incidentType; }
    public void setIncidentType(String incidentType) { this.incidentType = incidentType; }

    public String getIncidentDescription() { return incidentDescription; }
    public void setIncidentDescription(String incidentDescription) { this.incidentDescription = incidentDescription; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}