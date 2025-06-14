Feature: Vehicle Registration
  As a system administrator
  I want to register vehicles in the system
  So that they can be displayed to potential buyers

  Scenario: Successfully register a new vehicle
    Given the system has a valid vehicle registration form
    When the admin submits the vehicle registration form
    Then the vehicle should be registered successfully

  Scenario: Fail to register a vehicle with missing required fields
    Given the system has an invalid vehicle registration form missing required fields
    When the admin submits the vehicle registration form
    Then the system should reject the registration with a bad request error
