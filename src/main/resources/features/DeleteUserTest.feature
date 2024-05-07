Feature: Delete users

  Scenario: Delete a specific user
    Given a request to https://reqres.in/api/users/999
    When I do DELETE request
    Then response code is 204