package oopminiproject;

import java.time.LocalDateTime;

public class Log {
    private int logID;
    private LocalDateTime time;
    private String action;
    private String username;
    private String relatedEntity;
    private Integer relatedEntityID;

    //ACTION TYPES:
    //user registration (UREG)
    //user login (ULOG)
    //failed user login (ULGF)
    //admin login (ALOG)
    //user registers new cow (RCOW)
    //user edits cow (ECOW)
    //user registers insurance on cow (ICOW)
    //user files claim on cow (CCOW)
    //user logs out (UEND)
    //admin logs out (AEND)
    //admin verifies claim (AVCL)
    //admin cannot verify claim (AFCL)
    //system approves claim (CLMA)
    //system rejects claim (CLMR)
    //failed admin login (ALGF)
    //detected failed checksum (FCHS)

    public Log(int logID, LocalDateTime time, String action, String username, String relatedEntity, Integer relatedEntityID) {
        this.logID = logID;
        this.time = time;
        this.action = action;
        this.username = username;
        this.relatedEntity = relatedEntity;
        this.relatedEntityID = relatedEntityID;
    }

    public int getLogID() {
        return logID;
    }

    public void setLogID(int logID) {
        this.logID = logID;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRelatedEntity() {
        return relatedEntity;
    }

    public void setRelatedEntity(String relatedEntity) {
        this.relatedEntity = relatedEntity;
    }

    public Integer getRelatedEntityID() {
        return relatedEntityID;
    }

    public void setRelatedEntityID(Integer relatedEntityID) {
        this.relatedEntityID = relatedEntityID;
    }
}
