package com.pauldailly.springcloudcontract

import com.pauldailly.springcloudcontract.model.Applicant
import com.pauldailly.springcloudcontract.model.LoadApplicationStatus
import com.pauldailly.springcloudcontract.model.LoanApplication
import com.pauldailly.springcloudcontract.model.LoanApplicationResponse
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.web.client.RestTemplate

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.*

@RunWith(SpringRunner)
@SpringBootTest(webEnvironment = NONE)
@AutoConfigureStubRunner(ids = ["com.pauldailly.springcloudcontract:spring-contract-http-server:+:stubs:6565"], workOffline = true)
@DirtiesContext
class LoanApplicationServiceTest {

    LoanApplicationService service

    @Before
    void setup() {
        service = new LoanApplicationService(new RestTemplate(), "http://localhost", 6565)
    }

    @Test
    void shouldBeRejectedDueToHighLoanAmount() {
        LoanApplication application = new LoanApplication(amount: new BigDecimal(99999), applicant: new Applicant(id: 1234567890))

        LoanApplicationResponse response = service.submitLoanApplication(application)

        assert response.status == LoadApplicationStatus.REJECTED
        assert response.reason == 'Amount too high'

    }

}