package com.trade.restapp.validator;

import com.trade.restapp.dto.Trade;
import com.trade.restapp.dto.ValidationResult;


/**
 * Interface which should be implemented by trade validator to be picked in validation core
 */
public interface TradeValidator {

    /**
     * Run trade validation checks and return results
     * @param trade Trade to validate
     * @return Validation errors is something found empty object otherwise
     */
    ValidationResult validate(Trade trade);
}
