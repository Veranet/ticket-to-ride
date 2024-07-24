package pl.veranet.tickettoroute.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.tickettoroute.entity.Route;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class RouteRepositoryTest {
    @Autowired
    private RouteRepository routeRepository;

    @Test
    void shouldReturnListRouteWhenRoutesAreExist() {
    var expected = List.of(
            new Route(1, "A", "C", 5),
            new Route(2, "A", "B", 2)
    );

         assertEquals(expected, routeRepository.findAll());
    }

    @Test
    void shouldReturnEmptyListRouteWhenRoutesDoesNotExist() {
        routeRepository.deleteAll();

        assertEquals(List.of(), routeRepository.findAll());
    }
}
