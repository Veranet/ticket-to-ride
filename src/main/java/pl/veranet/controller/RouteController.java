package pl.veranet.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.model.ResponsePriceEntity;
import pl.veranet.service.RouteService;

@RestController("/ticket-to-ride/price")
public class RouteController {
    private RouteService routeService;

    @GetMapping
    public ResponsePriceEntity calculateThePriceOfAMostOptimalTravel(@RequestParam String fromTown,
                                                                     @RequestParam String toTown) {
        return routeService.countOptimalPathAndPrice(fromTown, toTown);
    }
}
