package pl.veranet.tickettoroute.entity;

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
    private String fromTown;
    private String toTown;
    private int segmentsAmount;

    public Route() {
    }

    public Route(String fromTown, String toTown, int segmentsAmount) {
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.segmentsAmount = segmentsAmount;
    }

    public Route(Integer id, String fromTown, String toTown, int segmentsAmount) {
        this.id = id;
        this.fromTown = fromTown;
        this.toTown = toTown;
        this.segmentsAmount = segmentsAmount;
    }

    public String getFromTown() {
        return fromTown;
    }

    public void setFromTown(String fromTown) {
        this.fromTown = fromTown;
    }

    public String getToTown() {
        return toTown;
    }

    public void setToTown(String toTown) {
        this.toTown = toTown;
    }

    public double getSegmentsAmount() {
        return segmentsAmount;
    }

    public void setSegmentsAmount(int segments) {
        this.segmentsAmount = segments;
    }

    public Integer getId() {
        return id;
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Route route = (Route) o;
        return segmentsAmount == route.segmentsAmount && Objects.equals(id, route.id) && Objects.equals(fromTown, route.fromTown)
                && Objects.equals(toTown, route.toTown);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, fromTown, toTown, segmentsAmount);
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Route{" +
                "id=" + id +
                ", fromTown='" + fromTown + '\'' +
                ", toTown='" + toTown + '\'' +
                ", segmentsAmount=" + segmentsAmount +
                '}';
    }
}
