package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    // Get all service entries for a vehicle (by vehicle ID)
    List<ServiceEntry> findByVehicle_Id(Long vehicleId);

    // Same as above, but required by tests (fixed using JPQL)
    @Query("SELECT s FROM ServiceEntry s WHERE s.vehicle.id = :vehicleId")
    List<ServiceEntry> findByVehicleId(@Param("vehicleId") Long vehicleId);

    // Find a service entry by its ID and vehicle ID
    Optional<ServiceEntry> findByIdAndVehicle_Id(Long id, Long vehicleId);

    // Get the latest service entry for a vehicle using odometer reading
    Optional<ServiceEntry> findTopByVehicle_IdOrderByOdometerReadingDesc(Long vehicleId);

    // Same as above but using Vehicle entity directly
    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    // Get service entries for a vehicle within a date range
    List<ServiceEntry> findByVehicle_IdAndServiceDateBetween(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // Get service entries done by a garage with minimum odometer reading
    @Query("""
        SELECT s FROM ServiceEntry s
        WHERE s.garage.id = :garageId
          AND s.odometerReading >= :minOdometer
    """)
    List<ServiceEntry> findByGarageAndMinOdometer(
            @Param("garageId") Long garageId,
            @Param("minOdometer") int minOdometer
    );
}
