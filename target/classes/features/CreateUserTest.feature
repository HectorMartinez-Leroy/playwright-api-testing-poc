Feature: Create users

  Scenario: Create an user
    Given a request to https://reqres.in/api/users
    And a request header Content-Type with value application/json
    And a request body from file src/main/resources/data/json/body/userBody.json
    When I do POST request
    Then response code is 201