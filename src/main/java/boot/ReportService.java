package boot;

import java.util.*;
import java.sql.*;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(ReportService.class);

  public List<Map<String, Object>> getWaitlist(String airline_id, 
    String flight_id, String departure_date) {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT r.username, t.reservation_id, t.ticket_id, r.purchase_date, "
      +"r.purchase_time "
      +"FROM ticket t, reservation r "
      +"WHERE t.booking_status='waitlist' AND r.reservation_id=t.reservation_id "
      +"AND t.airline_id=? AND t.flight_id=? AND t.departure_date=?", 
      new Object[] { airline_id, flight_id, departure_date }
    );

    return results;
  }

  public List<Map<String, Object>> getTopCustomers() {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT r.username, sum(r.total_fare) as revenue "
      +"FROM reservation r "
      +"GROUP BY r.username "
      +"ORDER BY revenue DESC "
      +"LIMIT 3", 
      new Object[] {}
    );

    return results;
  }


  public List<Map<String, Object>> getTopFlights() {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT t.airline_id, t.flight_id, count(*) as tickets "
      +"FROM ticket t "
      +"GROUP BY t.airline_id, t.flight_id "
      +"ORDER BY tickets DESC "
      +"LIMIT 3", 
      new Object[] {}
    );
  
    return results;
  }

  public List<Map<String, Object>> getRevenueByCustomer(String username) {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT r.username, sum(r.total_fare) as revenue "
      +"FROM reservation r "
      +"WHERE r.username=?", 
      new Object[] { username }
    );

    return results;
  }

  public List<Map<String, Object>> getRevenueByFlight(
    String airline_id, String flight_id) {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT t.airline_id, t.flight_id, sum(price) as revenue "
      +"FROM ticket t "
      +"WHERE t.airline_id=? AND t.flight_id=?", 
      new Object[] { airline_id, flight_id }
    );

    return results;
  }

  public List<Map<String, Object>> getRevenueByAirline(String airline_id) {
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT t.airline_id, sum(price) as revenue "
      +"FROM ticket t "
      +"WHERE t.airline_id=?", 
      new Object[] { airline_id }
    );

    return results;
  }

  public List<Map<String, Object>> getRevenueByMonth(String month) {
    String start = month+"-01";
    String end = month+"-31";
    log.info("start: "+start);
    log.info("end: "+end);
    List<Map<String, Object>> results = jdbc.queryForList(
      "SELECT sum(r.total_fare) as revenue "
      +"FROM reservation r "
      +"WHERE r.purchase_date>=? AND r.purchase_date<=?", 
      new Object[] { start, end }
    );

    return results;
  }
}