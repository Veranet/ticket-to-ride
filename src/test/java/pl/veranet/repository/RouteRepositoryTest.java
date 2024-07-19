package pl.veranet.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.jdbc.Sql;
import pl.veranet.entity.Route;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Sql("/testDb.sql")
class RouteRepositoryTest {
    @Autowired
    private RouteRepository routeRepository;

    @Test
    void shouldReturnListRoute() {
    List<Route> expected = List.of(
            new Route(1, "A", "C", 5),
            new Route(2, "A", "B", 2)
    );
         assertEquals(expected, routeRepository.findAll());
    }
}