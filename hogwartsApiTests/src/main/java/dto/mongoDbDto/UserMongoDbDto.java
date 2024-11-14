package dto.mongoDbDto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserMongoDbDto {
    String _id;
    String name;
    String role;
    Boolean isHidden;
    Boolean isArrested;
    Boolean isCatch;
}
