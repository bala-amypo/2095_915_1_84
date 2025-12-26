@Override
public ServiceEntry createServiceEntry(ServiceEntry entry) {
    return serviceEntryRepository.save(entry);
}
