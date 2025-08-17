package test.TodosTests;

import POJO.Serialization.Comments.addComment;
import POJO.Serialization.Comments.updateComment;
import POJO.Serialization.Todos.TodosTest;
import POJO.Serialization.Todos.updateTodo;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class InvalidTodoTests {
    @Test
    public void getalltodos_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/todoss").then()
                .statusCode(404);
    }@Test
    public void getatodo_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().get("/todos/-1").then()
                .statusCode(404);
    }

    @Test
    public void gettodobyindex_invalid(){
        given().baseUri("https://dummyjson.com").when().get("/todos/user/550")
                .then().assertThat().statusCode(404);
    }

    @Test
    public void updatetodo_invalid(){
        updateTodo repos = new updateTodo();
        repos.setCompleted(false);
        given().baseUri("https://dummyjson.com").body(repos)
                .when().put("/todos/1000")
                .then().assertThat().statusCode(404);
    }
    @Test
    public void addtodo_invalid(){
        TodosTest repos = new TodosTest();
        repos.setTodo("Use DummyJSON in the project");
        repos.setCompleted(false);
        repos.setUserId(5);
        given().baseUri("https://dummyjson.com")
                .body(repos)
                .when().post("/todos/add/1").then()
                .assertThat().statusCode(404);
    }
    @Test
    public void deletetodo_invalid(){
        given().baseUri("https://dummyjson.com")
                .when().delete("/todos/550")
                .then().assertThat().statusCode(404);
    }

}
