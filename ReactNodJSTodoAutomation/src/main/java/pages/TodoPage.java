package pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;

public class TodoPage {
    private final Page page;

    // Locators
    private final String todoInput = "Enter a new todo";
    private final String addButton = "button:has-text('Add')";
    private final String editButton = "button:has-text('Edit')";
    @SuppressWarnings("unused")
    private final String todoList = ".todo-list";
    private final String logoutButton = "button:has-text('Logout')";

    // Constructor
    public TodoPage(Page page) {
        this.page = page;
    }

    public void addTodoItem(String itemText) {
        page.getByPlaceholder(todoInput).fill(itemText);
        page.click(addButton);
    }

    public boolean isTodoItemPresent(String itemText) {
        return page.locator("li.todo-item >> input[type='text'][value='" + itemText + "']").nth(0).isVisible();
    }

    public void editTodoItem(String oldText, String newText) {
        String todoLabel = "li.todo-item:has(input[value='" + oldText + "']) >> button:has-text('Edit')";
        page.click(todoLabel);

        String todoLabelUpdate = "input[type='text'][value='" + oldText + "']";
        page.locator(todoLabelUpdate).fill(newText);
        page.click(editButton);
    }

    public void deleteTodoItem(String itemText) {
        Locator deleteButton = page.locator("li.todo-item:has(input[value='" + itemText + "']) button:has-text('Delete')");
        deleteButton.click();
    }

    public void logout() {
        page.click(logoutButton);
    }
}
