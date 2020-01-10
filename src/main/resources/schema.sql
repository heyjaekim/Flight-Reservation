DROP TABLE IF EXISTS ticket;
DROP TABLE IF EXISTS reservation;
DROP TABLE IF EXISTS flight;
DROP TABLE IF EXISTS aircraft_capacity;
DROP TABLE IF EXISTS aircraft;
DROP TABLE IF EXISTS airline;
DROP TABLE IF EXISTS airport;
DROP TABLE IF EXISTS role;
DROP TABLE IF EXISTS user;

CREATE TABLE IF NOT EXISTS user (
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	PRIMARY KEY(username)
);

CREATE TABLE IF NOT EXISTS role (
	username VARCHAR(255) NOT NULL,
	role VARCHAR(255) NOT NULL,
	PRIMARY KEY(username, role)
);

CREATE TABLE IF NOT EXISTS airport (
  airport_id CHAR(3) NOT NULL,
  airport_name varchar(255) NOT NULL,
  airport_tags varchar(512) NOT NULL,
  PRIMARY KEY (airport_id)
);

CREATE TABLE IF NOT EXISTS airline (
  airline_id CHAR(2) NOT NULL,
  airline_name VARCHAR(255) NOT NULL,
  PRIMARY KEY (airline_id)
);

CREATE TABLE IF NOT EXISTS aircraft (
  aircraft_id VARCHAR(32) NOT NULL,
  aircraft_model VARCHAR(32) NOT NULL,
  airline_id CHAR(2) NOT NULL,
  PRIMARY KEY (aircraft_id),
  FOREIGN KEY (airline_id) REFERENCES airline(airline_id)
);

CREATE TABLE IF NOT EXISTS aircraft_capacity (
  aircraft_id VARCHAR(32) NOT NULL,
  seat_class VARCHAR(32) NOT NULL,
  capacity INT NOT NULL,
  PRIMARY KEY (aircraft_id, seat_class),
  FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

CREATE TABLE IF NOT EXISTS flight (
  airline_id CHAR(2) NOT NULL,
  flight_id INT NOT NULL,
  departure_airport_id CHAR(3) NOT NULL,
  arrival_airport_id CHAR(3) NOT NULL,
  departure_weekday INT NOT NULL,
  arrival_weekday INT NOT NULL,
  departure_time TIME,
  arrival_time TIME,
  aircraft_id VARCHAR(32) NOT NULL,
  price DECIMAL(9, 2) NOT NULL,
  PRIMARY KEY (airline_id, flight_id, departure_weekday),
  FOREIGN KEY (departure_airport_id) REFERENCES airport(airport_id),
  FOREIGN KEY (arrival_airport_id) REFERENCES airport(airport_id),
  FOREIGN KEY (airline_id) REFERENCES airline(airline_id),
  FOREIGN KEY (aircraft_id) REFERENCES aircraft(aircraft_id)
);

CREATE TABLE IF NOT EXISTS reservation (
  reservation_id VARCHAR(255) NOT NULL,
  username VARCHAR(255) NOT NULL,
  departure_airport_id CHAR(3) NOT NULL,
  purchase_date DATE NOT NULL,
  purchase_time TIME NOT NULL,
  departure_date DATE NOT NULL,
  departure_time TIME NOT NULL,
  total_fare DECIMAL(11, 2) NOT NULL,
  fee DECIMAL(9, 2) NOT NULL,
  special_meal VARCHAR(32),
  seat_class VARCHAR(32) NOT NULL,
  booking_status VARCHAR(32) NOT NULL,
  PRIMARY KEY (reservation_id),
  FOREIGN KEY (departure_airport_id) REFERENCES airport(airport_id)
);

CREATE TABLE IF NOT EXISTS ticket (
  reservation_id VARCHAR(255) NOT NULL,
  ticket_id VARCHAR(255) NOT NULL,
  leg_id INT NOT NULL,
  airline_id CHAR(3) NOT NULL,
  flight_id INT NOT NULL,
  departure_weekday INT NOT NULL,
  departure_date DATE NOT NULL,
  price DECIMAL(11, 2) NOT NULL,
  booking_status VARCHAR(32) NOT NULL,
  PRIMARY KEY (reservation_id, ticket_id, leg_id),
  FOREIGN KEY (reservation_id) REFERENCES reservation(reservation_id),
  FOREIGN KEY (airline_id, flight_id, departure_weekday) REFERENCES flight(airline_id, flight_id, departure_weekday)
);