Feature: Payment Update
  As a external payment service partner
  I want to update a payment status after processing externally
  So that the order flow can continue

  Scenario: Successfully update an existing payment
    Given the system has a pending payment
    And the external payment service has a valid payment update
    When the external payment service submits the payment update
    Then the payment status should be updated successfully

  Scenario: Fail to update a payment with invalid data
    Given the system has a pending payment
    And the external payment service attempts to update the payment status with invalid data
    When the external payment service submits the payment update
    Then the system should reject the payment update with a bad request error

  Scenario: Fail to update a non-existent payment
    Given the system has a pending payment
    And the external payment service attempts to update a non-existent payment
    When the external payment service submits the payment update
    Then the system should reject the payment update with a not found error
