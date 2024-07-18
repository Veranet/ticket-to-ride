package pl.veranet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.util.Objects;

@Entity
public class Route {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String fromCity;
    private String toCity;
    private int segments;

    public Route() {
    }

    public Route(String fromCity, String toCity, int segments) {
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.segments = segments;
    }

    public Route(Integer id, String fromCity, String toCity, int segments) {
        this.id = id;
        this.fromCity = fromCity;
        this.toCity = toCity;
        this.segments = segments;
    }

    public String getFromCity() {
        return fromCity;
    }

    public void setFromCity(String fromCity) {
        this.fromCity = fromCity;
    }

    public String getToCity() {
        return toCity;
    }

    public void setToCity(String toCity) {
        this.toCity = toCity;
    }

    public double getSegments() {
        return segments;
    }

    public void setSegments(int segments) {
        this.segments = segments;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Route segment = (Route) o;
        return Double.compare(segments, segment.segments) == 0
                && Objects.equals(fromCity, segment.fromCity)
                && Objects.equals(toCity, segment.toCity);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fromCity, toCity, segments);
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromCity='" + fromCity + '\'' +
                ", toCity='" + toCity + '\'' +
                ", segments=" + segments +
                '}';
    }
}
