package com.example.emergencyapp.entities;

import java.time.LocalDateTime;
import java.util.UUID;

public class Emergency {
    UUID id;
    UUID emergencyTypeId;
    LocalDateTime creationDateTime;

    public Emergency() {
    }

    public Emergency(UUID emergencyTypeId, LocalDateTime creationDateTime) {
        this.emergencyTypeId = emergencyTypeId;
        this.creationDateTime = creationDateTime;
    }
}
