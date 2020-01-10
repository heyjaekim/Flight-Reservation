package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TicketService {
  @Autowired
  private JdbcTemplate jdbc;

  @Autowired
  private FlightService fs;

  private static final Logger log = LoggerFactory.getLogger(TicketService.class);
  
  private static final String COLUMNS = "reservation_id, ticket_id, leg_id, airline_id, "+
    "flight_id, departure_weekday, departure_date, price, booking_status";

  public Ticket save(Ticket ticket) {
    Flight f = fs.get(ticket.getAirline_id(), ticket.getFlight_id(), 
      ticket.getDeparture_weekday());

    // first, add ticket as waitlist
    jdbc.update(
      "INSERT INTO ticket("+COLUMNS+") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)", 
      ticket.getReservation_id(), 
      ticket.getTicket_id(),
      ticket.getLeg_id(),
      ticket.getAirline_id(),
      ticket.getFlight_id(),
      ticket.getDeparture_weekday(),
      ticket.getDeparture_date(),
      f.getPrice(),
      "waitlist" // all ticket start with waitlist
    );

    // then, confirm waitlist tickets
    confirm(ticket);

    return ticket;
  }

  public void confirm(Ticket ticket) {
    while(jdbc.update("update ticket inner join ("
       + "select t.reservation_id, t.ticket_id, t.leg_id "
       + "from ticket as t inner join reservation as r on t.reservation_id = r.reservation_id "
       + "where t.airline_id=? and t.flight_id=? and t.departure_date=? "
       +   "and t.booking_status='waitlist' and exists ("
       +     "select count(*) booked from ticket tk, reservation rs "
       +     "where tk.airline_id=? and tk.flight_id=? "
       +       "and tk.departure_date=? and tk.booking_status='confirmed' "
       +       "and tk.reservation_id=rs.reservation_id and rs.seat_class=r.seat_class "
       +     "having booked < (select capacity from aircraft_capacity ac, flight f "
       +        "where ac.seat_class=r.seat_class and f.aircraft_id=ac.aircraft_id "
       +        "and f.airline_id=? and f.flight_id=? and f.departure_weekday=?)) "        
       + "order by r.purchase_date, r.purchase_time limit 1) as s "
       + "on ticket.reservation_id = s.reservation_id "
       + "and ticket.ticket_id = s.ticket_id and ticket.leg_id = s.leg_id "
       + "set ticket.booking_status = 'confirmed'",
      ticket.getAirline_id(), 
      ticket.getFlight_id(),
      ticket.getDeparture_date(),
      ticket.getAirline_id(), 
      ticket.getFlight_id(),
      ticket.getDeparture_date(),
      ticket.getAirline_id(), 
      ticket.getFlight_id(),
      ticket.getDeparture_weekday()
    )>0) { log.info("confirmed one waitlist ticket"); }
  }
    
  public Iterable<Ticket> getByReservationId(String id) {
    return jdbc.query("SELECT "+COLUMNS+" FROM ticket "
      + "WHERE reservation_id=? ORDER BY departure_date, ticket_id, leg_id", 
      new Object[] { id }, // arguments as array
      (rs, rowNum) -> new Ticket(
        rs.getString("reservation_id"), 
        rs.getString("ticket_id"), 
        rs.getString("leg_id"), 
        rs.getString("airline_id"), 
        rs.getString("flight_id"), 
        rs.getString("departure_weekday"), 
        rs.getString("departure_date"), 
        rs.getString("price"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }

  public void delete(Ticket ticket) {
    // first, delete the ticket
    jdbc.update("DELETE FROM ticket WHERE reservation_id=? AND ticket_id=? AND leg_id=?", 
      new Object[] { ticket.getReservation_id(), ticket.getTicket_id(), ticket.getLeg_id() }
    );

    // then, confirm waitlist tickets
    confirm(ticket);

    // update status of other reservation since it may change one from waitlist to confirmed
    jdbc.update("UPDATE reservation r  SET r.booking_status='confirmed' WHERE r.booking_status='waitlist' AND NOT EXISTS (SELECT * FROM ticket t WHERE t.reservation_id=r.reservation_id AND t.airline_id=? AND t.flight_id=? AND t.departure_date=? AND t.booking_status='waitlist')",
    new Object[] { ticket.getAirline_id(), ticket.getFlight_id(), ticket.getDeparture_date() }
    );
  }

  public void deleteByReservationId(String id) {
    Iterable<Ticket> tickets = getByReservationId(id);
    for(Ticket ticket:tickets) {
      delete(ticket);
    }
  }
}