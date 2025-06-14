Feature: Vehicle Update
  As a system administrator
  I want to update vehicle details in the system
  So that the information remains current and accurate

  Scenario: Successfully update an existing vehicle
    Given the admin have registered a vehicle in the system
    And the admin has a valid vehicle update form
    When the admin submits the vehicle update form
    Then the vehicle should be updated successfully

  Scenario: Fail to update a vehicle with invalid data
    Given the admin have registered a vehicle in the system
    And the admin has an invalid vehicle update form with incorrect data
    When the admin submits the vehicle update form
    Then the system should reject the vehicle update with a bad request error

  Scenario: Fail to update a non-existent vehicle
    Given the admin has not registered a vehicle in the system
    When the admin submits the vehicle update form
    Then the system should reject the vehicle update with a not found error
