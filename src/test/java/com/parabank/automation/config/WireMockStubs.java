package com.parabank.automation.config;

import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import org.springframework.stereotype.Component;

@Component
public class WireMockStubs {

    private final WireMockServer wireMockServer;

    public WireMockStubs(WireMockServer wireMockServer) {
        this.wireMockServer = wireMockServer;
        WireMock.configureFor("localhost", wireMockServer.port());
    }

    /*
     * Stub an external KYC (Know Your Customer) service.
     * Simulates a successful customer verification response.
     */
    public void stubKycVerificationSuccess(String customerId) {
        WireMock.stubFor(WireMock.get(WireMock.urlPathEqualTo("/kyc/verify/" + customerId))
                                 .willReturn(WireMock.aResponse()
                                                     .withStatus(200)
                                                     .withHeader("Content-Type", "application/json")
                                                     .withBody("""
                                                                       {
                                                                           "customerId": "%s",
                                                                           "status": "VERIFIED",
                                                                           "riskLevel": "LOW"
                                                                       }
                                                                       """.formatted(customerId))));
    }

    /**
     * Stub an external notification service.
     * Simulates a successful email notification.
     */
    public void stubNotificationSuccess() {
        WireMock.stubFor(WireMock.post(WireMock.urlPathEqualTo("/notification/email"))
                                 .willReturn(WireMock.aResponse()
                                                     .withStatus(200)
                                                     .withHeader("Content-Type", "application/json")
                                                     .withBody("""
                                                                       {
                                                                           "status": "SENT",
                                                                           "message": "Notification sent successfully"
                                                                       }
                                                                       """)));
    }

    /**
     * Stub an external fraud detection service.
     * Simulates a transaction approved response.
     */
    public void stubFraudCheckApproved() {
        WireMock.stubFor(WireMock.post(WireMock.urlPathEqualTo("/fraud/check"))
                                 .willReturn(WireMock.aResponse()
                                                     .withStatus(200)
                                                     .withHeader("Content-Type", "application/json")
                                                     .withBody("""
                                                                       {
                                                                           "transactionId": "TXN123456",
                                                                           "status": "APPROVED",
                                                                           "riskScore": 15
                                                                       }
                                                                       """)));
    }
}
