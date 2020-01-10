package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class AirportService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(AirportService.class);

  public Airport save(Airport airport) {
    jdbc.update("INSERT INTO airport (airport_id, airport_name, airport_tags) VALUES (?, ?, ?)", 
    airport.getAirport_id(), airport.getAirport_name(), airport.getAirport_tags() // arguments
    );
    return airport;
  }
    
  public Iterable<Airport> get() {
    return jdbc.query("SELECT airport_id, airport_name, airport_tags FROM airport", 
      new Object[] { }, // arguments as array
      (rs, rowNum) -> new Airport(
        rs.getString("airport_id"), 
        rs.getString("airport_name"),
        rs.getString("airport_tags")
      ) // row mapper 
    );
  }

  public Airport upsert(Airport airport) {
    jdbc.update("INSERT INTO airport(airport_id, airport_name, airport_tags) "
      + "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE "
      + "airport_id=?, airport_name=?, airport_tags=?",
      airport.getAirport_id(), airport.getAirport_name(), airport.getAirport_tags(), 
      airport.getAirport_id(), airport.getAirport_name(), airport.getAirport_tags()  // arguments
    );
    return airport;
  }
  
  public void delete(Airport airport) {
    jdbc.update("DELETE FROM airport WHERE airport_id=?", airport.getAirport_id());
  }
  
}