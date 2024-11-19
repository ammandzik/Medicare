package pl.infoshare.clinicweb.advice;

import static java.lang.String.format;

public class UserEmailExistsException extends RuntimeException {

    private static final String MSG = "User with this email already exists %s.";

    public UserEmailExistsException(String email) {
        super(format(MSG, email));
    }
}


