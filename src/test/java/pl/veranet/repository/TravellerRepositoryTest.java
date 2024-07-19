package pl.veranet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.entity.Traveller;

import java.time.Instant;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class TravellerRepositoryTest {

    @Autowired
    private TravellerRepository travellerRepository;

    @Test
    void shouldUpdateTraveller() {
        travellerRepository.updateTravellerByDeletedDate(1, Boolean.FALSE);
        Optional<Traveller> updatedTraveller = travellerRepository.findById(1);

        Optional<Traveller> expectedTraveller = Optional.of(new Traveller(1, "Alex", "nalex@dot.com",
                Instant.parse("2024-05-05T00:00:00Z"), Boolean.FALSE));
        assertEquals(expectedTraveller, updatedTraveller);
    }
}