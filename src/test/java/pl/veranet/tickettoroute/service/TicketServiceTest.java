package pl.veranet.tickettoroute.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.veranet.tickettoroute.dto.Decision;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Ticket;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.TicketRepository;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TicketServiceTest {
    @Mock
    TicketRepository ticketRepository;
    @Mock
    AccountService accountService;
    @Mock
    DateTimeProvider dateTimeProvider;
    @InjectMocks
    TicketService ticketService;

    @Test
    void shouldCreateTicketAndReturnResponseTicketEntitySuccess() {
        when(accountService.getBalance(1)).thenReturn(15.0);
        when(dateTimeProvider.provideDateTime()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        Ticket ticket = new Ticket(null, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        Ticket ticketFromRepo = new Ticket(1, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        when(ticketRepository.save(ticket)).thenReturn(ticketFromRepo);

        var ticketCreate = new Ticket(1, "A", "C", BigDecimal.valueOf(5));
        var actual =
                ticketService.createTicket(ticketCreate);


        Decision expected =
                new Decision("success", Currency.GBP, "10.0", null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldNotCreateTicketAndReturnResponseTicketEntityLackOfWhenBalanceLessThanPrice() {
        when(accountService.getBalance(1)).thenReturn(15.0);
        verify(dateTimeProvider, never()).provideDateTime();

        var ticketCreate = new Ticket(1, "A", "C", BigDecimal.valueOf(50.0));
        var actual =
                ticketService.createTicket(ticketCreate);


        var expected =
                new Decision("lackOf", Currency.GBP, null, "35.0");
        assertEquals(expected, actual);
    }
}
