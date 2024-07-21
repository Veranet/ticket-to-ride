package pl.veranet.tickettoroute.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.util.Objects;

public class TicketCreateDto {
    @NotBlank(message = "From Town must not be null or empty.")
    private String fromTown;
    @NotBlank(message = "To Town must not be null or empty.")
    private String toTown;
    private int travellerId;
    @PositiveOrZero(message = "Price must not be negative or zero.")
    private BigDecimal price;

    public TicketCreateDto() {
    }

    public TicketCreateDto(String fromTown, String toTown, int travellerId, BigDecimal price) {
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.travellerId = travellerId;
        this.price = price;
    }

    public @NotNull String getFromTown() {
        return fromTown;
    }

    public void setFromTown(@NotNull String fromTown) {
        this.fromTown = fromTown;
    }

    public @NotNull String getToTown() {
        return toTown;
    }

    public void setToTown(@NotNull String toTown) {
        this.toTown = toTown;
    }

    public int getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(int travellerId) {
        this.travellerId = travellerId;
    }

    public @PositiveOrZero BigDecimal getPrice() {
        return price;
    }

    public void setPrice(@PositiveOrZero BigDecimal price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TicketCreateDto that = (TicketCreateDto) o;
        return travellerId == that.travellerId && Objects.equals(fromTown, that.fromTown) && Objects.equals(toTown, that.toTown)
                && Objects.equals(price, that.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromTown, toTown, travellerId, price);
    }

    @Override
    public String toString() {
        return "TicketCreateDto{" +
                "fromTown='" + fromTown + '\'' +
                ", toTown='" + toTown + '\'' +
                ", travellerId=" + travellerId +
                ", price=" + price +
                '}';
    }
}
