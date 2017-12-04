package com.trade.restapp.dto;

import com.fasterxml.jackson.annotation.JsonGetter;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Model object for storing trade validation results, holds trade and collection of invalid fields with messages
 */
public class TradeValidationResult {

    private Trade trade;
    private Map<String, Collection<String>> errorFields = new ConcurrentHashMap<>();

    public static TradeValidationResult tradeValidationResult() {
        return  new TradeValidationResult();
    }

    public TradeValidationResult() {}

    public TradeValidationResult addInvalidField(String field, String message) {

        if (!errorFields.containsKey(field)) {
            errorFields.put(field, ConcurrentHashMap.newKeySet());
        }
        errorFields.get(field).add(message);
        return this;
    }


    public Trade getTrade() {
        return trade;
    }

    public void setTrade(Trade trade) {
        this.trade = trade;
    }

    public Map<String, Collection<String>> getInvalidFields() {
        return errorFields;
    }

    public void setErrorFields(Map<String, Collection<String>> errorFields) {
        this.errorFields = errorFields;
    }

    @JsonGetter("haveErrors")
    public boolean haveErrors() {
        return !errorFields.isEmpty();
    }

    public TradeValidationResult trade(Trade trade) {
        this.trade = trade;
        return this;
    }

    public Map<String, Collection<String>> errorFields() {
        return errorFields;
    }
}
