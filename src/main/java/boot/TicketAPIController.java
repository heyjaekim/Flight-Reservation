package boot;

import java.util.*;
import java.security.Principal;
import org.slf4j.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.*;

// needed for cross domain Ajax
@CrossOrigin(origins={"http://codepen.io", "https://codepen.io", "https://cdpn.io"})
@RestController
public class TicketAPIController {

  private static final Logger log = LoggerFactory.getLogger(TicketAPIController.class);

  @Autowired
  ReservationService rs;
  @Autowired
  TicketService ts;
  @Autowired
  RoleService roleService;

	@RequestMapping(value="/ticket", method=RequestMethod.POST)
  public Ticket createTicket(
    Principal principal,
    @RequestBody Ticket ticket
  ) {
    String username = principal.getName();
    Reservation reservation = rs.getById(ticket.getReservation_id());
    if (reservation != null && reservation.username.equals(username)) {
      ts.save(ticket);
    }
    return ticket;
  }

	@RequestMapping(value="/tickets/{id}", method=RequestMethod.GET)
  public Iterable<Ticket> getTickets(
    Principal principal,
    @PathVariable("id") String id
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null && reservation.username.equals(principal.getName())) {
      return ts.getByReservationId(id);
    }
    return new ArrayList<Ticket>();
  }

	@RequestMapping(value="/tickets/{id}", method=RequestMethod.DELETE)
  public void deleteTickets(
    Principal principal,
    @PathVariable("id") String id
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null && reservation.username.equals(principal.getName())) {
      ts.deleteByReservationId(id);
    }
  }

	@RequestMapping(value="/cr/tickets/{id}", method=RequestMethod.GET)
  public Iterable<Ticket> getTicketsByCR(
    Principal principal,
    @PathVariable("id") String id
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null) {
      return ts.getByReservationId(id);
    }
    return new ArrayList<Ticket>();
  }

	@RequestMapping(value="/admin/tickets/{id}", method=RequestMethod.GET)
  public Iterable<Ticket> getTicketsByAdmin(
    Principal principal,
    @PathVariable("id") String id
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null) {
      return ts.getByReservationId(id);
    }
    return new ArrayList<Ticket>();
  }

  @RequestMapping(value="/cr/tickets/{id}", method=RequestMethod.DELETE)
  public void deleteTicketsByCR(
    Principal principal,
    @PathVariable("id") String id
  ) {
    Reservation reservation = rs.getById(id);
    if (reservation != null) {
      ts.deleteByReservationId(id);
    }
  }

  @RequestMapping(value="/cr/ticket", method=RequestMethod.POST)
  public Ticket createTicketByCR(
    Principal principal,
    @RequestBody Ticket ticket
  ) {
    ts.save(ticket);
    return ticket;
  }
}
