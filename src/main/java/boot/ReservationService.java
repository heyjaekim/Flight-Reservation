package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReservationService {
  @Autowired
  private JdbcTemplate jdbc;

  @Autowired
  private TicketService ts;

	private static final Logger log = LoggerFactory.getLogger(ReservationService.class);

  public Reservation save(Reservation reservation) {
    jdbc.update("INSERT INTO reservation(reservation_id, username, departure_airport_id, purchase_date, purchase_time, departure_date, departure_time, total_fare, fee, special_meal, seat_class, booking_status) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)", 
      reservation.getReservation_id(), 
      reservation.getUsername(),
      reservation.getDeparture_airport_id(),
      reservation.getPurchase_date(),
      reservation.getPurchase_time(),
      reservation.getDeparture_date(),
      reservation.getDeparture_time(),
      reservation.getTotal_fare(),
      reservation.getFee(),
      reservation.getSpecial_meal(),
      reservation.getSeat_class(),
      reservation.getBooking_status() // arguments
    );
    return reservation;
  }
    
  public Reservation getById(String id) {
    return (Reservation)jdbc.queryForObject("SELECT reservation_id, username, departure_airport_id, purchase_date, purchase_time, departure_date, departure_time, total_fare, fee, special_meal, seat_class, booking_status FROM reservation WHERE reservation_id=? ORDER BY purchase_date DESC, purchase_time DESC", 
      new Object[] { id }, // arguments as array
      (rs, rowNum) -> new Reservation(
        rs.getString("reservation_id"), 
        rs.getString("username"), 
        rs.getString("departure_airport_id"), 
        rs.getString("purchase_date"), 
        rs.getString("purchase_time"), 
        rs.getString("departure_date"), 
        rs.getString("departure_time"), 
        rs.getString("total_fare"), 
        rs.getString("fee"), 
        rs.getString("special_meal"),
        rs.getString("seat_class"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }

  public Iterable<Reservation> getByUsername(String username) {
    return jdbc.query("SELECT reservation_id, username, departure_airport_id, purchase_date, purchase_time, departure_date, departure_time, total_fare, fee, special_meal, seat_class, booking_status FROM reservation WHERE username=? ORDER BY purchase_date DESC, purchase_time DESC", 
      new Object[] { username }, // arguments as array
      (rs, rowNum) -> new Reservation(
        rs.getString("reservation_id"), 
        rs.getString("username"), 
        rs.getString("departure_airport_id"), 
        rs.getString("purchase_date"), 
        rs.getString("purchase_time"), 
        rs.getString("departure_date"), 
        rs.getString("departure_time"), 
        rs.getString("total_fare"), 
        rs.getString("fee"), 
        rs.getString("special_meal"),
        rs.getString("seat_class"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }

  public void delete(Reservation reservation) {
    ts.deleteByReservationId(reservation.getReservation_id());

    jdbc.update("DELETE FROM reservation WHERE reservation_id=?", 
      reservation.getReservation_id() // arguments
    );
  }

  public Reservation update(Reservation reservation) {
    jdbc.update("UPDATE reservation SET status=? WHERE reservation_id=?", 
      reservation.getReservation_id() // arguments
    );
    return reservation;
  }

  public Reservation confirm(Reservation reservation) {
    float total_fare = 0;
    String departure_date = "2050-12-31";
    Iterable<Ticket> tickets = ts.getByReservationId(reservation.getReservation_id());
    String status = "confirmed";
    for(Ticket t: tickets){
      if (t.getBooking_status().equals("waitlist")) 
        status="waitlist";
      total_fare += Float.parseFloat(t.getPrice());
      if (departure_date.compareTo(t.getDeparture_date()) > 0) {
        departure_date = t.getDeparture_date();
      }
    }

    jdbc.update("UPDATE reservation SET booking_status=?, departure_date=?, total_fare=?, fee=? WHERE reservation_id=?", 
      status,
      departure_date,
      total_fare,
      new Float(15.00),
      reservation.getReservation_id() // arguments
    );
    return reservation;
  }
  
  public Iterable<Reservation> searchByUsername(String username) {
    return jdbc.query("SELECT reservation_id, username, departure_airport_id, purchase_date, purchase_time, departure_date, departure_time, total_fare, fee, special_meal, seat_class, booking_status FROM reservation WHERE username=? ORDER BY purchase_date DESC, purchase_time DESC", 
      new Object[] { username }, // arguments as array
      (rs, rowNum) -> new Reservation(
        rs.getString("reservation_id"), 
        rs.getString("username"), 
        rs.getString("departure_airport_id"), 
        rs.getString("purchase_date"), 
        rs.getString("purchase_time"), 
        rs.getString("departure_date"), 
        rs.getString("departure_time"), 
        rs.getString("total_fare"), 
        rs.getString("fee"), 
        rs.getString("special_meal"),
        rs.getString("seat_class"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }

  public Iterable<Reservation> searchById(String id) {
    return jdbc.query("SELECT reservation_id, username, departure_airport_id, purchase_date, purchase_time, departure_date, departure_time, total_fare, fee, special_meal, seat_class, booking_status FROM reservation WHERE reservation_id=? ORDER BY purchase_date DESC, purchase_time DESC", 
      new Object[] { id }, // arguments as array
      (rs, rowNum) -> new Reservation(
        rs.getString("reservation_id"), 
        rs.getString("username"), 
        rs.getString("departure_airport_id"), 
        rs.getString("purchase_date"), 
        rs.getString("purchase_time"), 
        rs.getString("departure_date"), 
        rs.getString("departure_time"), 
        rs.getString("total_fare"), 
        rs.getString("fee"), 
        rs.getString("special_meal"),
        rs.getString("seat_class"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }

  public Iterable<Reservation> searchByFlight(
    String airline_id, String flight_id, String departure_date) {
    return jdbc.query("SELECT DISTINCT r.reservation_id, r.username, r.departure_airport_id, r.purchase_date, r.purchase_time, r.departure_date, r.departure_time, r.total_fare, r.fee, r.special_meal, r.seat_class, r.booking_status FROM reservation r, ticket t WHERE t.airline_id=? AND t.flight_id=? AND t.departure_date=? AND r.reservation_id=t.reservation_id ORDER BY r.purchase_date DESC, r.purchase_time DESC", 
      new Object[] { airline_id, flight_id, departure_date }, // arguments as array
      (rs, rowNum) -> new Reservation(
        rs.getString("reservation_id"), 
        rs.getString("username"), 
        rs.getString("departure_airport_id"), 
        rs.getString("purchase_date"), 
        rs.getString("purchase_time"), 
        rs.getString("departure_date"), 
        rs.getString("departure_time"), 
        rs.getString("total_fare"), 
        rs.getString("fee"), 
        rs.getString("special_meal"),
        rs.getString("seat_class"),
        rs.getString("booking_status")
      ) // row mapper 
    );
  }
}