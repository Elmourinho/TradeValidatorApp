package com.trade.restapp.validator;


import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.ValidationError.validationError;
import com.trade.restapp.dto.ValidationResult;
import static com.trade.restapp.dto.ValidationResult.validationResult;
import java.time.DayOfWeek;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 *  Validator which checks business rule: value date cannot fall on weekend
 */
@Component
@ManagedResource(objectName = "TradeValidators:name=ValueDateWeekendValidator", description = "Tunes on value date weekend validator")
public class ValueDateWeekendValidator implements TradeValidator{

    private Set<DayOfWeek> weekendDays = new HashSet<>();

    public ValueDateWeekendValidator() {
        weekendDays.add(DayOfWeek.SATURDAY);
        weekendDays.add(DayOfWeek.SUNDAY);
    }

    @Override
    public ValidationResult validate(Trade trade) {

        ValidationResult validationResult = validationResult();

        if (trade.getValueDate() == null) {
            validationResult.withError(
                    validationError().field("valueDate").message("valueDate is missing")
            );
            return validationResult;
        }

        boolean isWeekendDay = weekendDays.contains(
                trade.getValueDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate().getDayOfWeek()  //extract day of week from date
        );

        if (isWeekendDay) {
            validationResult.withError(
                    validationError().field("valueDate").message("valueDate is holiday date")
            );
        }


        return validationResult;
    }

    @ManagedAttribute(description = "Get weekend days")
    public String getWeekendDaysStr() {
        return StringUtils.join(",", weekendDays);
    }

    @ManagedAttribute(description = "Set weekend days, comma separated values: MONDAY,TUESDAY,WEDNESDAY,THURSDAY,FRIDAY,SATURDAY,SUNDAY")
    public void setWeekendDaysStr(String weekendDaysStr) {
        weekendDays.clear();
        for(String rawDay : weekendDaysStr.split(",")) {
            weekendDays.add(DayOfWeek.valueOf(rawDay));
        }
    }

}
