package test.QoutesTests;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;



public class ValidQoutesTesta {


        @Test
        public void getall(){
            Response response=
            given().baseUri("https://dummyjson.com").
                    header("Connection","keep-alive")
                .when().get("/quotes").
                then().log().all().statusCode(200)
                    .extract().response();
    }

    @Test
    public void getone(){
        given().baseUri("https://dummyjson.com")
                .when().get("quotes/1").
                then().assertThat().statusCode(200);
                //.assertThat().body("qoutes.id" , equals(1));

    }

    @Test
    public void getrandom(){
        given().baseUri("https://dummyjson.com").header("Connection", "Connection")
                .when().get("/quotes/random").
                then().assertThat().statusCode(200);

    }
    @Test
    public void getlimitedquotes(){
        given().baseUri("https://dummyjson.com").params("limit",3,"skip",10)
                .when().get("/quotes").
                then().log().all().assertThat().statusCode(200);

    }
}
