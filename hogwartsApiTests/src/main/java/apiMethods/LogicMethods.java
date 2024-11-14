package apiMethods;

import client.RestClient;
import dto.UserDto;
import endpoints.Logical;
import io.restassured.http.ContentType;

import java.util.Arrays;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;

public class LogicMethods extends RestClient {

    public List<UserDto> hideOrCatchStudents(String endpoint) {
        UserDto[] users = request()
                .when().put(endpoint)
                .then()
                .statusCode(SC_OK)
                .extract()
                .response()
                .getBody().as(UserDto[].class);
        return Arrays.asList(users);
    }

    public List<UserDto> ratDumbledoreSquad() {
        UserDto[] users = request()
                .when().get(Logical.GET_STUDENTS_LIST.getValue())
                .then()
                .statusCode(SC_OK)
                .extract()
                .response()
                .getBody().as(UserDto[].class);
        return Arrays.asList(users);
    }

    public String sendOwl() {
        return request()
                .contentType(ContentType.JSON)
                .when().post(Logical.SEND_OWL.getValue())
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .body()
                .asString();
    }
    public List<UserDto> castSpell() {
        UserDto[] users = request()
                .contentType(ContentType.JSON)
                .when().post(Logical.CAST_SPELL.getValue())
                .then()
                .statusCode(SC_CREATED)
                .extract()
                .response()
                .getBody().as(UserDto[].class);
        return Arrays.asList(users);
    }
}
