package pl.veranet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.entity.Traveller;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class TravellerRepositoryTest {

    @Autowired
    private TravellerRepository travellerRepository;

    @Test
    void shouldSaveTraveller() {
        var traveller =
                new Traveller(null, "Vernon", "nver@mail.com",
                        Instant.parse("2020-01-01T00:00:00Z"), null);
        travellerRepository.save(traveller);

        var expectedTraveller = Optional.of(new Traveller(4, "Vernon", "nver@mail.com",
                Instant.parse("2020-01-01T00:00:00Z"), null));
        assertEquals(expectedTraveller, travellerRepository.findById(4));
    }

    @Test
    void shouldUpdateTraveller() {
        travellerRepository.updateTravellerByDeletedDate(1, Instant.parse("2027-05-05T00:00:00Z"));
        var updatedTraveller = travellerRepository.findById(1);

        var expectedTraveller = Optional.of(new Traveller(1, "Alex", "nalex@dot.com",
                Instant.parse("2024-05-05T00:00:00Z"), Instant.parse("2027-05-05T00:00:00Z")));
        assertEquals(expectedTraveller, updatedTraveller);
    }

    @Test
    void shouldReturnListTravellerWhenTravellersAreExist() {
        var expectedTravellers = List.of(
                new Traveller(1, "Alex", "nalex@dot.com", Instant.parse("2024-05-05T00:00:00Z"), null),
                new Traveller(2, "Bob", "nob@dot.com", Instant.parse("2024-06-06T00:00:00Z"), null),
                new Traveller(3, "Jack", "nlack@dot.com", Instant.parse("2024-07-07T00:00:00Z"),
                        Instant.parse("2024-06-05T00:00:00Z"))
        );

        var actualTravellers = travellerRepository.findAll();

        assertEquals(expectedTravellers, actualTravellers);
    }
}
