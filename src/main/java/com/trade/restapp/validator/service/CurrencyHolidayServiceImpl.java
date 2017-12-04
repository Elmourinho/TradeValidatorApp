/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.trade.restapp.validator.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Currency;
import java.util.Date;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import org.springframework.context.annotation.Bean;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.stereotype.Service;

/**
 *
 * @author mahir.eynullayev
 */
@Service
public class CurrencyHolidayServiceImpl implements CurrencyHolidayService {

    @Override
    public Optional<Set<Date>> receiveHolidays(Currency currency) {
        // service which return set of holidays for currencies, good point to extend and add querying of remote holidays rules repository

        SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd");
        Currency USD = Currency.getInstance("USD");

        if (USD.equals(currency)) {
            try {
                return Optional.of(new HashSet<>(
                        Arrays.asList(
                                DATE_FORMATTER.parse("2017-01-01"),
                                DATE_FORMATTER.parse("2017-01-02"),
                                DATE_FORMATTER.parse("2017-02-20")
                        )));
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        return Optional.empty();

    }
    
    

}
