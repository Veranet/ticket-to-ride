package pl.veranet.tickettoroute.service;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import pl.veranet.tickettoroute.dto.ResponsePriceEntity;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Route;
import pl.veranet.tickettoroute.repository.RouteRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RouteService {
    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository) {
        this.routeRepository = routeRepository;
    }

    public ResponsePriceEntity countOptimalPathAndPrice(String from, String to) {
        if(from == null || to == null) {
            throw new IllegalArgumentException("From Town and To Town must not be null");
        }
        if(from.equals(to)) {
            throw new IllegalArgumentException("From Town and To Town are the same");
        }
        List<Route> allRoutes = routeRepository.findAll();
        if(allRoutes.isEmpty()) {
            throw new IllegalArgumentException("Routes are empty");
        }
        Graph<String, DefaultWeightedEdge> multiGraph = getRouteGraph(allRoutes);
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAl =
                new DijkstraShortestPath<>(multiGraph);
        int weight = Double.valueOf(Math.ceil(dijkstraAl.getPath(from, to).getWeight())).intValue();
        double optimalPrice = calculatePrice(weight);
        return new ResponsePriceEntity(weight, BigDecimal.valueOf(optimalPrice), Currency.GBP);
    }

    private Graph<String, DefaultWeightedEdge> getRouteGraph(List<Route> allRoutes) { // todo : add routes to repo!!!
        Graph<String, DefaultWeightedEdge> multiGraph =
                new WeightedMultigraph<>(DefaultWeightedEdge.class);
        for (Route route : allRoutes) {
            multiGraph.addVertex(route.getFromTown());
            multiGraph.addVertex(route.getToTown());
            DefaultWeightedEdge edge = multiGraph.addEdge(route.getFromTown(), route.getToTown());
            multiGraph.setEdgeWeight(edge, route.getSegments());
        }
        return multiGraph;
    }

    private double calculatePrice(int segments) { // todo : move to application.yaml
        if (segments == 1) {
            return 5;
        } else if (segments == 2) {
            return 7;
        } else if (segments == 3) {
            return 10;
        } else if (segments > 3) {
            int setsOfThree = segments / 3;
            int remainingSegments = segments % 3;
            return setsOfThree * 10 + calculatePrice(remainingSegments);
        }
        return -1;
    }
}
