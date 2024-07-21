package pl.veranet.tickettoroute.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import pl.veranet.tickettoroute.enams.Currency;

import java.util.Objects;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ResponseTicketEntity {
    private String result;
    private Currency currency;
    private String change;
    private String lackOf;

    public ResponseTicketEntity() {}

    public ResponseTicketEntity(String result, Currency currency, String change, String lackOf) {
        this.result = result;
        this.currency = currency;
        this.change = change;
        this.lackOf = lackOf;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    public String getChange() {
        return change;
    }

    public void setChange(String change) {
        this.change = change;
    }

    public String getLackOf() {
        return lackOf;
    }

    public void setLackOf(String lackOf) {
        this.lackOf = lackOf;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponseTicketEntity that = (ResponseTicketEntity) o;
        return Objects.equals(result, that.result) && currency == that.currency && Objects.equals(change, that.change) && Objects.equals(lackOf, that.lackOf);
    }

    @Override
    public int hashCode() {
        return Objects.hash(result, currency, change, lackOf);
    }

    @Override
    public String toString() {
        return "ResponseTicketEntity{" +
                "result='" + result + '\'' +
                ", currency=" + currency +
                ", change='" + change + '\'' +
                ", lackOf='" + lackOf + '\'' +
                '}';
    }
}
