package fr.ibralogan.mediatheque.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class UtilisateurNotFoundException extends RuntimeException {
    public UtilisateurNotFoundException(String message) {
        super(message);
    }

    public UtilisateurNotFoundException() {
        super();
    }
}
