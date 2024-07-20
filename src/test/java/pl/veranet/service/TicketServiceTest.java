package pl.veranet.service;

import org.junit.jupiter.api.Test;
import pl.veranet.enams.Currency;
import pl.veranet.entity.Ticket;
import pl.veranet.model.ResponseTicketEntity;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.TicketRepository;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {
    TicketRepository ticketRepository = mock(TicketRepository.class);
    AccountService accountService = mock(AccountService.class);
    DateTimeProvider dateTimeProvider = mock(DateTimeProvider.class);

    TicketService ticketService =
            new TicketService(ticketRepository, accountService, dateTimeProvider);

    @Test
    void shouldCreateTicketAndReturnResponseTicketEntitySuccess() {
        when(accountService.getBalance(1)).thenReturn(15);
        when(dateTimeProvider.now()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        Ticket ticket = new Ticket(null, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        Ticket ticketFromRepo = new Ticket(1, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        when(ticketRepository.save(ticket)).thenReturn(ticketFromRepo);
        ResponseTicketEntity expected =
                new ResponseTicketEntity("success", Currency.GBP, "10", null);
        var actual =
                ticketService.createTicket(1, "A", "C", BigDecimal.valueOf(5), Currency.GBP);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDoNotCreateTicketAndReturnResponseTicketEntityLackOfWhenBalanceLessThanPrice() {
        when(accountService.getBalance(1)).thenReturn(15);
        verify(dateTimeProvider, never()).now();

        ResponseTicketEntity expected =
                new ResponseTicketEntity("lackOf", Currency.GBP, null, "35");
        var actual =
                ticketService.createTicket(1, "A", "C", BigDecimal.valueOf(50), Currency.GBP);
        assertEquals(expected, actual);
    }
}
