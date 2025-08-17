package test.e2e;

import POJO.Deserialization.Users.*;
import POJO.Serialization.Users.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class UserE2ETests {
    private static final Logger logger = LoggerFactory.getLogger(UserE2ETests.class);
    private static RequestSpecification requestSpec;
    private static int createdUserId;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .build();
    }
    @Test(groups = {"e2e", "user", "smoke"})
    public void userLifecycleWorkflow() {
        logger.info("=== Starting User Lifecycle E2E Test ===");
        // Step 1: Get all users (from getAllUsers_Valid)
        logger.info("Step 1: Getting all users");
        UsersListResponse allUsers = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(allUsers.getUsers());
        logger.info("Found {} users", allUsers.getUsers().size());

        // Step 2: Get specific user (from getUserById_Valid)
        logger.info("Step 2: Getting user with ID 1");
        UserByIdResponse user = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(UserByIdResponse.class);
        Assert.assertEquals(user.getId(), 1);
        logger.info("User retrieved: {} {}", user.getFirstName(), user.getLastName());

        // Step 3: Search for users (from searchUsers_Valid)
        logger.info("Step 3: Searching for users with 'John'");
        UsersListResponse searchResults = RestAssured.given()
                .spec(requestSpec)
                .queryParam("q", "John")
                .when()
                .get("/users/search")
                .then()
                .statusCode(200)
                .extract()
                .as(UsersListResponse.class);
        Assert.assertNotNull(searchResults.getUsers());
        logger.info("Search returned {} users", searchResults.getTotal());

        // Step 4: Add new user (from addUser_Valid)
        logger.info("Step 4: Adding new user");
        AddUserRequest newUser = new AddUserRequest();
        newUser.setFirstName("Test");
        newUser.setLastName("User");
        newUser.setAge(30);
        newUser.setEmail("test.user@example.com");
        newUser.setGender("male");
        newUser.setPhone("+1234567890");

        AddUserResponse createResponse = RestAssured.given()
                .spec(requestSpec)
                .body(newUser)
                .when()
                .post("/users/add")
                .then()
                .statusCode(201)
                .extract()
                .as(AddUserResponse.class);
        createdUserId = createResponse.getId();
        logger.info("User created with ID: {}", createdUserId);

        // Step 5: Update existing user (from updateUser_Valid)
        logger.info("Step 5: Updating existing user");
        UpdateUserRequest updateRequest = new UpdateUserRequest();
        updateRequest.setLastName("Updated");
        updateRequest.setAge(31);

        Response updateResponse = RestAssured.given()
                .spec(requestSpec)
                .body(updateRequest)
                .when()
                .put("/users/1");
        Assert.assertEquals(updateResponse.getStatusCode(), 200);
        logger.info("User updated successfully");

        // Step 6: Delete existing user (from deleteUser_Valid)
        logger.info("Step 6: Deleting existing user");
        DeleteUserResponse deleteResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(DeleteUserResponse.class);
        Assert.assertTrue(deleteResponse.isDeleted());
        logger.info("User deleted successfully");
        
        logger.info("=== User Lifecycle E2E Test Completed ===");
    }

    @Test(groups = {"e2e", "user", "regression"})
    public void userDataRelationshipsWorkflow() {
        logger.info("=== Starting User Data Relationships E2E Test ===");
        
        // Step 1: Get user (from getUserById_Valid)
        logger.info("Step 1: Getting user with ID 1");
        UserByIdResponse user = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1")
                .then()
                .statusCode(200)
                .extract()
                .as(UserByIdResponse.class);
        Assert.assertEquals(user.getId(), 1);
        logger.info("User retrieved: {}", user.getFirstName());

        // Step 2: Get user's posts (from getUserPosts_Valid)
        logger.info("Step 2: Getting user's posts");
        UserPostsResponse posts = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1/posts")
                .then()
                .statusCode(200)
                .extract()
                .as(UserPostsResponse.class);
        Assert.assertNotNull(posts.getPosts());
        logger.info("User has {} posts", posts.getPosts().size());

        // Step 3: Get user's todos (from getUserTodos_Valid)
        logger.info("Step 3: Getting user's todos");
        UserTodosResponse todos = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1/todos")
                .then()
                .statusCode(200)
                .extract()
                .as(UserTodosResponse.class);
        Assert.assertNotNull(todos.getTodos());
        logger.info("User has {} todos", todos.getTodos().size());

        // Step 4: Get user's carts (from getUserCarts_Valid)
        logger.info("Step 4: Getting user's carts");
        UserCartsResponse carts = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/users/1/carts")
                .then()
                .statusCode(200)
                .extract()
                .as(UserCartsResponse.class);
        Assert.assertNotNull(carts.getCarts());
        logger.info("User has {} carts", carts.getCarts().size());
        
        logger.info("=== User Data Relationships E2E Test Completed ===");
    }

    @AfterClass
    public void cleanup() {
        logger.info("User E2E tests cleanup completed");
    }
}
