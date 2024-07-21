package pl.veranet.tickettoroute.service;

import org.junit.jupiter.api.Test;
import pl.veranet.tickettoroute.dto.TravellerCreateDto;
import pl.veranet.tickettoroute.entity.Account;
import pl.veranet.tickettoroute.entity.Traveller;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.AccountRepository;
import pl.veranet.tickettoroute.repository.TravellerRepository;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class TravellerServiceTest {
    private final TravellerRepository travellerRepository = mock(TravellerRepository.class);
    private final AccountRepository accountRepository = mock(AccountRepository.class);
    private final DateTimeProvider dateTimeProvider = mock(DateTimeProvider.class);

    TravellerService travellerService = new TravellerService(travellerRepository, accountRepository, dateTimeProvider);

    @Test
    void shouldCreateTravellerAndAccount() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(dateTimeProvider.now()).thenReturn(date);
        var travellerForSave = new Traveller(null, "Sara", "nsara@mc.com", date, null);
        when(travellerRepository.save(travellerForSave)).thenReturn(new Traveller(1, "Sara", "nsara@mc.com", date, null));

        var travellerDto = new TravellerCreateDto("Sara", "nsara@mc.com");

        travellerService.createTraveller(travellerDto);

        var accountForSave = new Account(null, 1, BigDecimal.valueOf(0.0));
        verify(accountRepository).save(accountForSave);
    }

    @Test
    void deleteTraveller() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(dateTimeProvider.now()).thenReturn(date);
        travellerService.deleteTraveller(1);
        verify(travellerRepository).updateTravellerByDeletedDate(1, date);
    }

    @Test
    void getAllTravellers() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(dateTimeProvider.now()).thenReturn(date);
        when(travellerRepository.findAll()).thenReturn(List.of(
                new Traveller(1, "Alex", "alex@email.com", date, null),
                new Traveller(1, "Alex", "alex@email.com", date, null)
        ));

        var expectedTravellers = List.of(
                new Traveller(1, "Alex", "alex@email.com", date, null),
                new Traveller(1, "Alex", "alex@email.com", date, null)
        );
        assertEquals(expectedTravellers, travellerRepository.findAll());
    }
}