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
    private TicketRepository ticketRepository;
    private AccountService accountService;
    private DateTimeProvider dateTimeProvider;

    public ResponseTicketEntity createTicket(int travellerId, String fromTown, String toTown, int segments,
                                             BigDecimal price, Currency currency) {
        int balance = accountService.checkTraveller(travellerId);
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
