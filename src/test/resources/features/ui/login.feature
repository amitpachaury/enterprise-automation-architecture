Feature: Login Functionality
  As a user
  I want to login to SauceDemo
  So that I can access the products

  Background:
    Given user is on the login page

  @smoke
  Scenario: Valid login
    When user enters a valid username
    And user enters a valid password
    And user clicks on Login button
    Then dashboard page is displayed

  @regression
  Scenario: Locked user
    When user enters a valid username for locked user
    And user enters a valid password
    And user clicks on Login button
    Then error message is displayed

  @regression
  Scenario: Empty Username
    When user enters a empty username
    And user enters a valid password
    And user clicks on Login button
    Then error message contains "Username is required"

  @regression
  Scenario Outline: Multiple invalid credentials
    When user enters a invalid '<username>'
    And user enters a invalid password '<password>'
    And user clicks on Login button
    Then error message should contain '<expectedError>'

    Examples:
      | username      | password      | expectedError                      |
      | invalid_user  | secret_sauce  | Username and password do not match |
      | standard_user | wrong_pass    | Username and password do not match |
      |               | secret_sauce  | Username is required               |