package pl.veranet.controller;

import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import pl.veranet.entity.Traveller;
import pl.veranet.model.TravellerDto;
import pl.veranet.service.TravellerService;

import java.time.Instant;
import java.util.List;

@RestController
@RequestMapping("/ticket-to-ride/traveller")
public class TravellerController {
    private final TravellerService travellerService;

    public TravellerController(TravellerService travellerService) {
        this.travellerService = travellerService;
    }

    @PostMapping
    public void createTraveller(@Valid @RequestBody TravellerDto travellerDto) {
        travellerService.createTraveller(travellerDto);
    }

    @DeleteMapping()
    public void deleteTraveller(@RequestParam Integer id,
                                @RequestParam String date) {
        travellerService.deleteTraveller(id, Instant.parse(date));
    }

    @GetMapping
    public List<Traveller> getTravellers() {
        return travellerService.getAllTravellers();
    }
}
