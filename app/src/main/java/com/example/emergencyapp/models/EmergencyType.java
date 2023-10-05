package com.example.emergencyapp.models;

import java.time.LocalTime;
import java.util.UUID;

public class EmergencyType {
<<<<<<< HEAD
    int id;
    String title;
    String description;
    String img;
=======
    UUID id;
    String emergencytype;
    LocalTime dateTime;
>>>>>>> 4591f3e57544f18cbf04a49328d4503a5b5a6851


<<<<<<< HEAD
=======
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
>>>>>>> 4591f3e57544f18cbf04a49328d4503a5b5a6851
}
