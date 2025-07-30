Feature: Todo App Login Feature Functional Test

  Background:
    Given I am on the login page

  @ui
  Scenario Outline: Login with different credentials
    When I login with username "<username>" and password "<password>"
    Then I should "<outcome>"

    Examples:
      | username | password    | outcome                  |
      | admin    | password    | see the logout button    |
      | fake     | wrong       | see an error message     |


  @api
  Scenario Outline: Login with valid credential
    When I login with username "<username>" and password "<password>"
    Then I should get a status code of 200

    Examples:
      | username | password    |
      | admin    | password    |