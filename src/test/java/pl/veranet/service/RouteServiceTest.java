package pl.veranet.service;

import org.junit.jupiter.api.Test;
import pl.veranet.enams.Currency;
import pl.veranet.entity.Route;
import pl.veranet.model.ResponsePriceEntity;
import pl.veranet.repository.RouteRepository;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class RouteServiceTest {
    private RouteRepository routeRepository = mock(RouteRepository.class);
    private RouteService routeService = new RouteService(routeRepository);

    @Test
    void shouldCountOptimalPathAndPrice() {
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
        ResponsePriceEntity responsePriceEntity = routeService.countOptimalPathAndPrice("cov", "r");
        ResponsePriceEntity expected = new ResponsePriceEntity(5, BigDecimal.valueOf(17), Currency.GBP);
        assertEquals(expected, responsePriceEntity);
    }

    @Test
    void shouldThrowExceptionWhenFromTownIsNull() {
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice(null, "A"));
        assertEquals("From Town and To Town must not be null", expected.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenToTownIsNull() {
        Exception expected = assertThrows(IllegalArgumentException.class, () ->
                routeService.countOptimalPathAndPrice("A", null));
        assertEquals("From Town and To Town must not be null", expected.getMessage());
    }

    @Test
    void shouldThrowExceptionWhenFromTownIsEqualsToTown() {
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
