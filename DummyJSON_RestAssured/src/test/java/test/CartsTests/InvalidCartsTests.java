package test.CartsTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import POJO.Serialization.Carts.AddCartRequest;
import POJO.Serialization.Carts.UpdateCartRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidCartsTests {
    private static RequestSpecification requestSpec;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @Test
    public void getAllCarts_InvalidEndpoint() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/cartz");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getCartById_Invalid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/carts/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getCartsByUser_Invalid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/carts/user/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void addCart_MissingFields() {
        AddCartRequest request = new AddCartRequest();
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/carts/add");
        Assert.assertTrue(response.getStatusCode() == 400 || response.getStatusCode() == 500);
    }

    @Test
    public void updateCart_InvalidId() {
        UpdateCartRequest request = new UpdateCartRequest();
        request.merge = true;
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .put("/carts/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteCart_InvalidId() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/carts/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }
} 