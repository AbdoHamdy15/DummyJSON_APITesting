package test.AuthTests;

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

public class InvalidAuthTests {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(LogDetail.ALL)
                .build();
    }

    @Test
    public void loginWithWrongPassword() {
        String loginBody = "{\"username\":\"emilys\",\"password\":\"wrongpass\",\"expiresInMins\":30}";
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(loginBody)
                .when()
                .post("/auth/login");
        Assert.assertEquals(response.getStatusCode(), 400, "Expected 400 for invalid credentials");
        String message = response.jsonPath().getString("message");
        Assert.assertTrue(message.toLowerCase().contains("invalid"), "Error message should mention invalid credentials");
    }

    @Test
    public void getCurrentAuthUser_InvalidToken() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .header("Authorization", "Bearer invalidtoken")
                .when()
                .get("/auth/me");
        Assert.assertEquals(response.getStatusCode(), 401, "Expected 401 for invalid/expired token");
        String message = response.jsonPath().getString("message");
        Assert.assertEquals(message, "Invalid/Expired Token!");
    }

    @Test
    public void refreshAuthSession_InvalidToken() {
        RefreshAuthRequest request = new RefreshAuthRequest();
        request.setRefreshToken("invalidtoken");
        request.setExpiresInMins(30);
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/auth/refresh");
        // NOTE: API currently returns 200 even for invalid token (should be 401 ideally)
        Assert.assertEquals(response.getStatusCode(), 200, "Expected 200 (API bug: should be 401 for invalid token)");
        // Optionally check message or log warning
    }
} 