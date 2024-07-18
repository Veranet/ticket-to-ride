package pl.veranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.model.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {
}
