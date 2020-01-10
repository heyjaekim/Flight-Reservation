package boot;

import java.util.ArrayList;
import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class CapacityService {
  @Autowired
  private JdbcTemplate jdbc;

	private static final Logger log = LoggerFactory.getLogger(CapacityService.class);

  public Capacity save(Capacity capacity) {
    jdbc.update("INSERT INTO aircraft_capacity (aircraft_id, seat_class, capacity) VALUES (?, ?, ?)", 
      capacity.getAircraft_id(), capacity.getSeat_class(), capacity.getCapacity() // arguments
    );
    return capacity;
  }
    
  public void delete(Capacity capacity) {
    jdbc.update("DELETE FROM aircraft_capacity WHERE aircraft_id=? AND seat_class=?", 
      capacity.getAircraft_id(), capacity.getSeat_class());
  }

  public Capacity upsert(Capacity capacity) {
    jdbc.update("INSERT INTO aircraft_capacity(aircraft_id, seat_class, capacity) "
      + "VALUES(?, ?, ?) ON DUPLICATE KEY UPDATE "
      + "aircraft_id=?, seat_class=?, capacity=?",
      capacity.getAircraft_id(), capacity.getSeat_class(), capacity.getCapacity(),
      capacity.getAircraft_id(), capacity.getSeat_class(), capacity.getCapacity()  // arguments
    );
    return capacity;
  }

  public void delete(Aircraft aircraft) {
    jdbc.update("DELETE FROM aircraft_capacity WHERE aircraft_id=?", 
    aircraft.getAircraft_id());
  }

  public Iterable<Capacity> getCapacities(Aircraft aircraft) {
    if (aircraft != null) {
      return jdbc.query("SELECT aircraft_id, seat_class, capacity FROM aircraft_capacity where aircraft_id=?", 
        new Object[] { aircraft.getAircraft_id() }, // arguments as array
        (rs, rowNum) -> new Capacity(rs.getString("aircraft_id"), rs.getString("seat_class"), rs.getInt("capacity"))); // row mapper
    } else {
      return new ArrayList<Capacity>();
    }
  }
}