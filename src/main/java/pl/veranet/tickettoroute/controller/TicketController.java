package pl.veranet.tickettoroute.controller;

import jakarta.validation.Valid;
import org.springframework.core.convert.ConversionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.ResponseTicketEntity;
import pl.veranet.tickettoroute.dto.TicketCreateDto;
import pl.veranet.tickettoroute.entity.Ticket;
import pl.veranet.tickettoroute.service.TicketService;

@RestController
@RequestMapping("/ticket-to-ride")
public class TicketController {
    private final TicketService ticketService;
    private final ConversionService conversionService;

    public TicketController(TicketService ticketService, ConversionService conversionService) {
        this.ticketService = ticketService;
        this.conversionService = conversionService;
    }

    @PostMapping("/ticket")
    public ResponseTicketEntity buyTicket(//@AuthenticationPrincipal Traveller traveller,
                                           @Valid @RequestBody TicketCreateDto ticketCreateDto) {
        var ticket = conversionService.convert(ticketCreateDto, Ticket.class);
        return ticketService.createTicket(ticket);
    }
}
