package pl.veranet.tickettoroute.service;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.veranet.tickettoroute.dto.Price;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Route;
import pl.veranet.tickettoroute.repository.RouteRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RouteServiceTest {
    @Mock
    private RouteRepository routeRepository;
    private RouteService routeService;

    @BeforeEach
    void init() {
        var oneSegmentPrice = 5.0;
        var twoSegmentPrice = 7.0;
        var threeSegmentPrice = 10.0;
        routeService = new RouteService(routeRepository, oneSegmentPrice, twoSegmentPrice, threeSegmentPrice);
    }

    @Test
    void shouldCountOptimalPathAndPriceWhenHappyPath() {
        List<Route> segments = new ArrayList<>();
        segments.add(new Route("benc", "cov", 1));
        segments.add(new Route("benc", "bris", 3));
        segments.add(new Route("benc", "swip", 4));
        segments.add(new Route("cov", "n", 2));
        segments.add(new Route("n", "london", 2));
        segments.add(new Route("bris", "swip", 2));
        segments.add(new Route("swip", "r", 4));
        segments.add(new Route("r", "london", 1));
        when(routeRepository.findAll()).thenReturn(segments);
        var responsePriceEntity = routeService.countOptimalPathAndPrice("cov", "r");
        var expected = new Price(5, BigDecimal.valueOf(17.0), Currency.GBP);
        assertEquals(expected, responsePriceEntity);
    }

    @Test
    void shouldThrowExceptionWhenFromIsNull() {
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice(null, "A"));
        assertEquals("From Town and To Town must not be null", expected.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenToIsNull() {
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice("A", null));
        assertEquals("From Town and To Town must not be null", expected.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFromIsEqualsTo() {
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice("A", "A"));
        assertEquals("From Town and To Town are the same", expected.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenRoutesDoNotExist() {
        when(routeRepository.findAll()).thenReturn(List.of());
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice("B", "A"));
        assertEquals("Routes are empty", expected.getMessage());
    }
}
