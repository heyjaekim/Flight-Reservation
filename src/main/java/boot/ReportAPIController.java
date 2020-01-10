package boot;

import java.util.*;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class ReportAPIController {

  // private static final Logger log = LoggerFactory.getLogger(ReportAPIController.class);

  @Autowired
  ReportService rs;

	@RequestMapping(value="/cr/report/waitlist", method=RequestMethod.GET)
  public List<Map<String, Object>> getWaitlist(Principal principal,
    @RequestParam(value="airline_id", required=true) String airline_id,
    @RequestParam(value="flight_id", required=true) String flight_id,
    @RequestParam(value="departure_date", required=true) String departure_date
  ) {
    return rs.getWaitlist(airline_id, flight_id, departure_date);
  }

	@RequestMapping(value="/admin/report/top_customers", method=RequestMethod.GET)
  public List<Map<String, Object>> getTopCustomers(Principal principal) {
    return rs.getTopCustomers();
  }

	@RequestMapping(value="/admin/report/top_flights", method=RequestMethod.GET)
  public List<Map<String, Object>> getTopFlights(Principal principal) {
    return rs.getTopFlights();
  }

  @RequestMapping(value="/admin/report/revenue_by_customer", method=RequestMethod.GET)
  public List<Map<String, Object>> getRevenueByCustomer(Principal principal,
    @RequestParam(value="username", required=true) String username
  ) {
    return rs.getRevenueByCustomer(username);
  }

  @RequestMapping(value="/admin/report/revenue_by_flight", method=RequestMethod.GET)
  public List<Map<String, Object>> getRevenueByFlight(Principal principal,
    @RequestParam(value="airline_id", required=true) String airline_id,
    @RequestParam(value="flight_id", required=true) String flight_id
  ) {
    return rs.getRevenueByFlight(airline_id, flight_id);
  }

  @RequestMapping(value="/admin/report/revenue_by_airline", method=RequestMethod.GET)
  public List<Map<String, Object>> getRevenueByAirline(Principal principal,
    @RequestParam(value="airline_id", required=true) String airline_id
  ) {
    return rs.getRevenueByAirline(airline_id);
  }

  @RequestMapping(value="/admin/report/revenue_by_month", method=RequestMethod.GET)
  public List<Map<String, Object>> getRevenueByMonth(Principal principal,
    @RequestParam(value="month", required=true) String month
  ) {
    return rs.getRevenueByMonth(month);
  }
}
