package pl.veranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.model.Ticket;

import java.util.Optional;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
    //void save(Ticket ticket, int travellerId);
}
