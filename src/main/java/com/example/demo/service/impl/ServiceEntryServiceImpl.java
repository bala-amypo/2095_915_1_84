@Service
public class ServiceEntryServiceImpl implements ServiceEntryService {

    private final ServiceEntryRepository repository;

    public ServiceEntryServiceImpl(ServiceEntryRepository repository) {
        this.repository = repository;
    }

    @Override
    public ServiceEntry save(ServiceEntry entry) {

        Vehicle v = entry.getVehicle();

        if (!v.getActive()) {
            throw new RuntimeException("Vehicle is inactive");
        }

        ServiceEntry last =
                repository.findTopByVehicleOrderByOdometerReadingDesc(v);

        if (last != null &&
            entry.getOdometerReading() <= last.getOdometerReading()) {

            throw new RuntimeException(
                "Odometer reading must be greater than last service"
            );
        }

        return repository.save(entry);
    }
}
