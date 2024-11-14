package dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
    String id;
    String name;
    String role;
    Boolean isHidden;
    Boolean isArrested;
    Boolean isCatch;

}
