package pl.veranet.tickettoroute.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.tickettoroute.entity.Ticket;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class TicketRepositoryTest {
    @Autowired
    private TicketRepository ticketRepository;

    @Test
    void shouldSaveTicketWhenHappyPath() {
        var ticket = new Ticket(null, 2, "A", "B", BigDecimal.valueOf(25),
                Instant.parse("2024-11-30T18:35:24.00Z"), Instant.parse("2024-11-30T18:35:24.00Z"));
        ticketRepository.save(ticket);

        var expected = Optional.of(
                new Ticket(2, 2, "A", "B", BigDecimal.valueOf(25),
                Instant.parse("2024-11-30T18:35:24.00Z"), Instant.parse("2024-11-30T18:35:24.00Z")));
        assertEquals(expected, ticketRepository.findById(2));
    }
}
