package pl.veranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
