package test.RecepsTests;

import POJO.Serialization.Posts.addposts;
import POJO.Serialization.Posts.updateposts;
import POJO.Serialization.Recipes.Addrecipes;
import POJO.Serialization.Recipes.Updaterecipe;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class Invalidtests {

    @Test
    public void getallreceps_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/recipess").then()
                .log().all().statusCode(404);
    }
    @Test
    public void getarecep_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/recipes/-1").then()
                .statusCode(404);
    }

    @Test
    public void updatepost_invalid(){
        Updaterecipe updd = new Updaterecipe();
        updd.setName("Pizza Chicken Ranch");
        given().baseUri("https://dummyjson.com")
                .body(updd)
                .when().put("/recipes/-1")
                .then().assertThat().statusCode(404);
    }
    @Test
    public void addrecipe_invalid(){
        Addrecipes adrr = new Addrecipes();
        adrr.setName("Tasty Pizza");
        adrr.setCuisine("Italian");
        adrr.setDifficulty("Easy");
        adrr.setServings(2);
        adrr.setCookTimeMinutes(20);
        adrr.setPrepTimeMinutes(15);
        adrr.setCaloriesPerServing(300);

        given().baseUri("https://dummyjson.com")
                .body(adrr)
                .when().post("/recipes/addd").then()
                .assertThat().statusCode(404);
    }
    @Test
    public void deletetrecipe_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/recipes/-1")
                .then().assertThat().statusCode(404)
                .log().all();
    }

}
