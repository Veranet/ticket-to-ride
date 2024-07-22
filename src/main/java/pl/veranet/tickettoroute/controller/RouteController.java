package pl.veranet.tickettoroute.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.ResponsePriceEntity;
import pl.veranet.tickettoroute.service.RouteService;

@RestController
@RequestMapping("/price")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponsePriceEntity calculateThePriceOfAMostOptimalTravel(@RequestParam("fromTown") String fromTown,
                                                                     @RequestParam("toTown") String toTown) {
        return routeService.countOptimalPathAndPrice(fromTown, toTown);
    }
}
