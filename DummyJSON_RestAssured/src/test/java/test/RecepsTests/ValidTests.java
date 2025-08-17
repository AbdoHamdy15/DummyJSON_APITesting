package test.RecepsTests;

import POJO.Serialization.Posts.addposts;
import POJO.Serialization.Posts.updateposts;
import POJO.Serialization.Recipes.Addrecipes;
import POJO.Serialization.Recipes.Updaterecipe;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidTests {
    @Test
    public void getall(){
        given().baseUri("https://dummyjson.com").
                header("Connection","keep-alive")
                .when().get("/recipes").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getarecipe(){
        given().baseUri("https://dummyjson.com").log().all()
                .when().get("/recipes/1")
                .then().log().all()
                .assertThat().statusCode(200);

    }
    @Test
    public void addrecipe(){
        Addrecipes adr = new Addrecipes();
        adr.setName("Tasty Pizza");
        adr.setCuisine("Italian");
        adr.setDifficulty("Easy");
        adr.setServings(2);
        adr.setCookTimeMinutes(20);
        adr.setPrepTimeMinutes(15);
        adr.setCaloriesPerServing(300);
        given().baseUri("https://dummyjson.com").log().all()
                .body(adr)
                .when().post("/recipes/add")
                .then().statusCode(200);
    }

    @Test
    public void updatepost(){
        Updaterecipe updd = new Updaterecipe();
        updd.setName("Pizza Chicken Ranch");
        given().baseUri("https://dummyjson.com")
                .body(updd)
                .when().put("/recipes/1")
                .then().assertThat().statusCode(200);


    }
    @Test
    public void deleterecipe(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/recipes/1")
                .then().log().all()
                .assertThat().statusCode(200);
    }





}
