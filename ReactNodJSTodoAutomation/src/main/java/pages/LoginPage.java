package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class LoginPage {
    private final Page page;

    // Locators
    private final String usernameInput = "Username";
    private final String passwordInput = "Password";
    private final String loginButton = "button[type='submit']";
    private final String errorMessage = "Invalid username or password";

    @SuppressWarnings("unused")
    private final String logoutButton = "button:has-text('Logout')";

    // Constructor
    public LoginPage(Page page) {
        this.page = page;
    }

    // Methods
    public void navigate() {
        page.navigate("http://localhost:3000/");
    }

    public void enterUsername(String username) {
        page.getByPlaceholder(usernameInput).fill(username);
    }

    public void enterPassword(String password) {
        page.getByPlaceholder(passwordInput).fill(password);
    }

    public void clickLogin() {
        page.click(loginButton);
    }

    public boolean isLoginSuccessful() {
        // Wait a bit for either dashboard or error message to appear
        page.waitForTimeout(5000); // Optional: Replace with more robust waits in real code

        if (page.getByText(errorMessage).isVisible()) {
            return false;
        } else {
            return true;
        }
    }

    public String getErrorMessage() {
        return page.textContent(errorMessage);
    }

    public boolean isOnTodoPage() {
        return page.url().contains("/todo");
    }

    public boolean isRedirectedToTodoPage() {
        Locator logoutButton = page.locator("button:has-text('Logout')");
        try {
            // return page.url().contains("/todo");
            return logoutButton.isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
