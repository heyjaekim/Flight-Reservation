package boot;

import java.util.*;
import java.security.Principal;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class ReservationAPIController {

  private static final Logger log = LoggerFactory.getLogger(ReservationAPIController.class);

  @Autowired
  ReservationService rs;

  @Autowired
  RoleService roleService;

	@RequestMapping(value="/reservation", method=RequestMethod.POST)
  public Reservation createReservation(
    @RequestBody Reservation reservation,
    Principal principal
  ) {
    String username = principal.getName();
    if (principal != null && username != null ) {
    //   if (!roleService.isCustomerRep(principal)) {
        reservation.username = username;
      // }
      rs.save(reservation);
    }
    return reservation;
  }

	@RequestMapping(value="/reservation", method=RequestMethod.GET)
  public Iterable<Reservation> getReservations(Principal principal) {
    return rs.getByUsername(principal.getName());
  }

	@RequestMapping(value="/reservation/{id}", method=RequestMethod.GET)
  public Reservation getReservation(
    @PathVariable("id") String id, 
    Principal principal
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null && reservation.username.equals(principal.getName())) {
      return reservation;
    }
    return new Reservation();
  }

	@RequestMapping(value="/reservation/{id}", method=RequestMethod.DELETE)
  public Reservation deleteReservation(
    @PathVariable("id") String id, 
    Principal principal
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null && reservation.username.equals(principal.getName())) {
      rs.delete(reservation);
      return reservation;
    } else {
      log.info("try to delete other's reservation");
    }
    return new Reservation();
  }

  @RequestMapping(value="/reservation", method=RequestMethod.PUT)
  public Reservation updateReservation(
    @RequestBody Reservation reservation, 
    Principal principal
  ) {
    Reservation r = rs.getById(reservation.getReservation_id());
    if (r != null && r.username.equals(principal.getName())) {
      r = rs.confirm(reservation);
      r = rs.getById(reservation.getReservation_id());
      return r;
    } else {
      log.info("try to update other's reservation");
    }
    return new Reservation();
  }

  @RequestMapping(value="/cr/reservation", method=RequestMethod.GET)
  public Iterable<Reservation> searchReservationByCR(
    @RequestParam(value="username", required=false) String username,
    @RequestParam(value="id", required=false) String id
  ) {
    if (id != null) {
      log.info("id: " + id);
      return rs.searchById(id);
    }
    if (username != null) {
      log.info("username: " + username);
      return rs.searchByUsername(username);
    }
    return null;
  }

  @RequestMapping(value="/admin/reservation", method=RequestMethod.GET)
  public Iterable<Reservation> searchReservationByAdmin(
    @RequestParam(value="username", required=false) String username,
    @RequestParam(value="airline_id", required=false) String airline_id,
    @RequestParam(value="flight_id", required=false) String flight_id,
    @RequestParam(value="departure_date", required=false) String departure_date,
    @RequestParam(value="id", required=false) String id
  ) {
    if (id != null) {
      log.info("id: " + id);
      return rs.searchById(id);
    } else if (username != null) {
      log.info("username: " + username);
      return rs.searchByUsername(username);
    } else if (airline_id !=null) {
      log.info("airline_id: " + airline_id);
      return rs.searchByFlight(airline_id, flight_id, departure_date);
    }
    return null;
  }

  @RequestMapping(value="/cr/reservation", method=RequestMethod.POST)
  public Reservation createReservationByCR(@RequestBody Reservation reservation) {
    return rs.save(reservation);
  }

	@RequestMapping(value="/cr/reservation", method=RequestMethod.PUT)
  public Reservation updateReservationByCR(@RequestBody Reservation reservation) {
    rs.confirm(reservation);
    return rs.getById(reservation.getReservation_id());
  }

	@RequestMapping(value="/cr/reservation/{id}", method=RequestMethod.DELETE)
  public Reservation deleteReservationByCR(
    @PathVariable("id") String id, 
    Principal principal
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null) {
      rs.delete(reservation);
    } else {
      log.info("try to delete other's reservation");
    }
    return reservation;
  }
}
