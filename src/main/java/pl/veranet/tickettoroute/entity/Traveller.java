package pl.veranet.tickettoroute.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;
import java.util.Objects;

@Entity
public class Traveller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Instant createdDate;
    private Instant deletedDate;

    public Traveller() {
    }

    public Traveller(Integer id, String name, String email, Instant createdDate, Instant deletedDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.createdDate = createdDate;
        this.deletedDate = deletedDate;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Instant getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Instant createdDate) {
        this.createdDate = createdDate;
    }

    public Instant getDeletedDate() {
        return deletedDate;
    }

    public void setDeletedDate(Instant deletedDate) {
        this.deletedDate = deletedDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Traveller traveller = (Traveller) o;
        return Objects.equals(id, traveller.id)
                && Objects.equals(name, traveller.name)
                && Objects.equals(email, traveller.email)
                && Objects.equals(createdDate, traveller.createdDate)
                && Objects.equals(deletedDate, traveller.deletedDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, email, createdDate, deletedDate);
    }

    @Override
    public String toString() {
        return "Traveller{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", createdDate=" + createdDate +
                ", deletedDate=" + deletedDate +
                '}';
    }
}
