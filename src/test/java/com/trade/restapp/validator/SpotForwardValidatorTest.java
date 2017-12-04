package com.trade.restapp.validator;


import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.Trade.newTrade;
import com.trade.restapp.dto.ValidationError;
import com.trade.restapp.dto.ValidationResult;
import java.util.Date;
import java.util.stream.Stream;
import org.apache.commons.lang3.time.DateUtils;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;

public class SpotForwardValidatorTest {

    private Trade trade;
    private SpotForwardValidator spotForwardValidator = new SpotForwardValidator();

    @Before
    public void before() {
        trade = newTrade();
    }


    @Test
    public void test_Spot_postive_path() {
        Date validSpotDate = DateUtils.addDays(spotForwardValidator.getTodayDate(), 2);
        trade.setValueDate(validSpotDate);
        trade.setType("Spot");

        ValidationResult result = spotForwardValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.errors(), is(empty()));
    }

    @Test
    public void test_Spot_invalid_value_date_path() {
        Date validSpotDate = DateUtils.addDays(spotForwardValidator.getTodayDate(), 1);
        trade.setValueDate(validSpotDate);
        trade.setType("Spot");

        ValidationResult result = spotForwardValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.errors().size(), is(1));

        ValidationError firstError = result.errors().stream().findFirst().get();
        assertThat(firstError.field(), is("valueDate"));
        assertThat(firstError.message(), is("On spot trades valueDate should be +2 days from today date"));
    }

    @Test
    public void test_Forward_postivie_path() {

        Date validForwardDate = DateUtils.addDays(spotForwardValidator.getTodayDate(), 5);
        trade.setValueDate(validForwardDate);
        trade.setType("Forward");


        ValidationResult result = spotForwardValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.errors(), is(empty()));

    }

    @Test
    public void test_Forward_invalid_value_date_path() {

        Date validForwardDate = DateUtils.addDays(spotForwardValidator.getTodayDate(), 2);
        trade.setValueDate(validForwardDate);
        trade.setType("Forward");


        ValidationResult result = spotForwardValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.errors().size(), is(1));

        ValidationError firstError = result.errors().stream().findFirst().get();
        assertThat(firstError.field(), is("valueDate"));
        assertThat(firstError.message(), is("On forward trades valueDate should be more than 2 days"));

    }

    @Test
    public  void test_SpotForward_missing_value_date() {
        Stream.of("Spot", "Forward").forEach( type -> {
            trade.setValueDate(null);
            trade.setType(type);

            ValidationResult result = spotForwardValidator.validate(trade);

            assertThat(result, is(not(nullValue())));
            assertThat(result.hasErrors(), is(true));
            assertThat(result.errors().size(), is(1));

            ValidationError firstError = result.errors().stream().findFirst().get();
            assertThat(firstError.field(), is("valueDate"));
            assertThat(firstError.message(), is("valueDate is missing"));
        });


    }


}
