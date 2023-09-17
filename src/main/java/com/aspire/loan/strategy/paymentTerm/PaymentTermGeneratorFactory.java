package com.aspire.loan.strategy.paymentTerm;

import com.aspire.loan.entities.enums.PaymentTermType;
import com.aspire.loan.exceptions.NotSupportedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class PaymentTermGeneratorFactory {
    private final Map<PaymentTermType, PaymentTermGeneratorStrategy> paymentTermGeneratorMap;

    @Autowired
    public PaymentTermGeneratorFactory(List<PaymentTermGeneratorStrategy> paymentTermGenerators) {
        paymentTermGeneratorMap = new HashMap<>();
        for (PaymentTermGeneratorStrategy generator : paymentTermGenerators) {
            paymentTermGeneratorMap.put(generator.getPaymentTermType(), generator);
        }
    }

    public PaymentTermGeneratorStrategy getPaymentTermGenerator(PaymentTermType termType) {
        if (paymentTermGeneratorMap.containsKey(termType)) {
            return paymentTermGeneratorMap.get(termType);
        }
        throw new NotSupportedException("Not supporting as of now " + termType.name() + " in payment term frequency");
    }
}

