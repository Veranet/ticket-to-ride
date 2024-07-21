package pl.veranet.tickettoroute.service;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import pl.veranet.tickettoroute.dto.ResponseTicketEntity;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Ticket;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.TicketRepository;

import java.math.BigDecimal;
import java.time.Instant;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TicketServiceTest {
    TicketRepository ticketRepository = mock(TicketRepository.class);
    AccountService accountService = Mockito.mock(AccountService.class);
    DateTimeProvider dateTimeProvider = mock(DateTimeProvider.class);

    TicketService ticketService =
            new TicketService(ticketRepository, accountService, dateTimeProvider);

    @Test
    void shouldCreateTicketAndReturnResponseTicketEntitySuccess() {
        when(accountService.getBalance(1)).thenReturn(15.0);
        when(dateTimeProvider.now()).thenReturn(Instant.parse("2020-01-01T00:00:00Z"));
        Ticket ticket = new Ticket(null, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        Ticket ticketFromRepo = new Ticket(1, 1, "A", "C", BigDecimal.valueOf(5),
                Instant.parse("2020-01-01T00:00:00Z"),
                Instant.parse("2020-01-03T00:00:00Z"));
        when(ticketRepository.save(ticket)).thenReturn(ticketFromRepo);

        var ticketCreate = new Ticket(1,"A", "C", BigDecimal.valueOf(5));
        var actual =
                ticketService.createTicket(ticketCreate);


        ResponseTicketEntity expected =
                new ResponseTicketEntity("success", Currency.GBP, "10.0", null);
        assertEquals(expected, actual);
    }

    @Test
    void shouldDoNotCreateTicketAndReturnResponseTicketEntityLackOfWhenBalanceLessThanPrice() {
        when(accountService.getBalance(1)).thenReturn(15.0);
        verify(dateTimeProvider, never()).now();

        var ticketCreate = new Ticket(1,"A", "C", BigDecimal.valueOf(50.0));
        var actual =
                ticketService.createTicket(ticketCreate);


        var expected =
                new ResponseTicketEntity("lackOf", Currency.GBP, null, "35.0");
        assertEquals(expected, actual);
    }
}
