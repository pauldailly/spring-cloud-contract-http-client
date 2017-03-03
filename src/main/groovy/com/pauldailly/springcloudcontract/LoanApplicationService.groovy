package com.pauldailly.springcloudcontract

import com.pauldailly.springcloudcontract.model.*
import org.springframework.web.client.RestTemplate

class LoanApplicationService {

    private static FRAUD_STATUS_MAP = [
            (FraudCheckStatus.FRAUDULENT) : LoadApplicationStatus.REJECTED,
            (FraudCheckStatus.NON_FRAUDULENT) : LoadApplicationStatus.ACCEPTED,
    ]

    RestTemplate restTemplate
    String fraudServiceBaseUrl
    int fraudServicePort

    LoanApplicationService(RestTemplate restTemplate, String fraudServiceBaseUrl, int fraudServicePort) {
        this.restTemplate = restTemplate
        this.fraudServiceBaseUrl = fraudServiceBaseUrl
        this.fraudServicePort = fraudServicePort
    }

    LoanApplicationResponse submitLoanApplication(LoanApplication application){

        FraudServiceRequest request = new FraudServiceRequest(clientId: application.applicant.id, loanAmount: application.amount)
        FraudServiceResponse response = restTemplate.postForObject("${fraudServiceBaseUrl}:${fraudServicePort}/fraudchecks", request, FraudServiceResponse)
        new LoanApplicationResponse(status: FRAUD_STATUS_MAP[response.status], reason: response.reason)

    }
}
