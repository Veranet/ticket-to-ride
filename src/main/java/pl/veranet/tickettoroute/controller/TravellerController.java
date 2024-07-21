package pl.veranet.tickettoroute.controller;

import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.tickettoroute.dto.TravellerCreateDto;
import pl.veranet.tickettoroute.entity.Traveller;
import pl.veranet.tickettoroute.service.TravellerService;

import java.util.List;

@RestController
@RequestMapping("/ticket-to-ride/traveller")
public class TravellerController {
    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping
    public void createTraveller(@Validated @RequestBody TravellerCreateDto travellerCreateDto) {
        travellerService.createTraveller(travellerCreateDto);
    }

    @DeleteMapping("/{id}")
    public void deleteTraveller(@PathVariable("id") int id) {
        travellerService.deleteTraveller(id);
    }

    @GetMapping
    public List<Traveller> getTravellers() { // todo: for ADMIN roll only!!!
        return travellerService.getAllTravellers();
    }
}
