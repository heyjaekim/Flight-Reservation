package boot;

public class Ticket {
  String reservation_id;
  String ticket_id;
  String leg_id;
  String airline_id;
  String flight_id;
  String departure_weekday;
  String departure_date;
  String price;
  String booking_status;

  public Ticket() {
  }

  public Ticket(
    String reservation_id,
    String ticket_id,
    String leg_id,
    String airline_id,
    String flight_id,
    String departure_weekday,
    String departure_date,
    String price,
    String booking_status
    ) {
    this.reservation_id = reservation_id;
    this.ticket_id = ticket_id;
    this.leg_id = leg_id;
    this.airline_id = airline_id;
    this.flight_id = flight_id;
    this.departure_weekday = departure_weekday;
    this.departure_date = departure_date;
    this.price = price;
    this.booking_status = booking_status;
  }

  public String getReservation_id() {
      return this.reservation_id;
  }
  public String getTicket_id() {
      return this.ticket_id;
  }
  public String getLeg_id() {
    return this.leg_id;
  }
  public String getAirline_id() {
    return this.airline_id;
  }
  public String getFlight_id() {
    return this.flight_id;
  }
  public String getDeparture_weekday() {
    return this.departure_weekday;
  }
  public String getDeparture_date() {
    return this.departure_date;
  }
  public String getPrice() {
    return this.price;
  }
  public String getBooking_status() {
    return this.booking_status;
  }
}
