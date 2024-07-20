package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.entity.Account;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.AccountRepository;

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
        var account = isUserExist(travellerId);
        int balance = account.getBalance().intValue() + newBalance.intValue();
        account.setBalance(BigDecimal.valueOf(balance));
        accountRepository.save(account);
    }

    public int getBalance(int travellerId) {
        var account = isUserExist(travellerId);
        return account.getBalance().intValue();
    }

    private Account isUserExist(int userId) {
        return accountRepository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(userId,
                dateTimeProvider.now()).orElseThrow(() ->
                new IllegalArgumentException(String.format("Traveller with ID =%d not found", userId)));
    }
}
