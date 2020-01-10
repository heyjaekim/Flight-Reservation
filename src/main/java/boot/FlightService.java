package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class FlightService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(AirlineService.class);

  public Flight get(String airline_id, String flight_id, String departure_weekday) {
    return jdbc.queryForObject("SELECT airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price FROM flight WHERE airline_id=? AND flight_id=? AND departure_weekday=?", 
      new Object[] { airline_id, flight_id, departure_weekday }, // arguments as array
      (rs, rowNum) -> new Flight(
        rs.getString("flight_id"),
        rs.getString("airline_id"),
        rs.getString("departure_airport_id"),
        rs.getString("arrival_airport_id"),
        rs.getString("departure_weekday"),
        rs.getString("arrival_weekday"),
        rs.getString("departure_time"),
        rs.getString("arrival_time"),
        rs.getString("aircraft_id"),
        rs.getString("price")
    )); // row mapper
  }

  public Iterable<Flight> getFlights() {
    return jdbc.query("SELECT airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price FROM flight", 
      new Object[] { }, // arguments as array
      (rs, rowNum) -> new Flight(
        rs.getString("flight_id"),
        rs.getString("airline_id"),
        rs.getString("departure_airport_id"),
        rs.getString("arrival_airport_id"),
        rs.getString("departure_weekday"),
        rs.getString("arrival_weekday"),
        rs.getString("departure_time"),
        rs.getString("arrival_time"),
        rs.getString("aircraft_id"),
        rs.getString("price")
    )); // row mapper
  }

  public Flight upsert(Flight f) {
    jdbc.update("INSERT INTO flight(airline_id, flight_id, departure_airport_id, departure_weekday, departure_time, arrival_airport_id, arrival_weekday, arrival_time, aircraft_id, price) "
      + "VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE "
      + "airline_id=?, flight_id=?, departure_airport_id=?, departure_weekday=?, departure_time=?, arrival_airport_id=?, arrival_weekday=?, arrival_time=?, aircraft_id=?, price=?",
      f.getAirline_id(), f.getFlight_id(), f.getDeparture_airport_id(), f.getDeparture_weekday(), f.getDeparture_time(), f.getArrival_airport_id(), f.getArrival_weekday(), f.getArrival_time(), f.getAircraft_id(), f.getPrice(),
      f.getAirline_id(), f.getFlight_id(), f.getDeparture_airport_id(), f.getDeparture_weekday(), f.getDeparture_time(), f.getArrival_airport_id(), f.getArrival_weekday(), f.getArrival_time(), f.getAircraft_id(), f.getPrice()
    );
    return f;
  }
  
  public void delete(Flight f) {
    jdbc.update("DELETE FROM flight WHERE airline_id=? AND flight_id=? AND departure_weekday=?",
      f.getAirline_id(), f.getFlight_id(), f.getDeparture_weekday());
  }

}