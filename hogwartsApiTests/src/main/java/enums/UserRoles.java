package enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum UserRoles {
    DIRECTOR("director"),
    STUDENT("student"),
    UNEMPLOYED("unemployed");

    private final String role;
}
