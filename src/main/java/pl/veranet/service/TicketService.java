package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.enams.Currency;
import pl.veranet.model.ResponseTicketEntity;
import pl.veranet.entity.Ticket;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.TicketRepository;

import java.math.BigDecimal;
import java.time.Instant;
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

    public ResponseTicketEntity createTicket(int travellerId, String fromTown, String toTown,
                                             BigDecimal price, Currency currency) {
        int balance = accountService.getBalance(travellerId);
        if (balance < price.intValue()) {
            var lackOf = String.valueOf(price.intValue() - balance);
            return new ResponseTicketEntity("lackOf", currency, null, lackOf);
        }
        Instant createDate = dateTimeProvider.now();
        Instant expiredDate = createDate.plus(2, ChronoUnit.DAYS);
        Ticket ticket = new Ticket(null, null, fromTown, toTown, price, createDate, expiredDate);
        ticketRepository.save(ticket);
        var success = String.valueOf(balance - price.intValue());
        return new ResponseTicketEntity("success", currency, success, null);
    }
}
