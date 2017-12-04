package com.trade.restapp.validator;



import com.trade.restapp.dto.Trade;
import static com.trade.restapp.dto.Trade.newTrade;
import com.trade.restapp.dto.ValidationError;
import com.trade.restapp.dto.ValidationResult;
import java.text.ParseException;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.empty;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.Matchers.nullValue;
import org.junit.Before;
import org.junit.Test;

public class CustomerValidatorTest {

    private Trade trade;
    private CustomerValidator customerValidator = new CustomerValidator();

    @Before
    public void before() {
        trade = newTrade();
    }

    @Test
    public void test_CustomerValidator_Valid_Path() throws ParseException {
        trade.setCustomer("PLUTO1");

        ValidationResult result = customerValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.errors(), is(empty()));
    }

    @Test
    public void test_CustomerValidator_Wrong_customer() throws ParseException {
        trade.setCustomer("TOMATO");

        ValidationResult result = customerValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.errors().size(), is(1));

        ValidationError firstError = result.errors().stream().findFirst().get();
        assertThat(firstError.field(), is("customer"));
        assertThat(firstError.message(), is("Customer is not in approved list"));
    }

    @Test
    public void test_CustomerValidator_Customer_missing() throws ParseException {

        ValidationResult result = customerValidator.validate(trade);

        assertThat(result, is(not(nullValue())));
        assertThat(result.hasErrors(), is(true));
        assertThat(result.errors().size(), is(1));

        ValidationError firstError = result.errors().stream().findFirst().get();
        assertThat(firstError.field(), is("customer"));
        assertThat(firstError.message(), is("Customer blank"));
    }


}
