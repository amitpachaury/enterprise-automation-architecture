Feature: Hybrid UI and API Tests
  As a QA Architect
  I want to combine API and UI testing
  So that I can validate end to end user journeys

  @hybrid @smoke
  Scenario: Verify UI login after API setup
    Given a new user is created via API
    When I login to the application with valid credentials
    Then the dashboard should be displayed successfully

  @hybrid @regression
  Scenario: Verify API user data after UI interaction
    Given a new user is created via API
    When I login to the application with valid credentials
    And I add a product to cart via UI
    Then cart count should show 1 via UI
    And user data should be retrievable via API