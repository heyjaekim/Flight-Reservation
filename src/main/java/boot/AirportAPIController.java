package boot;

import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class AirportAPIController {

    // private static final Logger log = LoggerFactory.getLogger(AirportAPIController.class);

    @Autowired
    AirportService as;

	@RequestMapping(value="/airport", method=RequestMethod.GET)
    public Iterable<Airport> get(Principal principal) {
        return as.get();
    }

	@RequestMapping(value="/cr/airport", method=RequestMethod.POST)
    public Airport create(@RequestBody Airport airport) {
    	return as.upsert(airport);
    }

	@RequestMapping(value="/cr/airport", method=RequestMethod.PUT)
    public Airport update(@RequestBody Airport airport) {
        return as.upsert(airport);
    }

    @RequestMapping(value="/cr/airport", method=RequestMethod.DELETE)
    public Airport delete(@RequestBody Airport airport) {
        as.delete(airport);
        return new Airport();
    }

}
