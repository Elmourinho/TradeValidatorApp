package com.trade.restapp.validator;


import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.ValidationError.validationError;
import com.trade.restapp.dto.ValidationResult;
import static com.trade.restapp.dto.ValidationResult.validationResult;

import java.util.*;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;


@Component
@ManagedResource(objectName = "TradeValidators:name=CustomerValidator", description = "Legal entity validation")
@PropertySource(value={"classpath:validator.properties"})
public class CustomerValidator implements TradeValidator {


    private List<String> validCustomers = new ArrayList<>();

    public CustomerValidator() {
        validCustomers.add("PLUTO1");
        validCustomers.add("PLUTO2");
    }

    @Override
    public ValidationResult validate(Trade trade) {

        ValidationResult validationResult = validationResult();

        if (StringUtils.isBlank(trade.getCustomer())) {
            validationResult.withError(validationError().field("customer").message("Customer blank"));
            return validationResult;
        }

        if (!validCustomers.contains(trade.getCustomer())) {
            validationResult.withError(validationError().field("customer").message("Customer is not in approved list"));
        }

        return validationResult;
    }

    @ManagedAttribute(description = "List valid customers")
    public List<String> getValidCustomers() {
        return validCustomers;
    }

    @ManagedOperation(description = "Load valid customers")
    @ManagedOperationParameters(
        @ManagedOperationParameter(name = "customerList", description = "Comma separated customer list")
    )
    @Value("${validator.customer.validCustomers}")
    public String validCustomersFromString(String customerList) {
        validCustomers = new ArrayList<>(Arrays.asList(customerList.split(",")));
        return validCustomers.toString();
    }

    public void setValidCustomers(List<String> validCustomers) {
        this.validCustomers = validCustomers;
    }
}
