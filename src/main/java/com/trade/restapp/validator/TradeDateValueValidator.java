package com.trade.restapp.validator;


import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.ValidationError.validationError;
import com.trade.restapp.dto.ValidationResult;
import static com.trade.restapp.dto.ValidationResult.validationResult;
import org.springframework.stereotype.Component;

/**
 * Validator which checks business rule:"value date cannot be before trade date"
 */
@Component
public class TradeDateValueValidator implements TradeValidator{


    @Override
    public ValidationResult validate(Trade trade) {

        ValidationResult validationResult = validationResult();

        if (trade.getValueDate() == null) {
            validationResult.withError(
                    validationError().field("valueDate").message("valueDate is missing")
            );
        }

        if (trade.getTradeDate() == null) {
            validationResult.withError(
                    validationError().field("tradeDate").message("tradeDate is missing")
            );
        }

        if (!validationResult.hasErrors()) {
            if (trade.getValueDate().before(trade.getTradeDate())) {
                validationResult.withError(
                        validationError().field("valueDate").message("Value date cannot be before Trade date")
                );
            }
        }

        return validationResult;
    }
}
