package pl.veranet.tickettoroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.tickettoroute.entity.Account;

import java.time.Instant;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Integer> {
    Optional<Account> findAccountByIdAndSuspendedDateIsNullOrSuspendedDateIsBefore(Integer id, Instant suspendedDate);
}
