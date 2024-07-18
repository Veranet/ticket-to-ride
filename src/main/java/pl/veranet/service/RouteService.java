package pl.veranet.service;

import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.stereotype.Service;
import pl.veranet.enams.Currency;
import pl.veranet.model.ResponsePriceEntity;
import pl.veranet.model.Route;
import pl.veranet.repository.RouteRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class RouteService {
    private RouteRepository routeRepository;

    public ResponsePriceEntity countOptimalPathAndPrice(String fromTown, String toTown) {
        if(fromTown == null || toTown == null) {
            throw new IllegalArgumentException("From Town and To Town must not be null");
        }
        if(fromTown.equals(toTown)) {
            throw new IllegalArgumentException("From Town and To Town are the same");
        }
        List<Route> allRoutes = routeRepository.findAll();
        if(allRoutes.isEmpty()) {
            throw new IllegalArgumentException("Routes are empty");
        }
        Graph<String, DefaultWeightedEdge> multiGraph = getStringDefaultWeightedEdgeGraph(allRoutes);
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraAl =
                new DijkstraShortestPath<>(multiGraph);
        double weight = dijkstraAl.getPath("cov", "r").getWeight();
        double optimalPrice = calculatePrice((int) weight);
        Route responseRoute = new Route(fromTown, toTown, (int) weight);
        ResponsePriceEntity response;
        response = new ResponsePriceEntity((int) weight, BigDecimal.valueOf(optimalPrice), Currency.GBP);
        return response;
    }

    private Graph<String, DefaultWeightedEdge> getStringDefaultWeightedEdgeGraph(List<Route> allRoutes) {
        Graph<String, DefaultWeightedEdge> multiGraph =
                new WeightedMultigraph<>(DefaultWeightedEdge.class);
        for (Route route : allRoutes) {
            multiGraph.addVertex(route.getFromCity());
            multiGraph.addVertex(route.getToCity());
            DefaultWeightedEdge edge = multiGraph.addEdge(route.getFromCity(), route.getToCity());
            multiGraph.setEdgeWeight(edge, route.getSegments());
        }
        return multiGraph;
    }

    private double calculatePrice(int segments) {
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
