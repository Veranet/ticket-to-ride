package pl.veranet.tickettoroute.converter;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import pl.veranet.tickettoroute.dto.TicketCreateDto;
import pl.veranet.tickettoroute.entity.Ticket;

@Component
public class TicketCreateDtoToTicketConverter implements Converter<TicketCreateDto, Ticket> {

        @Override
        public Ticket convert(TicketCreateDto source) {
            return new Ticket(source.getTravellerId(), source.getFromTown(), source.getToTown(), source.getPrice());
        }
    }
