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

    // ===== Vehicle based queries =====

    List<ServiceEntry> findByVehicle_Id(Long vehicleId);
    List<ServiceEntry> findByVehicle_Id(long vehicleId); // for tests using int/long

    @Query("SELECT s FROM ServiceEntry s WHERE s.vehicle.id = :vehicleId")
    List<ServiceEntry> findByVehicleId(@Param("vehicleId") Long vehicleId);

    Optional<ServiceEntry> findByIdAndVehicle_Id(Long id, Long vehicleId);
    Optional<ServiceEntry> findByIdAndVehicle_Id(long id, long vehicleId);

    Optional<ServiceEntry> findTopByVehicle_IdOrderByOdometerReadingDesc(Long vehicleId);
    Optional<ServiceEntry> findTopByVehicle_IdOrderByOdometerReadingDesc(long vehicleId);

    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    // ===== Date range =====

    List<ServiceEntry> findByVehicle_IdAndServiceDateBetween(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // REQUIRED by tests (exact name)
    @Query("""
        SELECT s FROM ServiceEntry s
        WHERE s.vehicle.id = :vehicleId
          AND s.serviceDate BETWEEN :startDate AND :endDate
    """)
    List<ServiceEntry> findByVehicleAndDateRange(
            @Param("vehicleId") long vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // ===== Garage =====

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
