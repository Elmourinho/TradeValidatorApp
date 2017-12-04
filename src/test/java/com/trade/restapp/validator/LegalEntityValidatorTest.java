package com.trade.restapp.validator;

import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.Trade.newTrade;
import com.trade.restapp.dto.ValidationError;
import com.trade.restapp.dto.ValidationResult;
import java.text.ParseException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import org.junit.Before;
import org.junit.Test;

public class LegalEntityValidatorTest {

    private Trade trade;
    private LegalEntityValidator legalEntityValidator = new LegalEntityValidator();

    @Before
    public void before() {
        trade = newTrade();
    }

    @Test
    public void test_LegalEntityValidator_Valid_Path() throws ParseException {
        trade.setLegalEntity("CS Zurich");

        ValidationResult result = legalEntityValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.errors(), is(empty()));
    }

    @Test
    public void test_LegalEntityValidator_Wrong_customer() throws ParseException {
        trade.setCustomer("Tomato");

        ValidationResult result = legalEntityValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.errors().size(), is(1));

        ValidationError firstError = result.errors().stream().findFirst().get();
        assertThat(firstError.field(), is("legalEntity"));
        assertThat(firstError.message(), is("Legal entity is invalid"));
    }

}
