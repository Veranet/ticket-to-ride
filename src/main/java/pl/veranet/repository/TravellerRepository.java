package pl.veranet.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import pl.veranet.entity.Traveller;

public interface TravellerRepository extends JpaRepository<Traveller, Integer> {
    @Modifying
    @Transactional
    @Query(nativeQuery = true, value =
            "UPDATE traveller SET deleted_date = ?2 WHERE id = ?1 ;")
    void updateTravellerByDeletedDate(int ticketId, Boolean deletedDate);
}
