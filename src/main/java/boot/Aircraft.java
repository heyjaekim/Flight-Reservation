package boot;

import java.util.ArrayList;

public class Aircraft {
  private String aircraft_id;
  private String airline_id;
  private String aircraft_model;
  private int economy_seats;
  private int business_seats;
  private int first_seats;

  public Aircraft() {
  }

  public Aircraft(String aircraft_id, String airline_id, String aircraft_model) {
    this.aircraft_id = aircraft_id;
    this.airline_id = airline_id;
    this.aircraft_model = aircraft_model;
  }

  // getter needed for JSON
  public String getAirline_id() {
    return this.airline_id;
  }

  public String getAircraft_id() {
    return this.aircraft_id;
  }

  public String getAircraft_model() {
    return this.aircraft_model;
  }

  public int getEconomy_seats() {
    return this.economy_seats;
  }

  public int getBusiness_seats() {
    return this.business_seats;
  }

  public int getFirst_seats() {
    return this.first_seats;
  }

  public void setEconomy_seats(int i) {
    this.economy_seats = i;
  }

  public void setBusiness_seats(int i) {
    this.business_seats = i;
  }

  public void setFirst_seats(int i) {
    this.first_seats = i;
  }
}
