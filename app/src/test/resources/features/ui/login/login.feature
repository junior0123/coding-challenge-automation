@Login @UI
Feature: Login to the application

  @BL-T001 @SmokeTest
  Scenario Outline: A user with valid credentials should be able to log in without any issues
    Given I navigate to the Login page
    When I login to the page using valid credentials for "<User Role>"
    Then I should login to home page successfully

    Examples:
      | User Role     |
      | administrator |

  @BL-T002 @SmokeTest
  Scenario: A user should able to logout successfully
    Given I navigate to the Login page
    When I login to the page using valid credentials for "administrator"
    Then I should login to home page successfully
    When I logout from the page
    Then I should logout successfully

  @BL-T003
  Scenario Outline: A user should not be able to log in with invalid credentials
    Given I navigate to the Login page
    When I attempt to log in using username "<username>" and password "<password>"
    Then I should see an error message indicating login failure

    Examples:
      | username    | password      |
      | invalidUser | wrongPassword |
      | admin       | invalidPass   |

  @BL-T004
  Scenario Outline: A user should not be able to log in with empty credentials
    Given I navigate to the Login page
    When I attempt to log in using username "<username>" and password "<password>"
    Then I should see an error message indicating login failure

    Examples:
      | username | password |
      | admin    |          |
      |          | admin    |
      |          |          |
  
  @BL-T005
  Scenario: The session should persist after refreshing the page following a successful login
    Given I navigate to the Login page
    When I login to the page using valid credentials for "administrator"
    Then I should login to home page successfully
    When I refresh the page
    Then I should still be logged in
