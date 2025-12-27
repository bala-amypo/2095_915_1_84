package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    // ================= BASIC QUERIES =================

    // Used by tests
    List<ServiceEntry> findByVehicleId(Long vehicleId);

    Optional<ServiceEntry> findByIdAndVehicleId(Long id, Long vehicleId);

    List<ServiceEntry> findByGarageId(Long garageId);

    List<ServiceEntry> findByServiceDateBetween(
            LocalDate startDate,
            LocalDate endDate
    );

    List<ServiceEntry> findByVehicleIdAndServiceDateBetween(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // ================= TEST-EXPECTED CUSTOM METHODS =================

    // Expected by tests: findByGarageAndMinOdometer(long,int)
    @Query("""
        SELECT s FROM ServiceEntry s
        WHERE s.garageId = :garageId
          AND s.odometerReading > :minOdometer
    """)
    List<ServiceEntry> findByGarageAndMinOdometer(
            @Param("garageId") Long garageId,
            @Param("minOdometer") int minOdometer
    );

    // Expected by tests: findByVehicleAndDateRange(long, LocalDate, LocalDate)
    @Query("""
        SELECT s FROM ServiceEntry s
        WHERE s.vehicleId = :vehicleId
          AND s.serviceDate BETWEEN :startDate AND :endDate
    """)
    List<ServiceEntry> findByVehicleAndDateRange(
            @Param("vehicleId") Long vehicleId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate
    );

    // Latest service entry for a vehicle (by odometer)
    Optional<ServiceEntry> findTopByVehicleIdOrderByOdometerReadingDesc(Long vehicleId);
}
