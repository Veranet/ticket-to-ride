package pl.veranet.service;

import org.springframework.stereotype.Service;
import pl.veranet.model.Traveller;
import pl.veranet.repository.TravellerRepository;

import java.math.BigDecimal;

@Service
public class TravellerService {
    private TravellerRepository travellerRepository;

    public Traveller createtraveller(String name, String email, String password, BigDecimal balance) {

        return null;
    }

    public void deleteTraveller(Traveller traveller) {

    }
}
