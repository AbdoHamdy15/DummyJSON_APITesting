package test.PostsTests;

import POJO.Serialization.Posts.addposts;
import POJO.Serialization.Posts.updateposts;
import POJO.Serialization.Todos.TodosTest;
import POJO.Serialization.Todos.updateTodo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class InvalidPostsTests {

    @Test
    public void getallPosts_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/postss").then()
                .statusCode(404);
    }
    @Test
    public void getapost_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/posts/-1").then()
                .statusCode(404);
    }

    @Test
    public void orderposts_invalid(){
        given().baseUri("https://dummyjson.com")
                .params("sortBy","title" , "order","des").log().all()
                .when().get("/posts")
                .then().log().all()
                .assertThat().statusCode(400);
    }

    @Test
    public void updatepost_invalid(){
        updateposts updpoo = new updateposts();
        updpoo.setTitle("I think I should shift to the moon");
        given().baseUri("https://dummyjson.com")
                .body(updpoo)
                .when().put("/posts/-100")
                .then().assertThat().statusCode(404);
    }
    @Test
    public void addpost_invalid(){
        addposts adpoo = new addposts();
        adpoo.setTitle("I am in love with someone.");
        adpoo.setUserId(5);
        given().baseUri("https://dummyjson.com")
                .body(adpoo)
                .when().post("/posts/addd").then()
                .assertThat().statusCode(404);
    }
    @Test
    public void deletetpost_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/posts/-1")
                .then().assertThat().statusCode(404)
                .log().all();
    }
}
