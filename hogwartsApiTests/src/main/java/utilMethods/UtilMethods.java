package utilMethods;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dto.ConstantDto;
import dto.UserDto;

import java.lang.reflect.Type;
import java.util.List;

public class UtilMethods {
    public UserDto getUserByName(String response, String userName) {
        Gson gson = new Gson();
        Type usersListType = new TypeToken<List<UserDto>>() {}.getType();
        List<UserDto> users = gson.fromJson(response, usersListType);
        return users.stream().filter(x -> x.getName().equals(userName))
                .findAny().get();
    }

    public ConstantDto getConstantByName(String response, String constantName) {
        Gson gson = new Gson();
        Type constantsListType = new TypeToken<List<ConstantDto>>() {}.getType();
        List<ConstantDto> constants = gson.fromJson(response, constantsListType);
        return constants.stream().filter(x -> x.getName().equals(constantName))
                .findAny().get();
    }
}
