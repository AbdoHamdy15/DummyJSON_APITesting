package test.CartsTests;

import POJO.Deserialization.Carts.*;
import POJO.Serialization.Carts.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ValidCartsTests {
    private static RequestSpecification requestSpec;
    private static int createdCartId;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @Test
    public void getAllCarts_Valid() {
        CartsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/carts")
                .then()
                .statusCode(200)
                .extract()
                .as(CartsListResponse.class);
        Assert.assertNotNull(response.carts);
        Assert.assertTrue(response.carts.size() <= 30);
    }

    @Test
    public void getCartById_Valid() {
        CartResponse cart = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/carts/1")
                .then()
                .statusCode(200)
                .extract()
                .as(CartResponse.class);
        Assert.assertEquals(cart.id, 1);
    }

    @Test
    public void getCartsByUser_Valid() {
        CartsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/carts/user/5")
                .then()
                .statusCode(200)
                .extract()
                .as(CartsListResponse.class);
        Assert.assertNotNull(response.carts);
        Assert.assertTrue(response.carts.size() >= 0);
    }

    @Test
    public void addCart_Valid() {
        AddCartRequest request = new AddCartRequest();
        request.userId = 1;
        CartProductRequest p1 = new CartProductRequest();
        p1.id = 144;
        p1.quantity = 2;
        CartProductRequest p2 = new CartProductRequest();
        p2.id = 98;
        p2.quantity = 1;
        request.products = Arrays.asList(p1, p2);
        CartResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/carts/add")
                .then()
                .statusCode(201)
                .extract()
                .as(CartResponse.class);
        createdCartId = response.id;
        Assert.assertEquals(response.userId, 1);
        Assert.assertTrue(response.products.size() > 0);
    }

    @Test
    public void updateCart_Valid() {
        UpdateCartRequest request = new UpdateCartRequest();
        request.merge = true;
        CartProductRequest p = new CartProductRequest();
        p.id = 1;
        p.quantity = 1;
        request.products = Collections.singletonList(p);
        CartResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .put("/carts/" + createdCartId)
                .then()
                .statusCode(200)
                .extract()
                .as(CartResponse.class);
        Assert.assertEquals(response.id, createdCartId);
        Assert.assertTrue(response.products.size() > 0);
    }

    @Test
    public void deleteCart_Valid() {
        DeleteCartResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/carts/" + createdCartId)
                .then()
                .statusCode(200)
                .extract()
                .as(DeleteCartResponse.class);
        Assert.assertTrue(response.isDeleted);
        Assert.assertEquals(response.id, createdCartId);
    }
} 