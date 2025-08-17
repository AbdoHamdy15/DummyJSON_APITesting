package test.PostsTests;

import POJO.Serialization.Posts.addposts;
import POJO.Serialization.Posts.updateposts;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidPostsTests {

    @Test
    public void getall(){
        given().baseUri("https://dummyjson.com").
                header("Connection","keep-alive")
                .when().get("/posts").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getapost(){
        given().baseUri("https://dummyjson.com").log().all()
                .when().get("/posts/1")
                .then().log().all()
                .assertThat().statusCode(200);

    }
    @Test
    public void searchforposts(){
        given().baseUri("https://dummyjson.com").param("q","love")
                .log().all()
                .when().get("/posts/search")
                .then().assertThat().statusCode(200)
                .log().all();

    }
    @Test
    public void orderposts(){
        given().baseUri("https://dummyjson.com")
                .params("sortBy","title" , "order","asc").log().all()
                .when().get("/posts")
                .then().log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void addpost(){
        addposts adpo = new addposts();
        adpo.setTitle("I am in love with someone.");
        adpo.setUserId(5);
        given().baseUri("https://dummyjson.com").log().all()
                .body(adpo)
                .when().post("/posts/add")
                .then().statusCode(400);
    }

    @Test
    public void updatepost(){
        updateposts updpo = new updateposts();
        updpo.setTitle("I think I should shift to the moon");
        given().baseUri("https://dummyjson.com")
                .body(updpo)
                .when().put("/posts/1")
                .then().assertThat().statusCode(200);


    }


    @Test
    public void deletepost(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/posts/1")
                .then().assertThat().statusCode(200)
                .log().all();
    }
}
