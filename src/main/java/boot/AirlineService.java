package boot;

import java.util.ArrayList;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AirlineService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(AirlineService.class);

  public Airline save(Airline airline) {
    jdbc.update("INSERT INTO airline (airline_id, airline_name) VALUES (?, ?)", 
      airline.getAirline_id(), airline.getAirline_name() // arguments
    );
    return airline;
  }
   
  public Airline upsert(Airline airline) {
    jdbc.update("INSERT INTO airline(airline_id, airline_name) "
      + "VALUES(?, ?) ON DUPLICATE KEY UPDATE "
      + "airline_id=?, airline_name=?",
      airline.getAirline_id(), airline.getAirline_name(),
      airline.getAirline_id(), airline.getAirline_name()  // arguments
    );
    return airline;
  }

  public void delete(Airline airline) {
    jdbc.update("DELETE FROM airline WHERE airline_id=?", airline.getAirline_id());
  }
  
  public Iterable<Airline> getAirlines() {
    return jdbc.query("SELECT airline_id, airline_name FROM airline", 
      new Object[] { }, // arguments as array
      (rs, rowNum) -> new Airline(rs.getString("airline_id"), rs.getString("airline_name"))); // row mapper
  }
}