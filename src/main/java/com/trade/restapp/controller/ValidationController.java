package com.trade.restapp.controller;


import com.trade.restapp.dto.Trade;
import com.trade.restapp.dto.TradeValidationResult;
import com.trade.restapp.validator.ValidationCore;
import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controller which handle API validation requests
 */
@RestController
@RequestMapping("/api")
public class ValidationController {

    @Autowired
    private ValidationCore validationCore;

    @RequestMapping(value = "validate", method = RequestMethod.POST)
    public TradeValidationResult validate(@RequestBody Trade trade) {
        return validationCore.validate(trade);
    }

    @RequestMapping(value = "validateBulk", method = RequestMethod.POST)
    public Collection<TradeValidationResult> validateBulk(@RequestBody Collection<Trade> trades) {
        return validationCore.bulkValidate(trades);
    }


}
