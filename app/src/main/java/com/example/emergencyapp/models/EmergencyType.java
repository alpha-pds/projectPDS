package com.example.emergencyapp.models;

import java.time.LocalTime;
import java.util.UUID;

public class EmergencyType {

    int id;
    String title;
    String description;
    String img;

    UUID Id;
    String emergencytype;
    LocalTime dateTime;


    public EmergencyType(UUID Id, String emergencytype, LocalTime dateTime) {
        this.Id = Id;
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
        return Id;
    }

    public String getEmergencytype() {
        return emergencytype;
    }

    public LocalTime getDateTime() {
        return dateTime;
    }

}
