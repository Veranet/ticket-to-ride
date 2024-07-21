package pl.veranet.tickettoroute.dto;

import pl.veranet.tickettoroute.enams.Currency;

import java.math.BigDecimal;
import java.util.Objects;

public class ResponsePriceEntity {
    private int segments;
    private BigDecimal price;
    private Currency currency;

    public ResponsePriceEntity () {}

    public ResponsePriceEntity(int segments, BigDecimal price, Currency currency) {
        this.segments = segments;
        this.price = price;
        this.currency = currency;
    }

    public int getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Currency getCurrency() {
        return currency;
    }

    public void setCurrency(Currency currency) {
        this.currency = currency;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResponsePriceEntity that = (ResponsePriceEntity) o;
        return segments == that.segments && Objects.equals(price, that.price) && currency == that.currency;
    }

    @Override
    public int hashCode() {
        return Objects.hash(segments, price, currency);
    }

    @Override
    public String toString() {
        return "ResponsePriceEntity{" +
                "segments=" + segments +
                ", price=" + price +
                ", currency=" + currency +
                '}';
    }
}
