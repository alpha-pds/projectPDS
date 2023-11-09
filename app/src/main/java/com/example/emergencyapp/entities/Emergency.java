package com.example.emergencyapp.entities;

import java.time.LocalDateTime;
import java.time.LocalTime;

public class Emergency {
    private int id;
    private int emergencyTypeId;
    private String reporterName;
    private String patientName;
    private String assistantName;
    public LocalTime creationDateTime;

    public Emergency() {
    }

    public Emergency(int emergencyTypeId) {
        this.emergencyTypeId =  emergencyTypeId;
        creationDateTime = LocalTime.now();
    }
}
