package endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;
@AllArgsConstructor
@Getter
public enum Users {
    USERS("/users/"),
    USER_BY_ID("/users/{userId}"),
    DEFAULT_USERS("/users/populate");

    private final String value;
}
