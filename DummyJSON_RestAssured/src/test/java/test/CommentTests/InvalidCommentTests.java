package test.CommentTests;

import POJO.Serialization.Comments.addComment;
import POJO.Serialization.Comments.updateComment;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class InvalidCommentTests {

    @Test
    public void getallcomments_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/commenta").then()
                .statusCode(404);
    }@Test
    public void getacomment_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/commentss/1").then()
                .statusCode(404);
    }

    @Test
    public void getcommntbyindex_invalid(){
        given().baseUri("https://dummyjson.com").when().get("/comments/postt/6")
                .then().assertThat().statusCode(404);
    }

    @Test
    public void updatecomment_invalid(){
        updateComment repos = new updateComment();
        repos.setBody("Updated comment body");
        given().baseUri("https://dummyjson.com").body(repos)
                .when().put("/comments/500")
                .then().assertThat().statusCode(404);
    }
    @Test
    public void addcomment_invalid(){
        addComment repos = new addComment();
        repos.setBody("This makes all sense to me!");
        repos.setPostId(5);
        repos.setUserId(3);
        given().baseUri("https://dummyjson.com")
                .body(repos)
                .when().post("/comments/addd").then()
                .assertThat().statusCode(404);
    }
    @Test
    public void deletecomment_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/commentss/100")
                .then().assertThat().statusCode(404);
    }
}
