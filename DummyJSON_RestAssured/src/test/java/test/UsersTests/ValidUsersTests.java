package test.UsersTests;

import POJO.Deserialization.Users.*;
import POJO.Serialization.Users.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Collections;

public class ValidUsersTests {
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
    public void getAllUsers_Valid() {
        UsersListResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(response.getUsers());
        Assert.assertTrue(response.getUsers().size() <= 30);
    }

    @Test
    public void getUserById_Valid() {
        UserByIdResponse user = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(UserByIdResponse.class);
        Assert.assertEquals(user.getId(), 1);
    }

    @Test
    public void searchUsers_Valid() {
        UsersListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("q", "John")
                .when()
                .get("/users/search")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(response.getUsers());
        Assert.assertTrue(response.getTotal() >= 0);
    }

    @Test
    public void filterUsers_Valid() {
        UsersListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("key", "hair.color")
                .queryParam("value", "Brown")
                .when()
                .get("/users/filter")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(response.getUsers());
    }

    @Test
    public void limitSkipUsers_Valid() {
        UsersListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("limit", 5)
                .queryParam("skip", 10)
                .queryParam("select", "firstName,age")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertEquals(response.getUsers().size(), 5);
    }

    @Test
    public void sortOrderUsers_Valid() {
        UsersListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("sortBy", "firstName")
                .queryParam("order", "asc")
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(response.getUsers());
    }

    @Test
    public void getUserCarts_Valid() {
        UserCartsResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/6/carts")
                .then()
                .statusCode(200)
                .extract()
                .as(UserCartsResponse.class);
        Assert.assertNotNull(response.getCarts());
    }

    @Test
    public void getUserPosts_Valid() {
        UserPostsResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/5/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(UserPostsResponse.class);
        Assert.assertNotNull(response.getPosts());
    }

    @Test
    public void getUserTodos_Valid() {
        UserTodosResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/5/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(UserTodosResponse.class);
        Assert.assertNotNull(response.getTodos());
    }

    @Test
    public void addUser_Valid() {
        AddUserRequest request = new AddUserRequest();
        request.setFirstName("Test");
        request.setLastName("User");
        request.setAge(30);
        request.setEmail("test.user@example.com");
        request.setGender("male");
        request.setPhone("+1234567890");
        AddUserResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/users/add")
                .then()
                .statusCode(201)
                .extract()
                .as(AddUserResponse.class);
        Assert.assertEquals(response.getFirstName(), "Test");
    }

    @Test
    public void updateUser_Valid() {
        UpdateUserRequest request = new UpdateUserRequest();
        request.setLastName("Owais");
        request.setAge(30);
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .put("/users/2");
        Assert.assertEquals(response.getStatusCode(), 200);
    }

    @Test
    public void deleteUser_Valid() {
        DeleteUserResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(DeleteUserResponse.class);
        System.out.println(response);
        Assert.assertTrue(response.isDeleted());
    }
} 