@ui
Feature: ParaBank Login Functionality

  As a ParaBank customer
  I want to log in to my account
  So that I can access my banking services

  Background:
    Given the ParaBank application is running

  @smoke
  Scenario: Successful login with valid credentials
    When I enter valid credentials
    And I click the login button
    Then I should be redirected to the account overview page

  @regression
  Scenario: Login with invalid credentials shows error
    When I enter invalid credentials
    And I click the login button
    Then I should see an error message "The username and password could not be verified."
