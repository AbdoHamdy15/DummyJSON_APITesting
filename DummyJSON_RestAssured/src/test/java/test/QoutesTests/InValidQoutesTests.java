package test.QoutesTests;

import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

public class InValidQoutesTests {


    @Test
    public void gatallqoutes_invalid(){
        given().baseUri("https://dummyjson.com").
                when().get("quotesa")
                .then().statusCode(404);
    }

    @Test
    public void getoneqoute_invalid(){

        given().baseUri("https://dummyjson.com")
                .when().get("/quotes/35").then().assertThat().statusCode(404);
    }

    @Test
    public void getrandomqoutes_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/quotes/randomm").then().statusCode(404);
    }



}
