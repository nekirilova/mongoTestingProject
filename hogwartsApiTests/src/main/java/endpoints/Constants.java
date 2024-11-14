package endpoints;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum Constants {
    CONSTANTS("/constants/"),
    CONSTANTS_BY_ID("/constants/{constantId}"),
    DELETE_AND_CREATE_DEFAULT_CONSTANTS("/constants/populate");
    private final String value;
}
