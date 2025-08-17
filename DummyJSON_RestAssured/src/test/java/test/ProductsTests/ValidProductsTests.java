package test.ProductsTests;

import POJO.Deserialization.Products.*;
import POJO.Serialization.Products.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import java.util.Collections;
import java.util.List;

public class ValidProductsTests {
    private static RequestSpecification requestSpec;
    private static int createdProductId;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .log(io.restassured.filter.log.LogDetail.ALL)
                .build();
    }

    @Test
    public void getAllProducts_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(response.products);
        Assert.assertTrue(response.products.size() <= 30);
    }

    @Test
    public void getProductById_Valid() {
        ProductResponse product = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/1")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
        Assert.assertEquals(product.id, 1);
    }

    @Test
    public void searchProducts_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("q", "phone")
                .when()
                .get("/products/search")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(response.products);
        Assert.assertTrue(response.total >= 0);
    }

    @Test
    public void getProductsByCategory_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/category/smartphones")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(response.products);
    }

    @Test
    public void getAllCategories_Valid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/categories");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void getCategoryList_Valid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/category-list");
        Assert.assertEquals(response.getStatusCode(), 200);
        Assert.assertTrue(response.as(List.class).size() > 0);
    }

    @Test
    public void addProduct_Valid() {
        AddProductRequest request = new AddProductRequest();
        request.title = "BMW Pencil";
        request.price = 10.5;
        request.category = "beauty";
        request.tags = Collections.singletonList("test");
        ProductResponse response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .extract()
                .as(ProductResponse.class);
        createdProductId = response.id;
        Assert.assertEquals(response.title, "BMW Pencil");
    }

    @Test
    public void deleteProduct_Valid() {
        DeleteProductResponse response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/products/1")
                .then()
                .statusCode(200)
                .extract()
                .as(DeleteProductResponse.class);
        Assert.assertTrue(response.isDeleted);
        Assert.assertEquals(response.id, createdProductId);
    }

    @Test
    public void getProductsWithPagination_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("limit", 10)
                .queryParam("skip", 10)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertEquals(response.products.size(), 10);
        Assert.assertEquals(response.skip, 10);
    }

    @Test
    public void getProductsWithSorting_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("sortBy", "title")
                .queryParam("order", "asc")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(response.products);
    }

    @Test
    public void getProductsWithSelectFields_Valid() {
        ProductsListResponse response = RestAssured.given()
                .spec(requestSpec)
                .queryParam("select", "title,price")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(response.products);
        Assert.assertNotNull(response.products.get(0).title);
        Assert.assertNotNull(response.products.get(0).price);
    }
} 