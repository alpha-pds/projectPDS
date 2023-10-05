package com.example.emergencyapp.models;

import java.time.LocalTime;
import java.util.UUID;

public class EmergencyType {
    UUID id;
    String emergencytype;
    LocalTime dateTime;

    public EmergencyType() {}

    public EmergencyType(UUID id, String emergencytype, LocalTime dateTime) {
        this.id = id;
        this.emergencytype = emergencytype;
        this.dateTime = dateTime;
    }

    public void setid(UUID id) {
        id = id;
    }

    public void setEmergencytype(String emergencytype) {
        this.emergencytype = emergencytype;
    }

    public void setDateTime(LocalTime dateTime) {
        this.dateTime = dateTime;
    }

    public UUID getid() {
        return id;
    }

    public String getEmergencytype() {
        return emergencytype;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }
}
