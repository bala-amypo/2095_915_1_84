package com.example.demo.repository;

import com.example.demo.model.ServiceEntry;
import com.example.demo.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface ServiceEntryRepository extends JpaRepository<ServiceEntry, Long> {

    // already present
    List<ServiceEntry> findByVehicle_Id(Long vehicleId);

    // ✅ REQUIRED for tests
    List<ServiceEntry> findByVehicleId(Long vehicleId);

    // already present
    Optional<ServiceEntry> findByIdAndVehicle_Id(Long id, Long vehicleId);

    // already present
    Optional<ServiceEntry> findTopByVehicle_IdOrderByOdometerReadingDesc(Long vehicleId);

    // ✅ REQUIRED for tests (Vehicle object version)
    Optional<ServiceEntry> findTopByVehicleOrderByOdometerReadingDesc(Vehicle vehicle);

    // already present
    List<ServiceEntry> findByServiceDateBetween(LocalDate startDate, LocalDate endDate);

    // already present
    List<ServiceEntry> findByGarage_Id(Long garageId);

    // already present
    List<ServiceEntry> findByVehicle_IdAndServiceDateBetween(
            Long vehicleId,
            LocalDate startDate,
            LocalDate endDate
    );

    // ✅ REQUIRED for tests
    @Query("""
        SELECT s FROM ServiceEntry s
        WHERE s.garage.id = :garageId
        AND s.odometerReading >= :minOdometer
    """)
    List<ServiceEntry> findByGarageAndMinOdometer(Long garageId, int minOdometer);
}
