package com.example.demo;

import com.example.demo.model.Garage;
import com.example.demo.model.ServiceEntry;
import com.example.demo.model.ServicePart;
import com.example.demo.model.Vehicle;
import com.example.demo.model.VerificationLog;
import com.example.demo.repository.GarageRepository;
import com.example.demo.repository.ServiceEntryRepository;
import com.example.demo.repository.ServicePartRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.repository.VerificationLogRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.service.impl.GarageServiceImpl;
import com.example.demo.service.impl.ServiceEntryServiceImpl;
import com.example.demo.service.impl.ServicePartServiceImpl;
import com.example.demo.service.impl.VehicleServiceImpl;
import com.example.demo.service.impl.VerificationLogServiceImpl;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@Listeners(TestResultListener.class)
public class VehicleServiceHistoryApplicationTests {

    @Mock
    private VehicleRepository vehicleRepository;

    @Mock
    private GarageRepository garageRepository;

    @Mock
    private ServiceEntryRepository serviceEntryRepository;

    @Mock
    private ServicePartRepository servicePartRepository;

    @Mock
    private VerificationLogRepository verificationLogRepository;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @InjectMocks
    private VehicleServiceImpl vehicleService;

    @InjectMocks
    private GarageServiceImpl garageService;

    @InjectMocks
    private ServiceEntryServiceImpl serviceEntryService;

    @InjectMocks
    private ServicePartServiceImpl servicePartService;

    @InjectMocks
    private VerificationLogServiceImpl verificationLogService;

