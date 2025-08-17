package test.AuthTests;

import POJO.Deserialization.Auth.LoginResponse;
import POJO.Deserialization.Auth.AuthUserResponse;
import POJO.Deserialization.Auth.RefreshAuthResponse;
import POJO.Serialization.Auth.RefreshAuthRequest;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class ValidAuthTests {
    private static RequestSpecification requestSpec;
    private static String accessToken;
    private static String refreshToken;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    public void loginValid() {
        String loginBody = "{\"username\":\"emilys\",\"password\":\"emilyspass\",\"expiresInMins\":30}";
        LoginResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(loginBody)
                .when()
                .post("/auth/login")
                .then()
                .statusCode(200)
                .extract()
                .as(LoginResponse.class);
        accessToken = response.getAccessToken();
        refreshToken = response.getRefreshToken();
        Assert.assertNotNull(accessToken);
        Assert.assertNotNull(refreshToken);
    }

    @Test(dependsOnMethods = "loginValid")
    public void getCurrentAuthUser() {
        AuthUserResponse user = RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", "Bearer " + accessToken)
                .when()
                .get("/auth/me")
                .then()
                .statusCode(200)
                .extract()
                .as(AuthUserResponse.class);
    }

    @Test(dependsOnMethods = "loginValid")
    public void refreshAuthSession() {
        RefreshAuthRequest request = new RefreshAuthRequest();
        request.setRefreshToken(refreshToken);
        request.setExpiresInMins(30);
        RefreshAuthResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/auth/refresh")
                .then()
                .statusCode(200)
                .extract()
                .as(RefreshAuthResponse.class);
        Assert.assertNotNull(response.getAccessToken());
        Assert.assertNotNull(response.getRefreshToken());
    }
} 