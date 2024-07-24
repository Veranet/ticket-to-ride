package pl.veranet.tickettoroute.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;
import java.util.Objects;

public class BalanceDto {
    @NotNull
    private Integer accountId;
    @Positive
    @NotNull
    private BigDecimal amount;

    public BalanceDto() {
    }

    public BalanceDto(Integer accountId, BigDecimal amount) {
        this.accountId = accountId;
        this.amount = amount;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(Integer accountId) {
        this.accountId = accountId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BalanceDto that = (BalanceDto) o;
        return Objects.equals(accountId, that.accountId) && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(accountId, amount);
    }

    @Override
    public String toString() {
        return "BalanceDto{" +
                "accountId=" + accountId +
                ", balance=" + amount +
                '}';
    }
}
