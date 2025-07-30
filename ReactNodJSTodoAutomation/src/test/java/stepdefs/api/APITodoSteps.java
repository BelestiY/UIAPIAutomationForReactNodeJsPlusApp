package stepdefs.api;

import io.restassured.RestAssured;
import io.restassured.common.mapper.TypeRef;
import io.restassured.response.Response;
import io.restassured.http.ContentType;
import io.cucumber.java.en.*;

import java.util.*;

import static io.restassured.RestAssured.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class APITodoSteps {

    private final String BASE_URL = "http://localhost:5000";
    @SuppressWarnings("unused")
    private static int lastCreatedItemId;

    private Response loginResponse;
    private Response createTodoItemResponse;

    private String sessionCookie;

    Map<String, Object> updatedResponse;

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        RestAssured.baseURI = BASE_URL;
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_credentials(String username, String password) {
        String payload = String.format("{\"username\":\"%s\", \"password\":\"%s\"}", username, password);
        loginResponse = given()
                .contentType(ContentType.JSON)
                .body(payload)
                .post("/api/login");

        String setCookieHeader = loginResponse.getHeader("Set-Cookie");
        if (setCookieHeader != null && setCookieHeader.contains("connect.sid")) {
            sessionCookie = setCookieHeader.split(";")[0].split("=")[1]; // grab just the value
        } else {
            throw new RuntimeException("Session cookie not found in login response!");
        }
    }

    @Then("I should get a status code of {int}")
    public void i_should_get_a_status_code_of_status_code(int expectedStatusCode) {
        assertEquals(expectedStatusCode, loginResponse.getStatusCode());
    }

    @When("I add a new todo with text via api {string}")
    public void i_add_a_new_todo_with_text(String todoText) {
        String payload = String.format("{\"text\":\"%s\"}", todoText);
        createTodoItemResponse = given()
                .contentType(ContentType.JSON)
                .cookie("connect.sid", sessionCookie)
                .body(payload)
                .post("/api/todos");

        // response.then().statusCode(201);
        lastCreatedItemId = createTodoItemResponse.jsonPath().getInt("id");
    }

    @Then("creating a todo item should return a status code of {int}")
    public void creating_a_todo_item_should_return_a_status_code_of_status_code(int expectedStatusCode) {
        assertEquals(expectedStatusCode, createTodoItemResponse.getStatusCode());
    }

    @When("the user edits the todo item {string} to {string} via api")
    public void user_edits_todo_item(String oldItem, String newItem) {
        List<Map<String, Object>> items = given()
            .cookie("connect.sid", sessionCookie)
            .get("/api/todos")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getObject("", new TypeRef<List<Map<String, Object>>>() {});

        for (Map<String, Object> item : items) {
            if (item.get("text").equals(oldItem)) {
                long itemId = ((Number) item.get("id")).longValue();

                String payload = String.format("{\"text\":\"%s\"}", newItem);

                Response putResponse = given()
                    .contentType(ContentType.JSON)
                    .cookie("connect.sid", sessionCookie)
                    .body(payload)
                    .when()
                    .put("/api/todos/" + itemId)
                    .then()
                    .statusCode(200)
                    .extract()
                    .response();

                updatedResponse = putResponse.jsonPath().getMap("");

                break;
            }
        }
    }

    @Then("I updated item should be the {string}")
    public void I_updated_item_should_be_the(String newItem) {
        assert updatedResponse.get("text").equals(newItem);
    }

    @When("the user deletes the todo item {string} via api")
    public void user_deletes_todo_item(String itemText) {
        List<Map<String, Object>> items = given()
            .cookie("connect.sid", sessionCookie)
            .get("/api/todos")
            .then()
            .statusCode(200)
            .extract()
            .jsonPath()
            .getObject("", new TypeRef<List<Map<String, Object>>>() {});

        for (Map<String, Object> item : items) {
            if (item.get("text").equals(itemText)) {
                long itemId = ((Number) item.get("id")).longValue();
                given()
                    .cookie("connect.sid", sessionCookie)
                    .delete("/api/todos/" + itemId)
                    .then()
                    .statusCode(200);
                    // .extract()
                    // .response();
                break;
            }
        }
    }

    @Then("the todo item {string} should not be visible via api")
    public void todo_item_should_not_be_visible(String deletedItem) {
        List<Map<String, Object>> todos = given()
        .cookie("connect.sid", sessionCookie)
        .get("/api/todos")
        .then()
        .statusCode(200)
        .extract()
        .jsonPath()
        .getObject("", new TypeRef<List<Map<String, Object>>>() {});

        boolean itemStillExists = todos.stream()
            .anyMatch(todo -> todo.get("text").equals(deletedItem));

        assertFalse(itemStillExists, "The todo item '" + deletedItem + "' was not deleted.");
    }
}
