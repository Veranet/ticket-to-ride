package pl.veranet.tickettoroute.service;

import org.springframework.stereotype.Service;
import pl.veranet.tickettoroute.entity.Account;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.AccountRepository;

import java.math.BigDecimal;

@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final DateTimeProvider dateTimeProvider;

    public AccountService(AccountRepository accountRepository, DateTimeProvider dateTimeProvider) {
        this.accountRepository = accountRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void updateBalance(int travellerId, BigDecimal newBalance) {
        var account = getAccountIfExist(travellerId);
        var balance = account.getBalance().intValue() + newBalance.intValue();
        account.setBalance(BigDecimal.valueOf(balance));
        accountRepository.save(account);
    }

    public double getBalance(int travellerId) {
        var account = getAccountIfExist(travellerId);
        return account.getBalance().doubleValue();
    }

    private Account getAccountIfExist(int userId) {
        return accountRepository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(userId,
                dateTimeProvider.provideDateTime()).orElseThrow(() ->
                new IllegalArgumentException(String.format("Traveller with ID =%d not found", userId)));
    }
}
