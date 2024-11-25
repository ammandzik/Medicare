package pl.infoshare.clinicweb.user.entity;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Gender {
    MALE("Mężczyzna"),
    FEMALE("Kobieta");

    Gender(String description) {
        this.description = description;
    }

    final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}