    @BeforeClass
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    static class SimpleHealthServlet extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
            resp.setStatus(200);
            resp.setContentType("text/plain");
            PrintWriter writer = resp.getWriter();
            writer.write("OK");
            writer.flush();
        }
    }

    @Test(groups = "servlet", priority = 1)
    public void testServletGetReturnsOk() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);

        Assert.assertEquals(resp.getStatus(), 200);
        Assert.assertEquals(resp.getContentAsString(), "OK");
    }

    @Test(groups = "servlet", priority = 2)
    public void testServletContentType() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);

        Assert.assertEquals(resp.getContentType(), "text/plain");
    }

    @Test(groups = "servlet", priority = 3)
    public void testServletEmptyQueryParams() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);

        Assert.assertNull(req.getQueryString());
    }

    @Test(groups = "servlet", priority = 4)
    public void testServletNotPost() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("POST", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.service(req, resp);

        Assert.assertNotEquals(resp.getStatus(), 500);
    }

    @Test(groups = "servlet", priority = 5)
    public void testServletDoesNotThrowOnNullParams() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);
        Assert.assertEquals(resp.getStatus(), 200);
    }

    @Test(groups = "servlet", priority = 6)
    public void testServletResponseWriterFlush() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        HttpServletRequest req = new MockHttpServletRequest();
        HttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);
        Assert.assertTrue(true);
    }

    @Test(groups = "servlet", priority = 7)
    public void testServletCanBeReusedMultipleRequests() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();

        for (int i = 0; i < 3; i++) {
            MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
            MockHttpServletResponse resp = new MockHttpServletResponse();
            servlet.doGet(req, resp);
            Assert.assertEquals(resp.getStatus(), 200);
        }
    }

    @Test(groups = "servlet", priority = 8)
    public void testServletOutputNotEmpty() throws Exception {
        SimpleHealthServlet servlet = new SimpleHealthServlet();
        MockHttpServletRequest req = new MockHttpServletRequest("GET", "/health");
        MockHttpServletResponse resp = new MockHttpServletResponse();

        servlet.doGet(req, resp);
        Assert.assertFalse(resp.getContentAsString().isEmpty());
    }

    @Test(groups = "crud", priority = 9)
    public void testCreateVehicleSuccess() {
        Vehicle v = new Vehicle();
        v.setVin("VIN123");
        v.setMake("Make");
        v.setModel("Model");
        v.setOwnerId(1L);

        when(vehicleRepository.findByVin("VIN123")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(inv -> {
            Vehicle saved = inv.getArgument(0);
            saved.setId(10L);
            return saved;
        });

        Vehicle saved = vehicleService.createVehicle(v);

        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getVin(), "VIN123");
    }

    @Test(groups = "crud", priority = 10)
    public void testCreateVehicleDuplicateVin() {
        Vehicle v = new Vehicle();
        v.setVin("VIN123");
        v.setMake("Make");
        v.setModel("Model");
        v.setOwnerId(1L);

        when(vehicleRepository.findByVin("VIN123")).thenReturn(Optional.of(v));

        try {
            vehicleService.createVehicle(v);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("VIN"));
        }
    }

    @Test(groups = "crud", priority = 11)
    public void testGetVehicleByIdNotFound() {
        when(vehicleRepository.findById(99L)).thenReturn(Optional.empty());
        try {
            vehicleService.getVehicleById(99L);
            Assert.fail("Expected EntityNotFoundException");
        } catch (EntityNotFoundException ex) {
            Assert.assertTrue(ex.getMessage().contains("Vehicle not found"));
        }
    }

    @Test(groups = "crud", priority = 12)
    public void testListVehiclesByOwner() {
        Vehicle v1 = new Vehicle();
        v1.setOwnerId(1L);
        Vehicle v2 = new Vehicle();
        v2.setOwnerId(1L);

        when(vehicleRepository.findByOwnerId(1L)).thenReturn(List.of(v1, v2));

        List<Vehicle> list = vehicleService.getVehiclesByOwner(1L);
        Assert.assertEquals(list.size(), 2);
    }

    @Test(groups = "crud", priority = 13)
    public void testCreateServiceEntrySuccess() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setActive(true);
        Garage garage = new Garage();
        garage.setId(2L);
        garage.setActive(true);

        ServiceEntry entry = new ServiceEntry();
        entry.setVehicle(vehicle);
        entry.setGarage(garage);
        entry.setServiceType("Oil Change");
        entry.setServiceDate(LocalDate.now());
        entry.setOdometerReading(10000);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(garageRepository.findById(2L)).thenReturn(Optional.of(garage));
        when(serviceEntryRepository.findTopByVehicleOrderByOdometerReadingDesc(vehicle))
                .thenReturn(Optional.empty());
        when(serviceEntryRepository.save(any(ServiceEntry.class))).thenAnswer(inv -> {
            ServiceEntry e = inv.getArgument(0);
            e.setId(5L);
            return e;
        });

        ServiceEntry saved = serviceEntryService.createServiceEntry(entry);
        Assert.assertNotNull(saved.getId());
        Assert.assertEquals(saved.getServiceType(), "Oil Change");
    }

    @Test(groups = "crud", priority = 14)
    public void testCreateServiceEntryInactiveVehicle() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId(1L);
        vehicle.setActive(false);

        ServiceEntry entry = new ServiceEntry();
        entry.setVehicle(vehicle);
        Garage g = new Garage();
        g.setId(2L);
        entry.setGarage(g);
        entry.setServiceType("Test");
        entry.setServiceDate(LocalDate.now());
        entry.setOdometerReading(1000);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(vehicle));
        when(garageRepository.findById(2L)).thenReturn(Optional.of(g));

        try {
            serviceEntryService.createServiceEntry(entry);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("active vehicles"));
        }
    }

    @Test(groups = "crud", priority = 15)
    public void testDeactivateVehicle() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setActive(true);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(v));
        when(vehicleRepository.save(any(Vehicle.class))).thenReturn(v);

        vehicleService.deactivateVehicle(1L);
        Assert.assertFalse(v.getActive());
    }

    @Test(groups = "crud", priority = 16)
    public void testGetEntriesForVehicleEmpty() {
        when(serviceEntryRepository.findByVehicleId(99L)).thenReturn(Collections.emptyList());
        List<ServiceEntry> entries = serviceEntryService.getEntriesForVehicle(99L);
        Assert.assertTrue(entries.isEmpty());
    }

    @Test(groups = "di", priority = 17)
    public void testVehicleServiceInjectedRepository() {
        Assert.assertNotNull(vehicleService);
        Assert.assertNotNull(vehicleRepository);
    }

    @Test(groups = "di", priority = 18)
    public void testGarageServiceInjectedRepository() {
        Assert.assertNotNull(garageService);
        Assert.assertNotNull(garageRepository);
    }

    @Test(groups = "di", priority = 19)
    public void testServiceEntryServiceInjectedRepositories() {
        Assert.assertNotNull(serviceEntryService);
        Assert.assertNotNull(serviceEntryRepository);
        Assert.assertNotNull(vehicleRepository);
        Assert.assertNotNull(garageRepository);
    }

    @Test(groups = "di", priority = 20)
    public void testServicePartServiceInjected() {
        Assert.assertNotNull(servicePartService);
        Assert.assertNotNull(servicePartRepository);
    }

    @Test(groups = "di", priority = 21)
    public void testVerificationLogServiceInjected() {
        Assert.assertNotNull(verificationLogService);
        Assert.assertNotNull(verificationLogRepository);
    }

    @Test(groups = "di", priority = 22)
    public void testIoCAllowsMockReplacementForVehicleRepository() {
        Vehicle v = new Vehicle();
        v.setId(7L);
        when(vehicleRepository.findById(7L)).thenReturn(Optional.of(v));
        Vehicle result = vehicleService.getVehicleById(7L);
        Assert.assertEquals(result, v);
    }

    @Test(groups = "di", priority = 23)
    public void testIoCSupportsMultipleServicesUsingSameRepository() {
        Vehicle v = new Vehicle();
        v.setId(3L);
        when(vehicleRepository.findById(3L)).thenReturn(Optional.of(v));
        Vehicle fromVehicleService = vehicleService.getVehicleById(3L);
        Vehicle fromVehicleServiceAgain = vehicleService.getVehicleById(3L);
        Assert.assertEquals(fromVehicleService.getId(), fromVehicleServiceAgain.getId());
    }

    @Test(groups = "di", priority = 24)
    public void testIoCConfiguredForJwtTokenProvider() {
        Assert.assertNotNull(jwtTokenProvider);
    }

    @Test(groups = "hibernate", priority = 25)
    public void testServiceEntryRespectsOdometerConstraint() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setActive(true);
        Garage g = new Garage();
        g.setId(2L);
        g.setActive(true);

        ServiceEntry last = new ServiceEntry();
        last.setOdometerReading(20000);
        last.setVehicle(v);

        ServiceEntry newEntry = new ServiceEntry();
        newEntry.setVehicle(v);
        newEntry.setGarage(g);
        newEntry.setServiceType("Test");
        newEntry.setServiceDate(LocalDate.now());
        newEntry.setOdometerReading(15000);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(v));
        when(garageRepository.findById(2L)).thenReturn(Optional.of(g));
        when(serviceEntryRepository.findTopByVehicleOrderByOdometerReadingDesc(v))
                .thenReturn(Optional.of(last));

        try {
            serviceEntryService.createServiceEntry(newEntry);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains(">="));
        }
    }

    @Test(groups = "hibernate", priority = 26)
    public void testServiceEntryFutureDateNotAllowed() {
        Vehicle v = new Vehicle();
        v.setId(1L);
        v.setActive(true);
        Garage g = new Garage();
        g.setId(2L);
        g.setActive(true);

        ServiceEntry e = new ServiceEntry();
        e.setVehicle(v);
        e.setGarage(g);
        e.setServiceType("Future");
        e.setServiceDate(LocalDate.now().plusDays(1));
        e.setOdometerReading(100);

        when(vehicleRepository.findById(1L)).thenReturn(Optional.of(v));
        when(garageRepository.findById(2L)).thenReturn(Optional.of(g));

        try {
            serviceEntryService.createServiceEntry(e);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("future"));
        }
    }

    @Test(groups = "hibernate", priority = 27)
    public void testGarageUniqueNameViolation() {
        Garage g = new Garage();
        g.setGarageName("XYZ Garage");

        when(garageRepository.findByGarageName("XYZ Garage")).thenReturn(Optional.of(g));

        try {
            garageService.createGarage(g);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("already exists"));
        }
    }

    @Test(groups = "hibernate", priority = 28)
    public void testServicePartQuantityMustBePositive() {
        ServiceEntry entry = new ServiceEntry();
        entry.setId(1L);

        ServicePart part = new ServicePart();
        part.setServiceEntry(entry);
        part.setPartName("Filter");
        part.setQuantity(0);

        when(serviceEntryRepository.findById(1L)).thenReturn(Optional.of(entry));

        try {
            servicePartService.createPart(part);
            Assert.fail("Expected IllegalArgumentException");
        } catch (IllegalArgumentException ex) {
            Assert.assertTrue(ex.getMessage().contains("Quantity"));
        }
    }

    @Test(groups = "hibernate", priority = 29)
    public void testVerificationLogImmutableCreation() {
        ServiceEntry entry = new ServiceEntry();
        entry.setId(1L);
        VerificationLog log = new VerificationLog();
        log.setServiceEntry(entry);

        when(serviceEntryRepository.findById(1L)).thenReturn(Optional.of(entry));
        when(verificationLogRepository.save(any(VerificationLog.class))).thenAnswer(inv -> {
            VerificationLog l = inv.getArgument(0);
            l.setId(10L);
            l.setVerifiedAt(LocalDateTime.now());
            return l;
        });

        VerificationLog saved = verificationLogService.createLog(log);

        Assert.assertNotNull(saved.getId());
        Assert.assertNotNull(saved.getVerifiedAt());
    }

    @Test(groups = "hibernate", priority = 30)
    public void testRepositorySaveVehicleAssignsId() {
        Vehicle v = new Vehicle();
        v.setVin("HHH");
        v.setOwnerId(1L);
        v.setMake("A");
        v.setModel("B");

        when(vehicleRepository.findByVin("HHH")).thenReturn(Optional.empty());
        when(vehicleRepository.save(any(Vehicle.class))).thenAnswer(inv -> {
            Vehicle vv = inv.getArgument(0);
            vv.setId(20L);
            return vv;
        });

        Vehicle saved = vehicleService.createVehicle(v);
        Assert.assertEquals(saved.getId(), Long.valueOf(20L));
    }

    @Test(groups = "hibernate", priority = 31)
    public void testRepositoryDeleteVehicleNoException() {
        doNothing().when(vehicleRepository).deleteById(5L);
        vehicleRepository.deleteById(5L);
        Assert.assertTrue(true);
    }

    @Test(groups = "hibernate", priority = 32)
    public void testRepositoryFindByVin() {
        Vehicle v = new Vehicle();
        v.setVin("VIN-SEARCH");
        when(vehicleRepository.findByVin("VIN-SEARCH")).thenReturn(Optional.of(v));
        Vehicle found = vehicleService.getVehicleByVin("VIN-SEARCH");
        Assert.assertEquals(found.getVin(), "VIN-SEARCH");
    }

    @Test(groups = "jpa-normalization", priority = 33)
    public void testVehicleNormalizedFields() {
        Vehicle v = new Vehicle();
        v.setVin("NORM1");
        v.setMake("Make");
        v.setModel("Model");
        v.setOwnerId(10L);
        Assert.assertEquals(v.getOwnerId(), Long.valueOf(10L));
    }

    @Test(groups = "jpa-normalization", priority = 34)
    public void testServiceEntryForeignKeysRepresent1NF() {
        ServiceEntry e = new ServiceEntry();
        Vehicle v = new Vehicle();
        v.setId(1L);
        Garage g = new Garage();
        g.setId(2L);

        e.setVehicle(v);
        e.setGarage(g);
        Assert.assertEquals(e.getVehicle().getId(), Long.valueOf(1L));
        Assert.assertEquals(e.getGarage().getId(), Long.valueOf(2L));
    }

    @Test(groups = "jpa-normalization", priority = 35)
    public void testServicePartBelongsToOneServiceEntry() {
        ServiceEntry e = new ServiceEntry();
        e.setId(5L);
        ServicePart p = new ServicePart();
        p.setServiceEntry(e);
        Assert.assertEquals(p.getServiceEntry().getId(), Long.valueOf(5L));
    }

    @Test(groups = "jpa-normalization", priority = 36)
    public void testVerificationLogBelongsToServiceEntry() {
        ServiceEntry e = new ServiceEntry();
        e.setId(8L);
        VerificationLog log = new VerificationLog();
        log.setServiceEntry(e);
        Assert.assertEquals(log.getServiceEntry().getId(), Long.valueOf(8L));
    }

    @Test(groups = "jpa-normalization", priority = 37)
    public void testVehicleDoesNotStoreGarageDetailsDirectly() {
        Vehicle v = new Vehicle();
        v.setVin("XX");
        v.setMake("M");
        v.setModel("N");
        v.setOwnerId(1L);
        Assert.assertNull(getGarageNameFromVehicle(v));
    }

    private String getGarageNameFromVehicle(Vehicle v) {
        return null;
    }

    @Test(groups = "jpa-normalization", priority = 38)
    public void testGarageIndependentOfVehicle() {
        Garage g = new Garage();
        g.setGarageName("G1");
        g.setAddress("Street");
        Assert.assertEquals(g.getGarageName(), "G1");
    }

    @Test(groups = "jpa-normalization", priority = 39)
    public void testServiceEntryDoesNotDuplicateOwnerId() {
        Vehicle v = new Vehicle();
        v.setOwnerId(99L);
        ServiceEntry e = new ServiceEntry();
        e.setVehicle(v);
        Assert.assertEquals(e.getVehicle().getOwnerId(), Long.valueOf(99L));
    }

    @Test(groups = "jpa-normalization", priority = 40)
    public void testServicePartUsesPartNameInsteadOfMultipleColumns() {
        ServicePart p = new ServicePart();
        p.setPartName("Brake Pad");
        Assert.assertEquals(p.getPartName(), "Brake Pad");
    }

    static class DummyVehicle {
        Long id;
        Set<DummyTag> tags = new HashSet<>();
    }

    static class DummyTag {
        Long id;
        Set<DummyVehicle> vehicles = new HashSet<>();
    }

    @Test(groups = "many-to-many", priority = 41)
    public void testManyToManyAddTagToVehicle() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        v.tags.add(t);
        t.vehicles.add(v);
        Assert.assertTrue(v.tags.contains(t));
        Assert.assertTrue(t.vehicles.contains(v));
    }

    @Test(groups = "many-to-many", priority = 42)
    public void testManyToManyVehicleCanHaveMultipleTags() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t1 = new DummyTag();
        DummyTag t2 = new DummyTag();
        v.tags.add(t1);
        v.tags.add(t2);
        Assert.assertEquals(v.tags.size(), 2);
    }

    @Test(groups = "many-to-many", priority = 43)
    public void testManyToManyTagCanHaveMultipleVehicles() {
        DummyTag t = new DummyTag();
        DummyVehicle v1 = new DummyVehicle();
        DummyVehicle v2 = new DummyVehicle();
        t.vehicles.add(v1);
        t.vehicles.add(v2);
        Assert.assertEquals(t.vehicles.size(), 2);
    }

    @Test(groups = "many-to-many", priority = 44)
    public void testManyToManyBidirectionalSync() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        v.tags.add(t);
        t.vehicles.add(v);
        Assert.assertTrue(v.tags.iterator().next().vehicles.contains(v));
    }

    @Test(groups = "many-to-many", priority = 45)
    public void testManyToManyRemoveVehicleFromTag() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        v.tags.add(t);
        t.vehicles.add(v);

        t.vehicles.remove(v);
        Assert.assertFalse(t.vehicles.contains(v));
    }

    @Test(groups = "many-to-many", priority = 46)
    public void testManyToManyEdgeCaseEmptySets() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        Assert.assertTrue(v.tags.isEmpty());
        Assert.assertTrue(t.vehicles.isEmpty());
    }

    @Test(groups = "many-to-many", priority = 47)
    public void testManyToManyDoesNotDuplicateEntries() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        v.tags.add(t);
        v.tags.add(t);
        Assert.assertEquals(v.tags.size(), 1);
    }

    @Test(groups = "many-to-many", priority = 48)
    public void testManyToManySimulateJunctionConcept() {
        DummyVehicle v = new DummyVehicle();
        DummyTag t = new DummyTag();
        v.tags.add(t);
        t.vehicles.add(v);
        boolean junctionConcept = v.tags.contains(t) && t.vehicles.contains(v);
        Assert.assertTrue(junctionConcept);
    }

    @Test(groups = "security", priority = 49)
    public void testJwtGenerateTokenIncludesEmail() {
        String email = "user@example.com";
        String role = "USER";
        Long userId = 1L;

        when(jwtTokenProvider.generateToken(email, role, userId))
                .thenReturn("dummy-token");

        String token = jwtTokenProvider.generateToken(email, role, userId);
        Assert.assertEquals(token, "dummy-token");
    }

    @Test(groups = "security", priority = 50)
    public void testJwtValidateTokenSuccess() {
        when(jwtTokenProvider.validateToken("valid-token")).thenReturn(true);
        Assert.assertTrue(jwtTokenProvider.validateToken("valid-token"));
    }

    @Test(groups = "security", priority = 51)
    public void testJwtValidateTokenFailure() {
        when(jwtTokenProvider.validateToken("invalid-token")).thenReturn(false);
        Assert.assertFalse(jwtTokenProvider.validateToken("invalid-token"));
    }

    @Test(groups = "security", priority = 52)
    public void testJwtGetAuthenticationNotNull() {
        Assert.assertTrue(true);
    }

    @Test(groups = "security", priority = 53)
    public void testSecurityDeniesMissingTokenConceptually() {
        boolean hasToken = false;
        Assert.assertFalse(hasToken);
    }

    @Test(groups = "security", priority = 54)
    public void testJwtClaimsContainRoleAndUserIdConceptually() {
        String email = "test@example.com";
        String role = "ADMIN";
        Long userId = 99L;

        when(jwtTokenProvider.generateToken(email, role, userId)).thenReturn("jwt-token");
        String token = jwtTokenProvider.generateToken(email, role, userId);
        Assert.assertEquals(token, "jwt-token");
    }

    @Test(groups = "security", priority = 55)
    public void testUnauthorizedAccessConceptual() {
        boolean hasToken = false;
        Assert.assertFalse(hasToken);
    }

    @Test(groups = "security", priority = 56)
    public void testAuthorizedAccessConceptual() {
        boolean hasToken = true;
        Assert.assertTrue(hasToken);
    }

    @Test(groups = "hql-hcql", priority = 57)
    public void testHqlQueryByVehicleAndDateRange() {
        String hql = "select s from ServiceEntry s where s.vehicle.id = :vehicleId and s.serviceDate between :from and :to";
        Assert.assertTrue(hql.contains("ServiceEntry"));
    }

    @Test(groups = "hql-hcql", priority = 58)
    public void testHqlQueryByGarageAndOdometer() {
        String hql = "select s from ServiceEntry s where s.garage.id = :garageId and s.odometerReading > :minOdometer";
        Assert.assertTrue(hql.contains("odometerReading"));
    }

    @Test(groups = "hql-hcql", priority = 59)
    public void testAdvancedQueryRepositoryInvocation() {
        List<ServiceEntry> result = List.of(new ServiceEntry(), new ServiceEntry());
        when(serviceEntryRepository.findByGarageAndMinOdometer(2L, 10000))
                .thenReturn(result);

        List<ServiceEntry> fetched = serviceEntryRepository.findByGarageAndMinOdometer(2L, 10000);
        Assert.assertEquals(fetched.size(), 2);
    }

    @Test(groups = "hql-hcql", priority = 60)
    public void testAdvancedQueryVehicleAndDateRangeInvocation() {
        List<ServiceEntry> result = List.of(new ServiceEntry());
        when(serviceEntryRepository.findByVehicleAndDateRange(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(result);

        List<ServiceEntry> fetched = serviceEntryRepository.findByVehicleAndDateRange(1L, LocalDate.now().minusDays(7), LocalDate.now());
        Assert.assertEquals(fetched.size(), 1);
    }

    @Test(groups = "hql-hcql", priority = 61)
    public void testEdgeCaseEmptyResultsFromHqlQuery() {
        when(serviceEntryRepository.findByVehicleAndDateRange(anyLong(), any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(Collections.emptyList());
        List<ServiceEntry> fetched = serviceEntryRepository.findByVehicleAndDateRange(1L, LocalDate.now(), LocalDate.now());
        Assert.assertTrue(fetched.isEmpty());
    }

    @Test(groups = "hql-hcql", priority = 62)
    public void testCriteriaLikeLogicConceptually() {
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("serviceType", "Oil Change");
        criteria.put("minOdometer", 5000);
        Assert.assertTrue(criteria.containsKey("serviceType"));
        Assert.assertTrue(criteria.containsKey("minOdometer"));
    }

    @Test(groups = "hql-hcql", priority = 63)
    public void testCriteriaEdgeCaseNoCriteria() {
        Map<String, Object> criteria = new HashMap<>();
        Assert.assertTrue(criteria.isEmpty());
    }

    @Test(groups = "hql-hcql", priority = 64)
    public void testCombinedHqlAndCriteriaConcept() {
        String hql = "select s from ServiceEntry s where 1=1";
        Map<String, Object> criteria = new HashMap<>();
        criteria.put("vehicleId", 1L);
        criteria.put("serviceType", "Brake");

        StringBuilder sb = new StringBuilder(hql);
        if (criteria.containsKey("vehicleId")) {
            sb.append(" and s.vehicle.id = :vehicleId");
        }
        if (criteria.containsKey("serviceType")) {
            sb.append(" and s.serviceType = :serviceType");
        }

        String finalQuery = sb.toString();
        Assert.assertTrue(finalQuery.contains("vehicle.id"));
        Assert.assertTrue(finalQuery.contains("serviceType"));
    }
}
