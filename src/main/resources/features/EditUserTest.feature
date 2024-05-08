Feature: Edit user

  Scenario: Edit an user
    Given a request to https://reqres.in/api/users/2
    And a request header Content-Type with value application/json
    And a request body from file src/main/resources/data/json/body/userBody.json
    When I do PUT request
    Then response code is 200