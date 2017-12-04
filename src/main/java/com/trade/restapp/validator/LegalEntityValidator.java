package com.trade.restapp.validator;

import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.ValidationError.validationError;
import com.trade.restapp.dto.ValidationResult;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.*;
import org.springframework.stereotype.Component;


/**
 * Only one legal entity is used: CS Zurich
 */
@ManagedResource(objectName = "TradeValidators:name=LegalEntityValidator", description = "Legal entity validation")
@Component
public class LegalEntityValidator implements TradeValidator {


    private Set<String> legalEntities = new HashSet<>();

    public LegalEntityValidator() {
        legalEntities.add("CS Zurich");
    }

    @Override
    public ValidationResult validate(Trade trade) {

        ValidationResult validationResult = ValidationResult.validationResult();

        if(!legalEntities.contains(trade.getLegalEntity())) {
            validationResult.withError(validationError().field("legalEntity").message("Legal entity is invalid"));
        }

        return validationResult;
    }

    @ManagedAttribute(description = "Get legal Entities")
    public Set<String> getLegalEntities() {
        return legalEntities;
    }

    public void setLegalEntities(Set<String> legalEntities) {
        this.legalEntities = legalEntities;
    }

    @ManagedOperation(description = "Load valid legalEntities")
    @ManagedOperationParameters(
            @ManagedOperationParameter(name = "value", description = "Comma separated list")
    )
    @Value("${validator.legalEntities}")
    public String loadValidLegalEntities(String value) {
        legalEntities = new HashSet<>(Arrays.asList(value.split(",")));
        return legalEntities.toString();
    }
}
