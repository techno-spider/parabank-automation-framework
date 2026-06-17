@e2e @regression @ui
Feature: Fund Transfer
  As a ParaBank customer
  I want to transfer money between my accounts
  So that I can manage my funds

  @e2e
  Scenario: Transfer funds between accounts
    Given I am logged in to ParaBank
    When I note the balance of my first account
    And I transfer "10" to another account
    Then the transfer should be successful
    And my account balance should be reduced by "10"