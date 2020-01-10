package boot;

public class Airline {
  private String airline_id;
  private String airline_name;

  public Airline() {}

  public Airline(String airline_id, String airline_name) {
    this.airline_id = airline_id;
    this.airline_name = airline_name;
  }

  // getter needed for JSON
  public String getAirline_id() {
    return this.airline_id;
}

  public String getAirline_name() {
    return this.airline_name;
  }
}
