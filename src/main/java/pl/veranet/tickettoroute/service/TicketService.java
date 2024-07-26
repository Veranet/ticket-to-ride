package pl.veranet.tickettoroute.service;

import org.springframework.stereotype.Service;
import pl.veranet.tickettoroute.dto.Decision;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Ticket;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.TicketRepository;

import java.time.temporal.ChronoUnit;

@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final AccountService accountService;
    private final DateTimeProvider dateTimeProvider;

    public TicketService(TicketRepository ticketRepository,
                         AccountService accountService,
                         DateTimeProvider dateTimeProvider) {
        this.ticketRepository = ticketRepository;
        this.accountService = accountService;
        this.dateTimeProvider = dateTimeProvider;
    }

    public Decision createTicket(Ticket ticket) {
        double balance = accountService.getBalance(ticket.getTravellerId());
        double price = ticket.getPrice().doubleValue();

        if (balance < price) {
            var lackOf = String.valueOf(price - balance);
            return new Decision("lackOf", Currency.GBP, null, lackOf);
        }

        var createDate = dateTimeProvider.provideDateTime();
        var expiredDate = createDate.plus(2, ChronoUnit.DAYS);
        var ticketForSave =
                new Ticket(null, ticket.getTravellerId(), ticket.getFromTown(), ticket.getToTown(), ticket.getPrice(),
                        createDate, expiredDate);
        ticketRepository.save(ticketForSave);
        accountService.updateBalance(ticket.getTravellerId(), ticket.getPrice().negate());
        var success = String.valueOf(balance - price);
        return new Decision("success", Currency.GBP, success, null);
    }
}
