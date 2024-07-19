package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.entity.Account;
import pl.veranet.repository.AccountRepository;
import pl.veranet.repository.TravellerRepository;

import java.math.BigDecimal;

@Service
public class AccountService {
    TravellerRepository travellerRepository;
    AccountRepository accountRepository;

    public int checkTraveller(int travellerId) {
        var userExist = isUserExist(travellerId);
        return userExist.getBalance().intValue();
    }

    public Account isUserExist(int userId) {
        return accountRepository
                .findById(userId).orElseThrow(() ->
                        new IllegalArgumentException(String.format("Traveller with ID =%d not found", userId)));
    }

    public void updateBalance(int travellerId, BigDecimal newBalance) {
        var account = isUserExist(travellerId);
        int balance = account.getBalance().intValue() + newBalance.intValue();
        account.setBalance(BigDecimal.valueOf(balance));
        accountRepository.save(account);
    }
}
