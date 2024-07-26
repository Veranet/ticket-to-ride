package pl.veranet.tickettoroute.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
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
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class TravellerServiceTest {
    @Mock
    private TravellerRepository travellerRepository;
    @Mock
    private AccountRepository accountRepository;
    @Mock
    private DateTimeProvider dateTimeProvider;
    @InjectMocks
    TravellerService travellerService;

    @Test
    void shouldCreateTravellerAndAccount() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(dateTimeProvider.provideDateTime()).thenReturn(date);
        var travellerForSave = new Traveller(null, "Sara", "nsara@mc.com", date, null);
        when(travellerRepository.save(travellerForSave)).thenReturn(new Traveller(1, "Sara", "nsara@mc.com", date, null));

        var travellerDto = new TravellerCreateDto("Sara", "nsara@mc.com");

        travellerService.createTraveller(travellerDto);

        var accountForSave = new Account(null, 1, BigDecimal.valueOf(0.0));
        verify(accountRepository).save(accountForSave);
    }

    @Test
    void shouldDeleteTravellerWhenHappyPath() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(dateTimeProvider.provideDateTime()).thenReturn(date);
        travellerService.deleteTraveller(1);
        verify(travellerRepository).updateTravellerByDeletedDate(1, date);
    }

    @Test
    void shouldReturnListTravellersWhenHappyPath() {
        var date = Instant.parse("2020-01-01T00:00:00Z");
        when(travellerRepository.findAll()).thenReturn(List.of(
                new Traveller(1, "Alex", "alex@email.com", date, null),
                new Traveller(1, "Alex", "alex@email.com", date, null)
        ));

        var expectedTravellers = List.of(
                new Traveller(1, "Alex", "alex@email.com", date, null),
                new Traveller(1, "Alex", "alex@email.com", date, null)
        );
        assertEquals(expectedTravellers, travellerService.getAllTravellers());
    }
}
