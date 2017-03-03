package com.pauldailly.springcloudcontract.model

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString


@EqualsAndHashCode
@ToString
class LoanApplication {

    Applicant applicant
    BigDecimal amount
    String id
}
