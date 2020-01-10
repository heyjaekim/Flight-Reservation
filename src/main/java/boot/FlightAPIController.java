package boot;

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class FlightAPIController {

  // private static final Logger log = LoggerFactory.getLogger(FlightAPIController.class);

  @Autowired
  FlightService fs;

	@RequestMapping(value="/flight", method=RequestMethod.GET)
  public Iterable<Flight> getFlights() {
    return fs.getFlights();
  }

	@RequestMapping(value="/cr/flight", method=RequestMethod.POST)
    public Flight create(@RequestBody Flight f) {
    	return fs.upsert(f);
    }

	@RequestMapping(value="/cr/flight", method=RequestMethod.PUT)
    public Flight update(@RequestBody Flight f) {
        return fs.upsert(f);
    }

    @RequestMapping(value="/cr/flight", method=RequestMethod.DELETE)
    public Flight delete(@RequestBody Flight f) {
        fs.delete(f);
        return new Flight();
    }

}
