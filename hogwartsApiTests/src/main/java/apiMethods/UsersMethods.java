package apiMethods;

import client.RestClient;
import dto.UserDto;
import endpoints.Users;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static org.apache.http.HttpStatus.SC_OK;

public class UsersMethods extends RestClient {
    public <Response> Response getAllUsers(int statusCode, Class<Response> responseClass) {
        return request()
                .when().get(Users.USERS.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response getUserById(int statusCode, Class<Response> responseClass, String userId) {
        return request()
                .when()
                .pathParam("userId", userId)
                .get(Users.USER_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response deleteUserById(int statusCode, Class<Response> responseClass, String userId) {
        return request()
                .pathParam("userId", userId)
                .when()
                .delete(Users.USER_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response createUser(UserDto userDto, int statusCode, Class<Response> responseClass) {
        return request()
                .contentType(ContentType.JSON)
                .body(userDto)
                .when()
                .post(Users.USERS.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response editUser(UserDto userDto, int statusCode, Class<Response> responseClass, String userId) {
        return request()
                .contentType(ContentType.JSON)
                .pathParam("userId", userId)
                .body(userDto)
                .when()
                .patch(Users.USER_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public ValidatableResponse deleteUsersAndCreateDefaultUsers() {
        return request()
                .contentType(ContentType.JSON)
                .when()
                .post(Users.DEFAULT_USERS.getValue())
                .then();
    }
}
