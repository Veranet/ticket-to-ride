package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.entity.Account;
import pl.veranet.entity.Traveller;
import pl.veranet.model.TravellerDto;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.AccountRepository;
import pl.veranet.repository.TravellerRepository;

import java.math.BigDecimal;
import java.time.Instant;
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

    public void createTraveller(TravellerDto travellerDto) {
        Traveller traveller =
                new Traveller(null, travellerDto.getName(), travellerDto.getEmail(), dateTimeProvider.now(), null);
        Traveller travellerFromRepo = travellerRepository.save(traveller);
        Account account = new Account(null, travellerFromRepo.getId(), BigDecimal.valueOf(0.0));
        accountRepository.save(account);
    }

    public void deleteTraveller(int id, Instant deletedDate) {
        travellerRepository.updateTravellerByDeletedDate(id, deletedDate);
    }

    public List<Traveller> getAllTravellers() {
        return travellerRepository.findAll();
    }
}
