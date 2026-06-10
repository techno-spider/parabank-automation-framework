@api @regression
Feature: ParaBank Account API
  As a ParaBank customer
  I want to fetch my account details via API
  So that I can verify my account information programmatically

  @api
  Scenario: Fetch account details with valid authentication
    Given I am authenticated via API
    When I fetch my account details via API
    Then the API response status should be 200
    And the response should contain account information