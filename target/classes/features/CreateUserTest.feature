Feature: Create users

  Scenario: Create an user using POJO
    Given a request to https://reqres.in/api/users
    When I do POST request
    Then response code is 201