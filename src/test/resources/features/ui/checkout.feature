Feature: Checkout Functionality
  As a logged in user
  I want to purchase products
  So that I can complete my order

  Background:
    Given user is logged in

  @smoke
  Scenario: Complete Purchase Flow
    When user adds first product to cart
    And user goes to cart
    And user proceeds to checkout
    And user fills shipping info with random data
    And user places the order
    Then order confirmation is displayed

  @regression
  Scenario: Checkout with missing first name
    When user adds first product to cart
    And user goes to cart
    And user proceeds to checkout
    And user submits shipping info with empty firstname
    Then error message is displayed on checkout page

  @regression
  Scenario: Cart item count after adding product
    When user adds first product to cart
    Then cart count should be 1

  @regression
  Scenario Outline: Checkout form validation
    When user adds first product to cart
    And user goes to cart
    And user proceeds to checkout
    And user fills shipping info with "<firstName>" "<lastName>" and "<postalCode>"
    Then checkout error message should contain "<expectedError>"
    
    Examples:
      | firstName | lastName | postalCode | expectedError |
      |           | Pachaury | 400001     | First Name is required  |
      | Ameet     |          | 400001     | Last Name is required   |
      | Ameet     | Pachaury |            | Postal Code is required |

