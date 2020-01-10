package boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class AircraftAPIController {

  // private static final Logger log = LoggerFactory.getLogger(AppUserAPIController.class);

  @Autowired
  AircraftService as;

  @Autowired
  CapacityService cs;
 
	@RequestMapping(value="/aircraft", method=RequestMethod.GET)
  public Iterable<Aircraft> getAircrafts() {
    Iterable<Aircraft> aircrafts = as.getAircrafts();
    for(Aircraft a:aircrafts) {
      for(Capacity c: cs.getCapacities(a)) {
        if (c.getSeat_class().equals("economy")) a.setEconomy_seats(c.getCapacity());
        if (c.getSeat_class().equals("business")) a.setBusiness_seats(c.getCapacity());
        if (c.getSeat_class().equals("first")) a.setFirst_seats(c.getCapacity());
      }
    }
    return aircrafts;
  }

	@RequestMapping(value="/cr/aircraft", method=RequestMethod.POST)
    public Aircraft create(@RequestBody Aircraft aircraft) {
      as.upsert(aircraft);
      cs.upsert(new Capacity(aircraft.getAircraft_id(), "economy", aircraft.getEconomy_seats()));
      cs.upsert(new Capacity(aircraft.getAircraft_id(), "business", aircraft.getBusiness_seats()));
      cs.upsert(new Capacity(aircraft.getAircraft_id(), "first", aircraft.getFirst_seats()));
      return as.upsert(aircraft);
  }

  @RequestMapping(value="/cr/aircraft", method=RequestMethod.DELETE)
  public Aircraft delete(@RequestBody Aircraft aircraft) {
      as.delete(aircraft);
      return new Aircraft();
  }
}
