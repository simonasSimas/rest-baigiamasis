package lt.codeacademy.restbagiamasis.exception;

import lombok.Getter;

@Getter
public class UserAlreadyExistsException extends RuntimeException {

    private final String username;

    public UserAlreadyExistsException(String username) {
        this.username = username;
    }
}
