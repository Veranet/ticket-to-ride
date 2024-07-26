package pl.veranet.tickettoroute.dto;

import java.util.Objects;

public class AccountBalanceDto {
    private Double balance;

    public AccountBalanceDto(Double balance) {
        this.balance = balance;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalanceDto that = (AccountBalanceDto) o;
        return Objects.equals(balance, that.balance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(balance);
    }

    @Override
    public String toString() {
        return "AccountBalanceDto{" +
                "balance=" + balance +
                '}';
    }
}
