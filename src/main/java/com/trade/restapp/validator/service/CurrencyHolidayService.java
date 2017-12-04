package com.trade.restapp.validator.service;

import java.util.Currency;
import java.util.Date;
import java.util.Optional;
import java.util.Set;


public interface CurrencyHolidayService {

    Optional<Set<Date>> receiveHolidays(Currency currency);

}
