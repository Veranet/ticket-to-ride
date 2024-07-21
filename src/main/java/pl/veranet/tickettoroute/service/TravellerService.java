package pl.veranet.tickettoroute.service;

import org.springframework.stereotype.Service;
import pl.veranet.tickettoroute.dto.TravellerCreateDto;
import pl.veranet.tickettoroute.entity.Account;
import pl.veranet.tickettoroute.entity.Traveller;
import pl.veranet.tickettoroute.provider.DateTimeProvider;
import pl.veranet.tickettoroute.repository.AccountRepository;
import pl.veranet.tickettoroute.repository.TravellerRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class TravellerService {
    private final TravellerRepository travellerRepository;
    private final AccountRepository accountRepository;
    private final DateTimeProvider dateTimeProvider;

    public TravellerService(TravellerRepository travellerRepository, AccountRepository accountRepository, DateTimeProvider dateTimeProvider) {
        this.travellerRepository = travellerRepository;
        this.accountRepository = accountRepository;
        this.dateTimeProvider = dateTimeProvider;
    }

    public void createTraveller(TravellerCreateDto travellerCreateDto) {
        Traveller traveller =
                new Traveller(null, travellerCreateDto.getName(), travellerCreateDto.getEmail(), dateTimeProvider.now(), null);
        Traveller travellerFromRepo = travellerRepository.save(traveller);
        Account account = new Account(null, travellerFromRepo.getId(), BigDecimal.valueOf(0.0));
        accountRepository.save(account);
    }

    public void deleteTraveller(int id) {
        travellerRepository.updateTravellerByDeletedDate(id, dateTimeProvider.now());
    }

    public List<Traveller> getAllTravellers() {
        return travellerRepository.findAll();
    }
}
