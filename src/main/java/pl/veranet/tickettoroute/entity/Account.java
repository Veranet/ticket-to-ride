package pl.veranet.tickettoroute.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.PositiveOrZero;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;

@Entity
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private int travellerId;
    private Instant suspendedDate;
    @PositiveOrZero(message = "Balance must be positive")
    private BigDecimal balance;

    public Account() {}

    public Account(Integer id, int travellerId, BigDecimal balance) {
        this.id = id;
        this.travellerId = travellerId;
        this.balance = balance;
    }

    public Account(Integer id, int travellerId, Instant suspendedDate, BigDecimal balance) {
        this.id = id;
        this.travellerId = travellerId;
        this.suspendedDate = suspendedDate;
        this.balance = balance;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Instant getSuspendedDate() {
        return suspendedDate;
    }

    public void setSuspendedDate(Instant suspendedDate) {
        this.suspendedDate = suspendedDate;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Account account = (Account) o;
        return travellerId == account.travellerId && Objects.equals(id, account.id) && Objects.equals(suspendedDate, account.suspendedDate) && Objects.equals(balance, account.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, travellerId, suspendedDate, balance);
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getTravellerId() {
        return travellerId;
    }

    public void setTravellerId(int travellerId) {
        this.travellerId = travellerId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    @Override
    public String toString() {
        return "Account{" +
                "id=" + id +
                ", travellerId=" + travellerId +
                ", suspendedDate=" + suspendedDate +
                ", balance=" + balance +
                '}';
    }
}
