Feature: User API
  As an API consumer
  I want to manage users via ReqRes API
  So that I can validate CRUD operations

  Background:
    Given API is available

  @api @smoke
  Scenario: Get single user
    When I send GET request to get user with id "2"
    Then response status code should be 200
    And response should contain user with email "janet.weaver@reqres.in"

  @api @regression
  Scenario: Get all users
    When I send GET request to get all users on page "1"
    Then response status code should be 200
    And response should contain 6 users

  @api @regression
  Scenario: Create user with valid data
    When I send POST request to create a user with valid data
    Then response status code should be 201
    And response should contain a valid user id
    And response should contain a created timestamp

  @api @regression
  Scenario: Update user with valid data
    When I send PUT request to update user "2" with valid data
    Then response status code should be 200
    And response should contain an updated timestamp

  @api @regression
  Scenario: Delete user
    When I send DELETE request to delete user "2"
    Then response status code should be 204

  @api @regression
  Scenario: Get non-existent user
    When I send GET request to get user with id "99"
    Then response status code should be 404