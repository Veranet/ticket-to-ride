package pl.veranet.tickettoroute.controller;

import jakarta.validation.Valid;
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
@RequestMapping("/ticket-to-ride")
public class TravellerController {
    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping("/traveller")
    public void createTraveller(@Valid @RequestBody TravellerCreateDto travellerCreateDto) {
        travellerService.createTraveller(travellerCreateDto);
    }

    @DeleteMapping("/traveller/{id}")
    public void deleteTraveller(@PathVariable("id") int id) {
        travellerService.deleteTraveller(id);
    }

    /**
     * Retrieves a list of all travellers.
     * This endpoint is restricted to users with the ADMIN role.
     *
     * @return a list of Traveller objects
     */
    @GetMapping("/admin")
    public List<Traveller> getTravellers() { // todo: for ADMIN roll only!!!
        return travellerService.getAllTravellers();
    }
}
