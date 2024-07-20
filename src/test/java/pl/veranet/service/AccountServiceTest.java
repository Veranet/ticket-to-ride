package pl.veranet.service;

import org.junit.jupiter.api.Test;
import pl.veranet.entity.Account;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.AccountRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AccountServiceTest {
    AccountRepository accountRepository = mock(AccountRepository.class);
    DateTimeProvider dateTimeProvider = mock(DateTimeProvider.class);

    AccountService accountService = new AccountService(accountRepository, dateTimeProvider);

    @Test
    void shouldReturnTravellerBalanceIfAccountExists() {
        when(dateTimeProvider.now()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        when(accountRepository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(1,
                Instant.parse("2020-01-01T00:00:00Z")))
                .thenReturn(Optional.of(new Account(1, 1, BigDecimal.valueOf(15))));

        assertEquals(15, accountService.getBalance(1));
    }

    @Test
    void shouldThrowExceptionIfAccountDoesNotExist() {
        when(dateTimeProvider.now()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        when(accountRepository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(1,
                Instant.parse("2020-01-01T00:00:00Z")))
                .thenReturn(Optional.empty());
        var actualException =
                assertThrows(IllegalArgumentException.class, () -> accountService.getBalance(1));
        assertEquals("Traveller with ID =1 not found", actualException.getMessage());
    }

    @Test
    void shouldUpdateBalance() {
        when(dateTimeProvider.now()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        when(accountRepository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(1,
                Instant.parse("2020-01-01T00:00:00Z")))
                .thenReturn(Optional.of(new Account(1, 1, BigDecimal.valueOf(15))));
        var accountForSave = new Account(1, 1, BigDecimal.valueOf(25));
        accountService.updateBalance(1, BigDecimal.valueOf(10));
        verify(accountRepository).save(accountForSave);
    }
}
