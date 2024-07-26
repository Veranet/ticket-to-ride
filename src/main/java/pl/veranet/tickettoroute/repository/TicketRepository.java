package pl.veranet.tickettoroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.tickettoroute.entity.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Integer> {
}
