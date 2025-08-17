package test.TodosTests;

import POJO.Serialization.Todos.TodosTest;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class ValidTodoTest {


    @Test
    public void getall(){
        given().baseUri("https://dummyjson.com").
                headers("Connection","keep-alive","User-Agent","User-Agent")
                .when().get("/todos").
                then().log().all().assertThat().statusCode(200);
    }

    @Test
    public void getatodo(){
        given().baseUri("https://dummyjson.com").log().all()
                .when().get("/todos/1")
                .then().log().all()
                .assertThat().statusCode(200);

    }
    @Test
    public void getlimitedtodos(){
        given().baseUri("https://dummyjson.com")
                .log().all()
                .when().get("/todos/user/5")
                .then().assertThat().statusCode(200)
                .log().all();

    }
    @Test
    public void getrandomtodos(){
        given().baseUri("https://dummyjson.com").log().all()
                .when().get("/todos/random")
                .then().log().all()
                .assertThat().statusCode(200);
    }
    @Test
    public void addtodo(){
        TodosTest res = new TodosTest();
        res.setTodo("Use DummyJSON in the project");
        res.setCompleted(false);
        res.setUserId(5);
        given().baseUri("https://dummyjson.com").log().all()
                .body(res)
                .when().post("/todos/add")
                .then().statusCode(400)
                .extract().body();
    }

    @Test
    public void updatetpdo(){
        TodosTest ress = new TodosTest();
        ress.setCompleted(false);
        given().baseUri("https://dummyjson.com")
                .body(ress)
                .when().put("/todos/1")
                .then().assertThat().statusCode(200);


    }


    @Test
    public void deletetodo(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/todos/1")
                .then().assertThat().statusCode(200)
                .log().all();
    }
}
