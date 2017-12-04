package com.trade.restapp.validator;

import com.trade.restapp.validator.service.CurrencyHolidayService;
import com.trade.restapp.dto.Trade;
import com.trade.restapp.dto.ValidationError;
import com.trade.restapp.dto.ValidationResult;
import java.util.Currency;
import java.util.Date;
import java.util.Optional;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CurrencyValidator implements TradeValidator {

    @Autowired
    private CurrencyHolidayService currencyHolidayService;

    public CurrencyValidator(CurrencyHolidayService currencyHolidayService) {
        this.currencyHolidayService = currencyHolidayService;
    }

    public CurrencyValidator() {
    }

    
    
    @Override
    public ValidationResult validate(Trade trade) {

        ValidationResult validationResult = new ValidationResult();

        String ccyPair = trade.getCcyPair();
        if (StringUtils.isBlank(ccyPair)) {
            return validationResult.withError(new ValidationError().field("ccyPair").message("ccyPair is blank"));
        }

        if (StringUtils.length(ccyPair) != 6) { 
            return validationResult.withError(new ValidationError().field("ccyPair").message("ccyPair length should be 6"));
        }

        boolean valueDateIsPresent = true;

        if (trade.getValueDate() == null) {
            valueDateIsPresent = false;
            validationResult.withError(ValidationError.validationError().field("valueDate").message("valueDate is missing"));
        }

        // extract currency pairs
        String currency1Str = ccyPair.substring(0, 3);
        String currency2Str = ccyPair.substring(3);

        try {
            Currency currency1 = Currency.getInstance(currency1Str);
            if (valueDateIsPresent && isDateHolidayCurrency(trade.getValueDate(), currency1)) {
                validationResult.withError(new ValidationError().field("ccyPair").message("valueDate matches to holiday for Currency 1"));
            }
        }catch (IllegalArgumentException e) {
            validationResult.withError(new ValidationError().field("ccyPair").message("Currency 1 is not valid"));
        }
        try {
            Currency currency2 = Currency.getInstance(currency2Str);
            if (valueDateIsPresent && isDateHolidayCurrency(trade.getValueDate(), currency2)) {
                validationResult.withError(new ValidationError().field("ccyPair").message("valueDate matches to holiday for Currency 2"));
            }
        }catch (IllegalArgumentException e) {
            validationResult.withError(new ValidationError().field("ccyPair").message("Currency 2 is not valid"));
        }

        return validationResult;
    }

    boolean isDateHolidayCurrency(Date date, Currency currency) {

        if (currencyHolidayService == null) {
            return false;
        }

        Optional<Set<Date>> dates = currencyHolidayService.receiveHolidays(currency);
        if (!dates.isPresent()) {
            return false;
        }
        return dates.get().contains(date);
    }

}
