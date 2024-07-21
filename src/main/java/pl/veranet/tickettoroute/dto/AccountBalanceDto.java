package pl.veranet.tickettoroute.dto;

import java.util.Objects;

public class AccountBalanceDto {
    private Double accountBalance;

    public AccountBalanceDto(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(Double accountBalance) {
        this.accountBalance = accountBalance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AccountBalanceDto that = (AccountBalanceDto) o;
        return Objects.equals(accountBalance, that.accountBalance);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(accountBalance);
    }

    @Override
    public String toString() {
        return "AccountBalanceDto{" +
                "accountBalance=" + accountBalance +
                '}';
    }
}
