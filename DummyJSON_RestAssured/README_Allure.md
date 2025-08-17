# Allure Reporting Setup for DummyJSON RestAssured Project

## Overview
This project now includes Allure reporting capabilities to generate beautiful, interactive test reports. Allure provides detailed insights into test execution, including steps, attachments, and test metadata.

## Allure Annotations Used

### 1. **@Epic**
- Used at class level to group related features
- Example: `@Epic("DummyJSON API Testing")`

### 2. **@Feature**
- Used at class level to describe a specific feature
- Example: `@Feature("User Management")`

### 3. **@Story**
- Used at test method level to describe a specific user story
- Example: `@Story("User Retrieval")`

### 4. **@Severity**
- Used to categorize test importance
- Levels: `CRITICAL`, `HIGH`, `MEDIUM`, `LOW`
- Example: `@Severity(SeverityLevel.CRITICAL)`

### 5. **@Description**
- Used to provide detailed test description
- Example: `@Description("This test verifies that we can successfully retrieve all users")`

### 6. **@Step**
- Used to break down test into logical steps
- Example: `Allure.step("Send GET request to /users endpoint");`

### 7. **@Attachment**
- Used to attach files, screenshots, or data to reports
- Example: `Allure.addAttachment("Response Body", "application/json", response.getBody().asString());`

## Project Structure

```
DummyJSON_RestAssured/
├── src/
│   └── test/
│       ├── java/
│       │   └── test/
│       │       ├── listeners/
│       │       │   └── AllureTestListener.java
│       │       └── AllureSampleTests.java
│       └── resources/
│           ├── allure-categories.json
│           └── allure-environment.properties
├── allure.properties
├── pom.xml
└── testng-all.xml
```

## Running Tests with Allure

### 1. **Run Tests**
```bash
mvn clean test
```

### 2. **Generate Allure Report**
```bash
# Install Allure command line tool first
# Windows: scoop install allure
# macOS: brew install allure
# Linux: sudo apt-add-repository ppa:qameta/allure && sudo apt-get update && sudo apt-get install allure

# Generate report
allure serve target/allure-results
```

### 3. **Generate Static Report**
```bash
allure generate target/allure-results -o target/allure-report --clean
```

## Allure Report Features

### 1. **Dashboard**
- Overview of test execution
- Pass/Fail statistics
- Duration trends
- Severity distribution

### 2. **Test Results**
- Detailed test steps
- Request/Response data
- Screenshots and attachments
- Error details

### 3. **Categories**
- Failed tests
- Broken tests
- Ignored tests
- Custom categories

### 4. **Environment**
- Test environment details
- Configuration information
- System properties

## Example Test with Allure Annotations

```java
@Test(description = "Get all users from the API")
@Story("User Retrieval")
@Severity(SeverityLevel.CRITICAL)
@Description("This test verifies that we can successfully retrieve all users from the DummyJSON API")
public void testGetAllUsers() {
    Allure.step("Send GET request to /users endpoint");
    Response response = given()
            .when()
            .get("/users")
            .then()
            .extract().response();

    Allure.step("Verify response status code is 200");
    Assert.assertEquals(response.getStatusCode(), 200);

    Allure.step("Verify response contains users data");
    Assert.assertNotNull(response.jsonPath().get("users"));
    
    Allure.addAttachment("Response Body", "application/json", response.getBody().asString());
}
```

## Configuration Files

### 1. **allure.properties**
- Configures Allure behavior
- Sets result and report directories
- Defines attachment types

### 2. **allure-categories.json**
- Defines custom test categories
- Helps organize test results
- Provides better filtering options

### 3. **allure-environment.properties**
- Environment information
- System configuration details
- Version information

## Benefits of Allure Reporting

1. **Visual Appeal**: Beautiful, interactive HTML reports
2. **Detailed Information**: Step-by-step test execution
3. **Attachments**: Screenshots, logs, and data files
4. **Filtering**: Multiple ways to filter and search tests
5. **Trends**: Historical data and trends analysis
6. **Integration**: Works with CI/CD pipelines
7. **Customization**: Highly customizable reports

## Troubleshooting

### Common Issues:
1. **Allure command not found**: Install Allure CLI tool
2. **No results generated**: Check if tests are running successfully
3. **Missing attachments**: Verify file paths and permissions
4. **Report not loading**: Check browser compatibility

### Solutions:
1. Install Allure CLI: `scoop install allure` (Windows)
2. Run tests first: `mvn clean test`
3. Check target directory: `target/allure-results`
4. Use modern browser: Chrome, Firefox, Safari

## Next Steps

1. Add more Allure annotations to existing tests
2. Customize categories and environment information
3. Integrate with CI/CD pipeline
4. Add custom listeners for specific requirements
5. Configure email notifications for failed tests





