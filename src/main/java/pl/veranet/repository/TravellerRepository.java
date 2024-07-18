package pl.veranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.model.Traveller;

public interface TravellerRepository extends JpaRepository<Traveller, Integer> {

}
