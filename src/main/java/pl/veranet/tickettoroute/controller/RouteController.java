package pl.veranet.tickettoroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.Price;
import pl.veranet.tickettoroute.service.RouteService;

@RestController
@RequestMapping("/price")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    /**
     * Calculates the price of the most optimal travel between two towns.
     * This method is public and can be accessed without authentication.
     * The calculation is based on Dijkstra's algorithm to determine the shortest path.
     *
     * @param fromTown the starting town of the route
     * @param toTown   the destination town of the route
     * @return a ResponsePriceEntity containing the price and other details of the optimal route
     */
    @GetMapping
    public Price calculateThePriceOfAMostOptimalTravel(@RequestParam("fromTown") String fromTown,
                                                       @RequestParam("toTown") String toTown) {
        return routeService.countOptimalPathAndPrice(fromTown, toTown);
    }
}
