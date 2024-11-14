package apiMethods;

import client.RestClient;
import dto.ConstantDto;
import dto.UserDto;
import endpoints.Constants;
import endpoints.Users;
import io.restassured.http.ContentType;

public class ConstantsMethods extends RestClient {
    public <Response> Response getAllConstants(int statusCode, Class<Response> responseClass) {
        return request()
                .when().get(Constants.CONSTANTS.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response getConstantById(int statusCode, Class<Response> responseClass, String constantId) {
        return request()
                .when()
                .pathParam("constantId", constantId)
                .get(Constants.CONSTANTS_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response deleteConstantById(int statusCode, Class<Response> responseClass, String constantId) {
        return request()
                .pathParam("constantId", constantId)
                .when()
                .delete(Constants.CONSTANTS_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response createConstant(ConstantDto constantDto, int statusCode, Class<Response> responseClass) {
        return request()
                .contentType(ContentType.JSON)
                .body(constantDto)
                .when()
                .post(Constants.CONSTANTS.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response editConstant(UserDto userDto, int statusCode, Class<Response> responseClass, String constantId) {
        return request()
                .contentType(ContentType.JSON)
                .pathParam("constantId", constantId)
                .body(userDto)
                .when()
                .patch(Constants.CONSTANTS_BY_ID.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
    public <Response> Response deleteConstantsAndCreateDefaultConstants(int statusCode, Class<Response> responseClass) {
        return request()
                .when()
                .post(Constants.DELETE_AND_CREATE_DEFAULT_CONSTANTS.getValue())
                .then()
                .statusCode(statusCode)
                .extract()
                .response()
                .as(responseClass);
    }
}
