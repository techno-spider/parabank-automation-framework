@ui @failure-test
Feature: Verify Screenshot on Failure

  Scenario: Intentionally fail to verify screenshot capture
    Given the ParaBank application is running for screenshot test
    Then I force a test failure to capture screenshot
