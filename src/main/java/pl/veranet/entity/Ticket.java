package pl.veranet.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Ticket {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private Integer travellerId;
    private String fromTown;
    private String toTown;
    private BigDecimal price;
    private Instant createdDate;
    private Instant expiredDate;

    public Ticket() {
    }

    public Ticket(Integer id, Integer travellerId, String fromTown, String toTown, BigDecimal price, Instant createdDate, Instant expiredDate) {
        this.id = id;
        this.travellerId = travellerId;
        this.price = price;
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(Integer travellerId) {
        this.travellerId = travellerId;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Instant expiredDate) {
        this.expiredDate = expiredDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id) && Objects.equals(travellerId, ticket.travellerId) && Objects.equals(fromTown, ticket.fromTown) && Objects.equals(toTown, ticket.toTown) && Objects.equals(price, ticket.price) && Objects.equals(createdDate, ticket.createdDate) && Objects.equals(expiredDate, ticket.expiredDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, travellerId, fromTown, toTown, price, createdDate, expiredDate);
    }

    @Override
    public String toString() {
        return "Ticket{" +
                "id=" + id +
                ", travellerId=" + travellerId +
                ", fromTown='" + fromTown + '\'' +
                ", toTown='" + toTown + '\'' +
                ", price=" + price +
                ", createdDate=" + createdDate +
                ", expiredDate=" + expiredDate +
                '}';
    }
}
