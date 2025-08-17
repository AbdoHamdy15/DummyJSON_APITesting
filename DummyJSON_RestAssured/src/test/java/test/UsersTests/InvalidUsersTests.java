package test.UsersTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import POJO.Serialization.Users.AddUserRequest;
import POJO.Serialization.Users.UpdateUserRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidUsersTests {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @Test
    public void getAllUsers_InvalidEndpoint() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/userz");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getUserById_Invalid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }



    @Test
    public void getUserCarts_InvalidUser() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/99999/carts");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getUserPosts_InvalidUser() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/99999/posts");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getUserTodos_InvalidUser() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/99999/todos");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void updateUser_InvalidId() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setLastName("Owais");
        request.setAge(30);
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .put("/users/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteUser_InvalidId() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/users/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }
} 