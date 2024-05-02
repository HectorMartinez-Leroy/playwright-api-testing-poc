Feature: Get users

  Scenario: Get a list of users
    Given reqres API https://reqres.in/api/users
    When I do request
    Then I receive list of users
    And response request code is 200