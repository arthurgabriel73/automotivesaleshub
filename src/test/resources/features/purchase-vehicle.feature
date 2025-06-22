Feature: Purchase Vehicle
  As a sales representative
  I want to request a vehicle purchase
  So that I can process the sale for a customer

  Scenario: Successfully request a vehicle purchase
    Given the system has a valid vehicle available for purchase
    And the sales representative has a valid vehicle purchase request form
    When the sales representative requests the purchase of the vehicle
    Then the vehicle purchase should be accepted

  Scenario: Fail to request a not available vehicle
    Given the system has a vehicle that has already been sold
    When the sales representative attempts to request the purchase of the sold vehicle
    Then the system should reject the request with a not acceptable error


  Scenario: Fail to request a vehicle purchase with missing required fields
    Given the system has a valid vehicle available for purchase
    And the sales representative has an invalid vehicle purchase request form missing required fields
    When the sales representative submits the vehicle purchase request form
    Then the system should reject the request with a bad request error
