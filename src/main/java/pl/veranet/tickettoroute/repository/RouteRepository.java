package pl.veranet.tickettoroute.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.tickettoroute.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}
