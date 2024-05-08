Feature: Get users

  Scenario: Get a list of users
    Given a request to https://reqres.in/api/users
    When I do GET request
    Then response code is 200
    And I receive list of users

  Scenario: Get a list of users with filter by fields
    Given a request to https://reqres.in/api/users
    And a request parameter gender with value male
    And a request parameter status with value active
    When I do GET request
    Then response code is 200
    And I receive list of users
