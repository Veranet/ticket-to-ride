package pl.veranet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.entity.Account;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class AccountRepositoryTest {
    @Autowired
    private AccountRepository repository;

  @Test
 void shouldReturnOptionalAccountById() {
      Optional<Account> expected = Optional.of(new Account(1, 1, BigDecimal.valueOf(25)));
      assertEquals(expected, repository.findById(1));
  }

  @Test
    void shouldReturnOptionalEmptyWhenAccountDoesNotExist() {
      assertEquals(Optional.empty(), repository.findById(20));
  }

  @Test
    void shouldSaveAccount() {
      Optional<Account> expected = Optional.of(new Account(4, 1, BigDecimal.valueOf(25)));
      Account accountSorSave = new Account(null, 1, BigDecimal.valueOf(25));
      repository.save(accountSorSave);
      assertEquals(expected, repository.findById(4));
  }
}