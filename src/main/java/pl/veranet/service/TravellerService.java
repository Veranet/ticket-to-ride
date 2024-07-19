package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.entity.Traveller;
import pl.veranet.provider.DateTimeProvider;
import pl.veranet.repository.TravellerRepository;

@Service
public class TravellerService {
    private TravellerRepository travellerRepository;
    private DateTimeProvider dateTimeProvider;

    public void createTraveller(String name, String email) {
        Traveller traveller = new Traveller(null, name, email, dateTimeProvider.now(), true);
        travellerRepository.save(traveller);
    }

    public void deleteTraveller(Traveller traveller) {
        travellerRepository.updateTravellerByDeletedDate(traveller.getId(), Boolean.FALSE);
    }
}
