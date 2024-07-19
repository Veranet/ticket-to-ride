package pl.veranet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.enams.Currency;
import pl.veranet.model.ResponseTicketEntity;
import pl.veranet.service.TicketService;

import java.math.BigDecimal;

@RestController
public class TicketController {
    TicketService ticketService;

    @GetMapping("/ticket-to-ride/ticket")
    public ResponseTicketEntity buyTicket(//@AuthenticationPrincipal Traveller traveller,
                                          @PathVariable String fromTown,
                                          @PathVariable String toTown,
                                          @PathVariable int segments,
                                          @PathVariable BigDecimal price,
                                          @PathVariable Currency currency) {
        return ticketService.createTicket(5, fromTown, toTown, segments, price, currency);
    }
}
