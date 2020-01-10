package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AircraftService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(AircraftService.class);

  public Aircraft save(Aircraft aircraft) {
    jdbc.update("INSERT INTO aircraft (aircraft_id, airline_id, aircraft_model) VALUES (?, ?, ?)", 
      aircraft.getAircraft_id(),aircraft.getAirline_id(),aircraft.getAircraft_model() // arguments
    );
    return aircraft;
  }
   
  public Aircraft upsert(Aircraft aircraft) {
    jdbc.update("INSERT INTO aircraft(aircraft_id, airline_id, aircraft_model) "
      + "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE "
      + "aircraft_id=?, airline_id=?, aircraft_model=?",
      aircraft.getAircraft_id(), aircraft.getAirline_id(), aircraft.getAircraft_model(),
      aircraft.getAircraft_id(), aircraft.getAirline_id(), aircraft.getAircraft_model()  // arguments
    );
    return aircraft;
  }

  public void delete(Aircraft aircraft) {
    jdbc.update("DELETE FROM aircraft_capacity WHERE aircraft_id=?", aircraft.getAircraft_id());
    jdbc.update("DELETE FROM aircraft WHERE aircraft_id=?", aircraft.getAircraft_id());
  }
  
  public Iterable<Aircraft> getAircrafts() {
    return jdbc.query("SELECT aircraft_id, airline_id, aircraft_model FROM aircraft", 
      new Object[] { }, // arguments as array
      (rs, rowNum) -> new Aircraft(rs.getString("aircraft_id"), rs.getString("airline_id"), rs.getString("aircraft_model"))); // row mapper
  }
}