package pl.veranet.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.veranet.enams.Currency;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTicketEntity {
    private String result;
    private Currency currency;
    private String change;
    private String lackOf;

    public ResponseTicketEntity(String result, Currency currency, String change, String lackOf) {
        this.result = result;
        this.currency = currency;
        this.change = change;
        this.lackOf = lackOf;
    }
}
