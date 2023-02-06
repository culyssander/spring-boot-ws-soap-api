package com.dxc.spring.soap.api.endpoint;

import com.dxc.spring.soap.api.loaneligibility.Acknowledgement;
import com.dxc.spring.soap.api.loaneligibility.CustomerRequest;
import com.dxc.spring.soap.api.service.LoanEligibilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

@Endpoint
public class LoanEligibilityindicatorEndpoint {

    private static final String NAMESPACE = "http://www.dxc.com/spring/soap/api/loaneligibility";

    @Autowired
    private LoanEligibilityService loanEligibilityService;

    @PayloadRoot(namespace = NAMESPACE, localPart = "CustomerRequest")
    @ResponsePayload
    public Acknowledgement getLoanStatus(@RequestPayload CustomerRequest request) {
        return loanEligibilityService.checkLoanEligibility(request);
    }
}
