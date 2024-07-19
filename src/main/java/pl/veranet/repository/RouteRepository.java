package pl.veranet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pl.veranet.entity.Route;

public interface RouteRepository extends JpaRepository<Route, Integer> {
}
