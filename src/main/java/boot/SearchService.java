package boot;

import org.slf4j.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class SearchService {
  @Autowired
  private JdbcTemplate jdbc;

  private static final Logger log = LoggerFactory.getLogger(SearchService.class);
  
  private String query = 
    "SELECT CONCAT(airline_id, '-', flight_id, '-', departure_weekday) FROM flight WHERE departure_airport_id=? AND arrival_airport_id=? AND departure_weekday=WEEKDAY(?) " 
    + "UNION SELECT CONCAT(CONCAT(f1.airline_id, '-', f1.flight_id, '-', f1.departure_weekday), ',', CONCAT(f2.airline_id, '-', f2.flight_id, '-', f2.departure_weekday)) FROM flight f1, flight f2 WHERE f1.departure_airport_id=? AND f2.arrival_airport_id=? AND f1.arrival_airport_id!=? AND f2.departure_airport_id!=? AND f1.departure_weekday=WEEKDAY(?) AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) < ? AND f2.departure_airport_id=f1.arrival_airport_id " 
    + "UNION SELECT CONCAT(CONCAT(f1.airline_id, '-', f1.flight_id, '-', f1.departure_weekday), ',', CONCAT(f2.airline_id, '-', f2.flight_id, '-', f2.departure_weekday), ',', CONCAT(f3.airline_id, '-', f3.flight_id, '-', f3.departure_weekday)) FROM flight f1, flight f2, flight f3 WHERE f1.departure_airport_id=? AND f3.arrival_airport_id=? AND f1.arrival_airport_id!=? AND f2.arrival_airport_id!=? AND f2.departure_airport_id!=? AND f3.departure_airport_id!=? AND f1.departure_weekday=WEEKDAY(?) AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) < ? AND ((TIME_TO_SEC(f3.departure_time)-TIME_TO_SEC(f2.arrival_time))/60+MOD((f3.departure_weekday-f2.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f3.departure_time)-TIME_TO_SEC(f2.arrival_time))/60+MOD((f3.departure_weekday-f2.arrival_weekday)+7, 7)*24*60) < ? AND f2.departure_airport_id=f1.arrival_airport_id AND f3.departure_airport_id=f2.arrival_airport_id " 
    + "UNION SELECT CONCAT(CONCAT(f1.airline_id, '-', f1.flight_id, '-', f1.departure_weekday), ',',CONCAT(f2.airline_id, '-', f2.flight_id, '-', f2.departure_weekday), ',', CONCAT(f3.airline_id, '-', f3.flight_id, '-', f3.departure_weekday), ',', CONCAT(f4.airline_id, '-', f4.flight_id, '-', f4.departure_weekday)) FROM flight f1, flight f2, flight f3, flight f4 WHERE f1.departure_airport_id=? AND f4.arrival_airport_id=? AND f1.arrival_airport_id!=? AND f2.arrival_airport_id!=? AND f3.arrival_airport_id!=? AND f2.departure_airport_id!=? AND f3.departure_airport_id!=? AND f4.departure_airport_id!=? AND f1.departure_weekday=WEEKDAY(?) AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f2.departure_time)-TIME_TO_SEC(f1.arrival_time))/60+MOD((f2.departure_weekday-f1.arrival_weekday)+7, 7)*24*60) < ? AND ((TIME_TO_SEC(f3.departure_time)-TIME_TO_SEC(f2.arrival_time))/60+MOD((f3.departure_weekday-f2.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f3.departure_time)-TIME_TO_SEC(f2.arrival_time))/60+MOD((f3.departure_weekday-f2.arrival_weekday)+7, 7)*24*60) < ? AND ((TIME_TO_SEC(f4.departure_time)-TIME_TO_SEC(f3.arrival_time))/60+MOD((f4.departure_weekday-f3.arrival_weekday)+7, 7)*24*60) > ? AND ((TIME_TO_SEC(f4.departure_time)-TIME_TO_SEC(f3.arrival_time))/60+MOD((f4.departure_weekday-f3.arrival_weekday)+7, 7)*24*60) < ? AND f2.departure_airport_id=f1.arrival_airport_id AND f3.departure_airport_id=f2.arrival_airport_id AND f4.departure_airport_id=f3.arrival_airport_id";

  public Iterable<String> search(
    String da, String aa, String dd, Integer nl, Integer xl) {
    // da - departure airport
    // aa - arrival airport
    // dd - departure weekday
    // nl - min layover minutes
    // xl - max layover minutes

    return jdbc.query(query, 
      new Object[] { 
        da, aa, dd, 
        da, aa, aa, da, dd, nl, xl, 
        da, aa, aa, aa, da, da, dd, nl, xl, nl, xl, 
        da, aa, aa, aa, aa, da, da, da, dd, nl, xl, nl, xl, nl, xl
      }, // arguments as array
      (rs, rowNum) -> new String(rs.getString(1))); // row mapper
  }
}