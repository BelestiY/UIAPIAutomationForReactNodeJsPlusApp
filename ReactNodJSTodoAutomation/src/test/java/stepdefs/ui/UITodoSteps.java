package stepdefs.ui;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;
import pages.LoginPage;
import pages.TodoPage;
import pages.PlaywrightFactory;

import static org.junit.jupiter.api.Assertions.*;

public class UITodoSteps {
    private Page page;
    private LoginPage loginPage;
    private TodoPage todoPage;
    private PlaywrightFactory factory;

    @SuppressWarnings("unused")
    private String actualOutcome;

    @Before
    public void setUp() {
        factory = new PlaywrightFactory();
        factory.init();
        page = factory.getPage();
        loginPage = new LoginPage(page);
        todoPage = new TodoPage(page);
    }

    @After
    public void tearDown() {
        factory.close();
    }

    @Given("I am on the login page")
    public void user_is_on_login_page() {
        loginPage.navigate();
    }

    @When("I login with username {string} and password {string}")
    public void i_login_with_credentials(String username, String password) {
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();
    }

    @And("the user clicks the login button")
    public void user_clicks_login() {
        loginPage.clickLogin();
    }

    @Then("I should {string}")
    public void outcome_should_be(String expectedOutcome) {
        Locator logoutButton = page.locator("button:has-text('Logout')");
        if(logoutButton.isVisible()) {
            actualOutcome = "see the logout button";
        } else {
            actualOutcome = "see an error message";
        }
    }

    @When("I add a new todo with text {string}")
    public void user_adds_todo(String itemText) {
        todoPage.addTodoItem(itemText);
    }

    @Then("I should see the todo item {string}")
    public void todo_item_should_be_visible(String itemText) {
        assertTrue(todoPage.isTodoItemPresent(itemText));
    }

    @When("the user edits the todo item {string} to {string}")
    public void user_edits_todo(String oldText, String newText) {
        todoPage.editTodoItem(oldText, newText);
    }

    @When("the user deletes the todo item {string}")
    public void user_deletes_todo_item(String itemText) {
        todoPage.deleteTodoItem(itemText);
    }

    @Then("the todo item {string} should not be visible")
    public void todo_item_should_not_be_visible(String itemText) {
        assertFalse(todoPage.isTodoItemPresent(itemText));
    }
}