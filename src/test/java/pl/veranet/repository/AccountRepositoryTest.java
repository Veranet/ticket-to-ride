package pl.veranet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.entity.Account;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class AccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

    @Test
    void shouldReturnOptionalAccountById() {
        var expectedAAccount = Optional.of(new Account(1, 1, BigDecimal.valueOf(25)));

        assertEquals(expectedAAccount, repository.findById(1));
    }

    @Test
    void shouldReturnOptionalEmptyWhenAccountDoesNotExist() {
        assertEquals(Optional.empty(), repository.findById(20));
    }

    @Test
    void shouldSaveAccount() {
        var accountForSave = new Account(null, 1, BigDecimal.valueOf(25));
        repository.save(accountForSave);

        var expectedAAccount = Optional.of(new Account(4, 1, BigDecimal.valueOf(25)));
        assertEquals(expectedAAccount, repository.findById(4));
    }

    @Test
    void shouldReturnAccountByIdAndDateIsNull() {
        var accountForSave = new Account(null, 1, null,  BigDecimal.valueOf(25));
        repository.save(accountForSave);
        var account = repository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(1, null);

        var expectedAccount = Optional.of(new Account(4, 1, null, BigDecimal.valueOf(25)));
        assertEquals(expectedAccount, account);
    }

    @Test
    void shouldReturnAccountByIdAndDateWhenDateIsAfter() {
        var accountForSave =
                new Account(null, 1, Instant.parse("2025-01-03T00:00:00Z"), BigDecimal.valueOf(25));
        repository.save(accountForSave);

        var actualAccount = repository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(4,
                Instant.parse("2026-01-03T00:00:00Z"));

        var expectedAccount = Optional.of(new Account(4, 1, Instant.parse("2025-01-03T00:00:00Z"),
                BigDecimal.valueOf(25)));
        assertEquals(expectedAccount, actualAccount);
    }

    @Test
    void shouldReturnOptionalEmptyByIdAndDateWhenDateIsBefore() {
        var accountForSave =
                new Account(null, 1, Instant.parse("2025-01-03T00:00:00Z"), BigDecimal.valueOf(25));
        repository.save(accountForSave);

        var actualAccount = repository.findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(4,
                Instant.parse("2014-01-03T00:00:00Z"));

        assertEquals(Optional.empty(), actualAccount);
    }
}
