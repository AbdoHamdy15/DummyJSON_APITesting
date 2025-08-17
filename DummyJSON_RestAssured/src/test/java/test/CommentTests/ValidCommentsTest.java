package test.CommentTests;

import POJO.Serialization.Comments.addComment;
import POJO.Serialization.Comments.updateComment;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidCommentsTest {
    @Test
    public void getall() {
        given().baseUri("https://dummyjson.com").header("User-Agent", "PostmanRuntime/7.44.1")
                .when().get("/comments")
                .then().assertThat().statusCode(200);

    }

    @Test
    public void getone() {
        given().baseUri("https://dummyjson.com")
                .when().get("/comments/1")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void getlimitedcomments() {
        given().baseUri("https://dummyjson.com")
                .headers("Accept-Encoding", "gzip, deflate, br")
                .params("limit", 10, "skip", 10, "select", "body,postId")
                .when().get("/comments")
                .then().assertThat().statusCode(200);

    }


    @Test
    public void getcommentbypost() {
        given().baseUri("https://dummyjson.com")
                .when().get("/comments/post/6")
                .then().assertThat().statusCode(200);
    }

    @Test
    public void addcomment() {
        addComment respo = new addComment();
        respo.setBody("This makes all sense to me!");
        respo.setPostId(3);
        respo.setUserId(5);
        given().baseUri("https://dummyjson.com").body(respo)
                .when().post("/comments/add")
                .then().assertThat().statusCode(400);
    }

    @Test
    public void updatecomment() {
        updateComment respo = new updateComment();
        respo.setBody("I think I should shift to the moon");
        given().baseUri("https://dummyjson.com").
                header("Content-Type", "application/json").body(respo)
                .when().put("/comments/1")
                .then().assertThat().statusCode(200);

    }


    @Test
    public void delete() {
        Response response = given().baseUri("https://dummyjson.com")
                .when().delete("/comments/1")
                .then().assertThat().statusCode(200)
                .extract().response();
        System.out.println(response);
    }
}