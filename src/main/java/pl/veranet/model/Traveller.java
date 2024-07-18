package pl.veranet.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.Instant;

@Entity
public class Traveller {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private String email;
    private Instant createdDate;
    private boolean deletedDate;

    public Traveller() {
    }
    public Traveller(String name, String email) {
        this.name = name;
        this.email = email;
        this.deletedDate = Boolean.TRUE;
    }
}
