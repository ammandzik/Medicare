package pl.infoshare.clinicweb.user;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;


public enum Gender {
    MAN("Mężczyzna"),
    WOMAN("Kobieta");

    Gender(String description) {
        this.description = description;
    }

    final String description;

    @JsonValue
    public String getDescription() {
        return description;
    }
}
