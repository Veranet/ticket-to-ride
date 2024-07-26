package pl.veranet.tickettoroute.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jgrapht.Graph;
import org.jgrapht.alg.shortestpath.DijkstraShortestPath;
import org.jgrapht.graph.DefaultWeightedEdge;
import org.jgrapht.graph.WeightedMultigraph;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import pl.veranet.tickettoroute.dto.Price;
import pl.veranet.tickettoroute.enams.Currency;
import pl.veranet.tickettoroute.entity.Route;
import pl.veranet.tickettoroute.repository.RouteRepository;

import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.util.List;

@Service
public class RouteService {
    private final double oneSegmentPrice;
    private final double twoSegmentsPrice;
    private final double threeSegmentsPrice;

    private final RouteRepository routeRepository;

    public RouteService(RouteRepository routeRepository,
                        @Value("${ticket.price.one-segment}") double oneSegmentPrice,
                        @Value("${ticket.price.two-segments}") double twoSegmentsPrice,
                        @Value("${ticket.price.three-segments}") double threeSegmentsPrice) {
        this.routeRepository = routeRepository;
        this.oneSegmentPrice = oneSegmentPrice;
        this.twoSegmentsPrice = twoSegmentsPrice;
        this.threeSegmentsPrice = threeSegmentsPrice;
    }

    public Price countOptimalPathAndPrice(String from, String to) {
        var allRoutes = getAllRoutes(from, to);
        Graph<String, DefaultWeightedEdge> graphRoutes = getRouteGraph(allRoutes);
        DijkstraShortestPath<String, DefaultWeightedEdge> dijkstraShortestPath =
                new DijkstraShortestPath<>(graphRoutes);
        int numberOfSegments = Double.valueOf(Math.ceil(dijkstraShortestPath.getPath(from, to).getWeight())).intValue();
        double optimalPrice = calculatePrice(numberOfSegments);
        return new Price(numberOfSegments, BigDecimal.valueOf(optimalPrice), Currency.GBP);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void loadRoutes() {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<Route>> typeReference = new TypeReference<>(){};
        InputStream inputStream = getClass().getResourceAsStream("/routes.json");

        List<Route> routes;
        try {
            routes = objectMapper.readValue(inputStream, typeReference);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load routes from JSON.");
        }
        routeRepository.saveAll(routes);
    }

    private List<Route> getAllRoutes(String from, String to) {
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
        return allRoutes;
    }

    private Graph<String, DefaultWeightedEdge> getRouteGraph(List<Route> allRoutes) {
        Graph<String, DefaultWeightedEdge> multiGraph =
                new WeightedMultigraph<>(DefaultWeightedEdge.class);
        for (Route route : allRoutes) {
            multiGraph.addVertex(route.getFromTown());
            multiGraph.addVertex(route.getToTown());
            DefaultWeightedEdge edge = multiGraph.addEdge(route.getFromTown(), route.getToTown());
            multiGraph.setEdgeWeight(edge, route.getSegmentsAmount());
        }
        return multiGraph;
    }

    private double calculatePrice(int segments) {
        if (segments == 1) {
            return oneSegmentPrice;

        } else if (segments == 2) {
            return twoSegmentsPrice;
        } else if (segments == 3) {
            return threeSegmentsPrice;
        } else if (segments > 3) {
            int setsOfThree = segments / 3;
            int remainingSegments = segments % 3;
            return setsOfThree * 10 + calculatePrice(remainingSegments);
        }
        return -1;
    }
}
