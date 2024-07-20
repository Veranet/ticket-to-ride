package pl.veranet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.model.ResponsePriceEntity;
import pl.veranet.service.RouteService;

@RestController
@RequestMapping("/ticket-to-ride/price")
public class RouteController {
    private final RouteService routeService;

    public RouteController(RouteService routeService) {
        this.routeService = routeService;
    }

    @GetMapping
    public ResponsePriceEntity calculateThePriceOfAMostOptimalTravel(@RequestParam("from") String fromTown,
                                                                     @RequestParam("to") String toTown) {
        return routeService.countOptimalPathAndPrice(fromTown, toTown);
    }
}
