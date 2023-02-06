package com.dxc.spring.soap.api.service;

import com.dxc.spring.soap.api.loaneligibility.Acknowledgement;
import com.dxc.spring.soap.api.loaneligibility.CustomerRequest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoanEligibilityServiceTest {

    private LoanEligibilityService loanEligibilityService;

    @BeforeEach
    void setUp() {
        this.loanEligibilityService = new LoanEligibilityService();
    }

    @Test
    void checkLoanEligibilityComSucesso() {
        CustomerRequest request = getCustomerRequest();

        LoanEligibilityService service = new LoanEligibilityService();

        Acknowledgement acknowledgement = service.checkLoanEligibility(request);

        Assertions.assertThat(acknowledgement.isIsEligible()).isTrue();
        Assertions.assertThat(acknowledgement.getApprovedAmount()).isEqualTo(request.getYearlyIncome());
        Assertions.assertThat(acknowledgement.getCriteriaMismatch().size()).isEqualTo(0);
    }

    @Test
    void checkLoanEligibilityQuandoNaoTemIdadeAceitavel() {
        CustomerRequest request = getCustomerRequest();
        request.setAge(20);

        LoanEligibilityService service = new LoanEligibilityService();

        Acknowledgement acknowledgement = service.checkLoanEligibility(request);

        Assertions.assertThat(acknowledgement.isIsEligible()).isFalse();
        Assertions.assertThat(acknowledgement.getApprovedAmount()).isEqualTo(0L);
        Assertions.assertThat(acknowledgement.getCriteriaMismatch().get(0))
                .isEqualTo("Person age should  in between 30 to 60");
    }

    @Test
    void checkLoanEligibilityQuandoNaoTemRendaAceitavel() {
        CustomerRequest request = getCustomerRequest();
        request.setYearlyIncome(400);

        LoanEligibilityService service = new LoanEligibilityService();

        Acknowledgement acknowledgement = service.checkLoanEligibility(request);

        Assertions.assertThat(acknowledgement.isIsEligible()).isFalse();
        Assertions.assertThat(acknowledgement.getApprovedAmount()).isEqualTo(0L);
        Assertions.assertThat(acknowledgement.getCriteriaMismatch().get(0))
                .isEqualTo("minimun income should be more da 200000");
    }

    @Test
    void checkLoanEligibilityQuandoNaoTemContaAceitavel() {
        CustomerRequest request = getCustomerRequest();
        request.setCibilScore(400);

        LoanEligibilityService service = new LoanEligibilityService();

        Acknowledgement acknowledgement = service.checkLoanEligibility(request);

        Assertions.assertThat(acknowledgement.isIsEligible()).isFalse();
        Assertions.assertThat(acknowledgement.getApprovedAmount()).isEqualTo(0L);
        Assertions.assertThat(acknowledgement.getCriteriaMismatch().get(0))
                .isEqualTo("Low CIBIL Score please try after 6 month");
    }

    @Test
    void checkLoanEligibilityQuandoNaoTemTodosCriterios() {
        CustomerRequest request = getCustomerRequest();
        request.setAge(20);
        request.setCibilScore(400);
        request.setYearlyIncome(40000);

        LoanEligibilityService service = new LoanEligibilityService();

        Acknowledgement acknowledgement = service.checkLoanEligibility(request);

        Assertions.assertThat(acknowledgement.isIsEligible()).isFalse();
        Assertions.assertThat(acknowledgement.getApprovedAmount()).isEqualTo(0L);
        Assertions.assertThat(acknowledgement.getCriteriaMismatch().size()).isEqualTo(3);
    }

    private CustomerRequest getCustomerRequest() {
        CustomerRequest request = new CustomerRequest();

        request.setCustomerName("Quitumba");
        request.setAge(30);
        request.setYearlyIncome(500000);
        request.setCibilScore(700);
        request.setEmploymentMode("GOVT");

        return request;
    }
}