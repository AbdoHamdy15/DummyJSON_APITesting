package test.ProductsTests;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import POJO.Serialization.Products.AddProductRequest;
import POJO.Serialization.Products.UpdateProductRequest;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

public class InvalidProductsTests {
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
    public void getAllProducts_InvalidEndpoint() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/productz");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getProductById_Invalid() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getAllCategories_InvalidEndpoint() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/categoriess");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void getCategoryList_InvalidEndpoint() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/category-listz");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void updateProduct_InvalidId() {
        UpdateProductRequest request = new UpdateProductRequest();
        request.title = "Invalid Update";
        Response response = RestAssured.given()
                .spec(requestSpec)
                .body(request)
                .when()
                .put("/products/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }

    @Test
    public void deleteProduct_InvalidId() {
        Response response = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/products/99999");
        Assert.assertEquals(response.getStatusCode(), 404);
    }


} 