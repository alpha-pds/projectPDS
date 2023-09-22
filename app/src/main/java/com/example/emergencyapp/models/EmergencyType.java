package com.example.emergencyapp.models;

import java.time.LocalTime;

public class EmergencyType {
    Integer Id;
    String emergencytype;
    LocalTime dateTime;

    public EmergencyType() {}

    public EmergencyType(Integer id, String emergencytype, LocalTime dateTime) {
        Id = id;
        this.emergencytype = emergencytype;
        this.dateTime = dateTime;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public void setEmergencytype(String emergencytype) {
        this.emergencytype = emergencytype;
    }

    public void setDateTime(LocalTime dateTime) {
        this.dateTime = dateTime;
    }

    public Integer getId() {
        return Id;
    }

    public String getEmergencytype() {
        return emergencytype;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }
}
