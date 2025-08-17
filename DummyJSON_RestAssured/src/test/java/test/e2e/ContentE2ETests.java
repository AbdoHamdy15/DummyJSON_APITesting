package test.e2e;

import POJO.Serialization.Posts.addposts;
import POJO.Serialization.Posts.updateposts;
import POJO.Serialization.Todos.TodosTest;
import POJO.Serialization.Comments.addComment;
import POJO.Serialization.Comments.updateComment;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class ContentE2ETests {
    private static final Logger logger = LoggerFactory.getLogger(ContentE2ETests.class);
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(groups = {"e2e", "content", "smoke"})
    public void contentManagementWorkflow() {
        logger.info("=== Starting Content Management E2E Test ===");
        
        // Step 1: Get all posts (from getall)
        logger.info("Step 1: Getting all posts");
        Response postsResponse = RestAssured.given()
                .spec(requestSpec)
                .header("Connection", "keep-alive")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Posts retrieved successfully");

        // Step 2: Get specific post (from getapost)
        logger.info("Step 2: Getting post with ID 1");
        Response postResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/posts/1")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Post retrieved successfully");

        // Step 3: Add new post (from addpost)
        logger.info("Step 3: Adding new post");
        addposts newPost = new addposts();
        newPost.setTitle("E2E Test Post");
        newPost.setUserId(5);
        
        Response addPostResponse = RestAssured.given()
                .spec(requestSpec)
                .body(newPost)
                .when()
                .post("/posts/add")
                .then()
                .statusCode(201)
                .extract()
                .response();
        logger.info("Post added successfully");

        // Step 4: Get all todos (from getall)
        logger.info("Step 4: Getting all todos");
        Response todosResponse = RestAssured.given()
                .spec(requestSpec)
                .headers("Connection", "keep-alive", "User-Agent", "User-Agent")
                .when()
                .get("/todos")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Todos retrieved successfully");

        // Step 5: Add new todo (from addtodo)
        logger.info("Step 5: Adding new todo");
        TodosTest newTodo = new TodosTest();
        newTodo.setTodo("E2E Test Todo");
        newTodo.setCompleted(false);
        newTodo.setUserId(5);
        
        Response addTodoResponse = RestAssured.given()
                .spec(requestSpec)
                .body(newTodo)
                .when()
                .post("/todos/add")
                .then()
                .statusCode(201)
                .extract()
                .response();
        logger.info("Todo added successfully");

        // Step 6: Get all comments (from getall)
        logger.info("Step 6: Getting all comments");
        Response commentsResponse = RestAssured.given()
                .spec(requestSpec)
                .header("User-Agent", "PostmanRuntime/7.44.1")
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Comments retrieved successfully");

        // Step 7: Add new comment (from addcomment)
        logger.info("Step 7: Adding new comment");
        addComment newComment = new addComment();
        newComment.setBody("E2E Test Comment");
        newComment.setPostId(3);
        newComment.setUserId(5);
        
        Response addCommentResponse = RestAssured.given()
                .spec(requestSpec)
                .body(newComment)
                .when()
                .post("/comments/add")
                .then()
                .statusCode(201)
                .extract()
                .response();
        logger.info("Comment added successfully");
        
        logger.info("=== Content Management E2E Test Completed ===");
    }

    @Test(groups = {"e2e", "content", "regression"})
    public void contentDiscoveryWorkflow() {
        logger.info("=== Starting Content Discovery E2E Test ===");
        
        // Step 1: Search for posts (from searchforposts)
        logger.info("Step 1: Searching for posts with 'love'");
        Response searchPostsResponse = RestAssured.given()
                .spec(requestSpec)
                .param("q", "love")
                .when()
                .get("/posts/search")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Posts search completed");

        // Step 2: Order posts (from orderposts)
        logger.info("Step 2: Ordering posts by title");
        Response orderPostsResponse = RestAssured.given()
                .spec(requestSpec)
                .params("sortBy", "title", "order", "asc")
                .when()
                .get("/posts")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Posts ordering completed");

        // Step 3: Get limited todos (from getlimitedtodos)
        logger.info("Step 3: Getting todos for user 5");
        Response limitedTodosResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/todos/user/5")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Limited todos retrieved");

        // Step 4: Get random todos (from getrandomtodos)
        logger.info("Step 4: Getting random todo");
        Response randomTodoResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/todos/random")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Random todo retrieved");

        // Step 5: Get limited comments (from getlimitedcomments)
        logger.info("Step 5: Getting limited comments");
        Response limitedCommentsResponse = RestAssured.given()
                .spec(requestSpec)
                .headers("Accept-Encoding", "gzip, deflate, br")
                .params("limit", 10, "skip", 10, "select", "body,postId")
                .when()
                .get("/comments")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Limited comments retrieved");

        // Step 6: Get comments by post (from getcommentbypost)
        logger.info("Step 6: Getting comments for post 6");
        Response commentsByPostResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/comments/post/6")
                .then()
                .statusCode(200)
                .extract()
                .response();
        logger.info("Comments by post retrieved");
        
        logger.info("=== Content Discovery E2E Test Completed ===");
    }

    @AfterClass
    public void cleanup() {
        logger.info("Content E2E tests cleanup completed");
    }
}
