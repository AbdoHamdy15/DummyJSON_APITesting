package test.e2e;

import POJO.Deserialization.Products.*;
import POJO.Serialization.Products.*;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.testng.Assert;
import org.testng.annotations.*;

public class ProductE2ETests {
    private static final Logger logger = LoggerFactory.getLogger(ProductE2ETests.class);
    private static RequestSpecification requestSpec;
    private static int createdProductId;

    @BeforeClass
    public void setupSpec() {
        requestSpec = new RequestSpecBuilder()
                .setBaseUri("https://dummyjson.com")
                .setContentType(ContentType.JSON)
                .build();
    }

    @Test(groups = {"e2e", "product", "smoke"})
    public void productLifecycleWorkflow() {
        logger.info("=== Starting Product Lifecycle E2E Test ===");
        
        // Step 1: Get all products (from getAllProducts_Valid)
        logger.info("Step 1: Getting all products");
        ProductsListResponse allProducts = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(allProducts.products);
        logger.info("Found {} products", allProducts.products.size());

        // Step 2: Get specific product (from getProductById_Valid)
        logger.info("Step 2: Getting product with ID 1");
        ProductResponse product = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/1")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
        Assert.assertEquals(product.id, 1);
        logger.info("Product retrieved: {}", product.title);

        // Step 3: Search for products (from searchProducts_Valid)
        logger.info("Step 3: Searching for products with 'phone'");
        ProductsListResponse searchResults = RestAssured.given()
                .spec(requestSpec)
                .queryParam("q", "phone")
                .when()
                .get("/products/search")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(searchResults.products);
        logger.info("Search returned {} products", searchResults.total);

        // Step 4: Get products by category (from getProductsByCategory_Valid)
        logger.info("Step 4: Getting smartphone products");
        ProductsListResponse smartphoneProducts = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/category/smartphones")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(smartphoneProducts.products);
        logger.info("Found {} smartphone products", smartphoneProducts.products.size());

        // Step 5: Add new product (from addProduct_Valid)
        logger.info("Step 5: Adding new product");
        AddProductRequest newProduct = new AddProductRequest();
        newProduct.title = "Test Product";
        newProduct.description = "A test product for E2E testing";
        newProduct.price = 99.99;
        newProduct.discountPercentage = 10.0;
        newProduct.rating = 4.5;
        newProduct.stock = 100;
        newProduct.brand = "TestBrand";
        newProduct.category = "electronics";

        ProductResponse createResponse = RestAssured.given()
                .spec(requestSpec)
                .body(newProduct)
                .when()
                .post("/products/add")
                .then()
                .statusCode(201)
                .extract()
                .as(ProductResponse.class);
        createdProductId = createResponse.id;
        logger.info("Product created with ID: {}", createdProductId);

        // Step 6: Update existing product (from updateProduct_Valid)
        logger.info("Step 6: Updating existing product");
        UpdateProductRequest updateRequest = new UpdateProductRequest();
        updateRequest.title = "Updated Test Product";
        updateRequest.price = 149.99;
        updateRequest.stock = 50;

        ProductResponse updatedProduct = RestAssured.given()
                .spec(requestSpec)
                .body(updateRequest)
                .when()
                .put("/products/1")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductResponse.class);
        Assert.assertEquals(updatedProduct.title, "Updated Test Product");
        logger.info("Product updated successfully");

        // Step 7: Delete existing product (from deleteProduct_Valid)
        logger.info("Step 7: Deleting existing product");
        DeleteProductResponse deleteResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .delete("/products/1")
                .then()
                .statusCode(200)
                .extract()
                .as(DeleteProductResponse.class);
        Assert.assertEquals(deleteResponse.id, 1);
        logger.info("Product deleted successfully");
        
        logger.info("=== Product Lifecycle E2E Test Completed ===");
    }

    @Test(groups = {"e2e", "product", "regression"})
    public void productDiscoveryWorkflow() {
        logger.info("=== Starting Product Discovery E2E Test ===");
        
        // Step 1: Get all categories (from getAllCategories_Valid)
        logger.info("Step 1: Getting all product categories");
        Response categoriesResponse = RestAssured.given()
                .spec(requestSpec)
                .when()
                .get("/products/categories");
        Assert.assertEquals(categoriesResponse.getStatusCode(), 200);
        logger.info("Categories retrieved successfully");

        // Step 2: Get products with pagination (from getProductsWithPagination_Valid)
        logger.info("Step 2: Getting products with pagination");
        ProductsListResponse paginatedProducts = RestAssured.given()
                .spec(requestSpec)
                .queryParam("limit", 5)
                .queryParam("skip", 0)
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertEquals(paginatedProducts.products.size(), 5);
        logger.info("Pagination returned {} products", paginatedProducts.products.size());

        // Step 3: Get products with sorting (from getProductsWithSorting_Valid)
        logger.info("Step 3: Getting products with sorting");
        ProductsListResponse sortedProducts = RestAssured.given()
                .spec(requestSpec)
                .queryParam("sortBy", "title")
                .queryParam("order", "asc")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(sortedProducts.products);
        logger.info("Sorting returned {} products", sortedProducts.products.size());

        // Step 4: Get products with select fields (from getProductsWithSelectFields_Valid)
        logger.info("Step 4: Getting products with select fields");
        ProductsListResponse selectedProducts = RestAssured.given()
                .spec(requestSpec)
                .queryParam("select", "title,price,rating")
                .when()
                .get("/products")
                .then()
                .statusCode(200)
                .extract()
                .as(ProductsListResponse.class);
        Assert.assertNotNull(selectedProducts.products);
        logger.info("Select fields returned {} products", selectedProducts.products.size());
        
        logger.info("=== Product Discovery E2E Test Completed ===");
    }

    @AfterClass
    public void cleanup() {
        logger.info("Product E2E tests cleanup completed");
    }
}
