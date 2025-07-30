Feature: Todo App Functional Tests

  Background:
    Given I am on the login page
    When I login with username "admin" and password "password"
#     And the user clicks the login button

  @ui
  Scenario Outline: Create a new todo
    When I add a new todo with text "<todoItem>"
    Then I should see the todo item "<todoItem>"

  Examples:
      | todoItem     |
      | Buy milk     |
      | Buy eggs     |
      | Buy yoghurt  |

  @ui
  Scenario Outline: Edit an existing todo
    When the user edits the todo item "<oldItem>" to "<newItem>"
    Then I should see the todo item "<newItem>"

  Examples:
      | oldItem      | newItem      |
      | Buy milk     | Buy tomato   |
      | Buy eggs     | Buy coffee   |

  @ui
  Scenario Outline: Delete a todo
    When the user deletes the todo item "<deleteItem>"
    Then the todo item "<deleteItem>" should not be visible

  Examples:
      | deleteItem         |
      | Buy coffee         |
      | Buy tomato         |

  @api
  Scenario Outline: Create a new todo
    When I add a new todo with text via api "<todoItem>"
    Then creating a todo item should return a status code of 201

  Examples:
      | todoItem     |
      | Buy milk     |
      | Buy eggs     |
      | Buy yoghurt  |

  @api
  Scenario Outline: Edit an existing todo
    When the user edits the todo item "<oldItem>" to "<newItem>" via api
    Then I updated item should be the "<newItem>"

  Examples:
      | oldItem      | newItem      |
      | Buy milk     | Buy tomato   |
      | Buy eggs     | Buy coffee   |

  @api
  Scenario Outline: Delete a todo
    When the user deletes the todo item "<deleteItem>" via api
    Then the todo item "<deleteItem>" should not be visible via api

  Examples:
      | deleteItem         |
      | Buy coffee         |
      | Buy tomato         |