package boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class AirlineAPIController {

  // private static final Logger log = LoggerFactory.getLogger(AppUserAPIController.class);

  @Autowired
  AirlineService as;

	@RequestMapping(value="/airline", method=RequestMethod.GET)
  public Iterable<Airline> getAirlines() {
    return as.getAirlines();
  }

	@RequestMapping(value="/cr/airline", method=RequestMethod.POST)
    public Airline create(@RequestBody Airline airline) {
    	return as.upsert(airline);
    }
  @RequestMapping(value="/cr/airline", method=RequestMethod.DELETE)
  public Airline delete(@RequestBody Airline airline) {
      as.delete(airline);
      return new Airline();
  }
}
