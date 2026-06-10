@wiremock @regression
Feature: WireMock Service Virtualization

  As a test engineer
  I want to stub external services
  So that I can test microservices in isolation

  @wiremock
  Scenario: Verify KYC service stub returns expected response
    Given the external KYC service is available
    When I call the KYC verification service
    Then the KYC response status should be 200
    And the KYC response should contain "VERIFIED"