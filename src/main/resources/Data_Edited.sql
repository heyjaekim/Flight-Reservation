INSERT IGNORE INTO user (username, password) VALUES ('admin', 'admin');
INSERT IGNORE INTO user (username, password) VALUES ('user', 'user');
INSERT IGNORE INTO user (username, password) VALUES ('rep', 'rep');
INSERT IGNORE INTO user (username, password) VALUES ('Mark', 'Mark');
INSERT IGNORE INTO user (username, password) VALUES ('Alex', 'Alex');
INSERT IGNORE INTO user (username, password) VALUES ('John', 'John');
INSERT IGNORE INTO user (username, password) VALUES ('Asher', 'Asher');
INSERT IGNORE INTO user (username, password) VALUES ('Esta', 'Esta');
INSERT IGNORE INTO user (username, password) VALUES ('Diana', 'Diana');
INSERT IGNORE INTO user (username, password) VALUES ('Mira', 'Mira');
INSERT IGNORE INTO user (username, password) VALUES ('Stephanie', 'Stephanie');

INSERT IGNORE INTO role (username, role) VALUES ('admin', 'admin');
INSERT IGNORE INTO role (username, role) VALUES ('rep', 'customer_representative');

INSERT IGNORE INTO airline (airline_id, airline_name) VALUES ('UA', 'United Airlines');
INSERT IGNORE INTO airline (airline_id, airline_name) VALUES ('DL', 'Delta Airlines');
INSERT IGNORE INTO airline (airline_id, airline_name) VALUES ('AA', 'American Airlines');
INSERT IGNORE INTO airline (airline_id, airline_name) VALUES ('B6', 'JetBlue Airways');
INSERT IGNORE INTO airline (airline_id, airline_name) VALUES ('F9', 'Frontier Airlines');

INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N14001', 'UA', 'Boeing 787-100 Dreamliner');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N501DN', 'DL', 'Airbus350-900');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N948UW', 'AA', 'Embraer E190AR');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N33286', 'UA', 'Boeing 737-824');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N301DV', 'DL', 'Airbus A321-211');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N396AN', 'AA', 'Boeing 767-323');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N14008', 'UA', 'Boeing 787-100 Dreamliner');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N522DN', 'DL', 'Airbus350-900');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N928UW', 'AA', 'Embraer E190AR');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N34661', 'UA', 'Boeing 737-824');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N12855', 'B6', 'Embraer E190AR');
INSERT IGNORE INTO aircraft (aircraft_id, airline_id, aircraft_model) 
VALUES ('N22525', 'F9', 'Boeing 737-824');

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N22525', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N22525', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N22525', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N12855', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N12855', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N12855', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14001', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14001', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14001', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N34661', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N34661', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N34661', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N928UW', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N928UW', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N928UW', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N522DN', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N522DN', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N522DN', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14008', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14008', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N14008', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N396AN', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N396AN', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N396AN', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N301DV', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N301DV', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N301DV', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N33286', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N33286', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N33286', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N501DN', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N501DN', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N501DN', 'first', 2);

INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N948UW', 'economy', 4);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N948UW', 'business', 2);
INSERT IGNORE INTO aircraft_capacity (aircraft_id, seat_class, capacity) 
VALUES ('N948UW', 'first', 2);

INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('EWR', 'Newark Liberty International Airport', 'ny, new york, nyc, philadephia, philly, newark, new jersey');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('JFK', 'John F. Kennedy International Airport', 'ny, new york, nyc, philadephia, philly, long island, new jersey');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('MUC', 'Munich International Airport', 'munich');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('BCN', 'Josep Tarradellas Barcelona-El Prat Airport', 'barcelona');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('MAD', 'Madrid-Barajas Adolfo Suárez Airport', 'madrid');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('DUB', 'Dublin Airport', 'dublin, ireland');
INSERT IGNORE INTO airport (airport_id, airport_name, airport_tags) 
VALUES ('YVR', 'Vancouver International Airport', 'vancouver, canada');


INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '100', 'EWR', 'BCN', 6, 0, '22:50', '08:20', 'N14001', 420.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '100', 'EWR', 'BCN', 3, 3, '00:10', '07:50', 'N501DN', 450.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '100', 'EWR', 'BCN', 5, 5, '00:10', '07:50', 'N501DN', 450.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 1, 1, '16:50', '20:20', 'N14001', 420.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 3, 3, '16:50', '20:20', 'N14001', 420.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 0, 0, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 1, 1, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 2, 2, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 3, 3, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 4, 4, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 5, 5, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '271', 'JFK', 'MUC', 6, 6, '15:00', '22:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 0, 0, '8:00', '13:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 1, 1, '8:00', '13:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 2, 2, '8:00', '13:20', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 3, 3, '8:00', '13:20', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 4, 4, '8:00', '13:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 5, 5, '8:00', '13:15', 'N928UW', 780.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '272', 'MUC', 'JFK', 6, 6, '8:00', '13:20', 'N928UW', 780.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '277', 'YVR', 'EWR', 2, 2, '10:30', '12:15', 'N14008', 265.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '277', 'YVR', 'EWR', 4, 4, '10:30', '12:15', 'N14008', 265.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '278', 'EWR', 'YVR', 2, 2, '6:15', '8:20', 'N14008', 265.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '278', 'EWR', 'YVR', 4, 4, '6:15', '8:20', 'N14008', 265.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '241', 'DUB', 'JFK', 1, 1, '18:30', '22:15', 'N33286', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '241', 'DUB', 'JFK', 3, 3, '18:30', '22:15', 'N33286', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '245', 'JFK', 'DUB', 1, 1, '12:15', '16:20', 'N33286', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '245', 'JFK', 'DUB', 3, 3, '12:15', '16:20', 'N33286', 550.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '771', 'DUB', 'JFK', 1, 1, '18:30', '22:15', 'N522DN', 510.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '771', 'DUB', 'JFK', 3, 3, '18:30', '22:15', 'N522DN', 510.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '772', 'JFK', 'DUB', 1, 1, '12:15', '16:20', 'N522DN', 510.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '772', 'JFK', 'DUB', 3, 3, '12:15', '16:20', 'N522DN', 510.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '111', 'MAD', 'EWR', 1, 1, '8:30', '16:15', 'N501DN', 750.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '111', 'MAD', 'EWR', 3, 3, '8:30', '16:15', 'N501DN', 750.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '112', 'EWR', 'MAD', 1, 2, '22:15', '6:20', 'N501DN', 750.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '112', 'EWR', 'MAD', 3, 4, '22:15', '6:20', 'N501DN', 750.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('F9', '700', 'JFK', 'EWR', 1, 1, '8:30', '11:15', 'N22525', 120.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('F9', '700', 'JFK', 'EWR', 3, 3, '8:30', '11:15', 'N22525', 120.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('F9', '701', 'EWR', 'JFK', 1, 1, '13:15', '15:20', 'N22525', 120.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('F9', '701', 'EWR', 'JFK', 3, 3, '13:15', '15:20', 'N22525', 120.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('B6', '445', 'JFK', 'EWR', 1, 1, '8:30', '11:15', 'N12855', 150.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('B6', '445', 'JFK', 'EWR', 3, 3, '8:30', '11:15', 'N12855', 150.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('B6', '446', 'EWR', 'JFK', 1, 1, '13:15', '15:20', 'N12855', 150.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('B6', '446', 'EWR', 'JFK', 3, 3, '13:15', '15:20', 'N12855', 150.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'DUB', 'EWR', 1, 2, '19:30', '3:40', 'N301DV', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'EWR', 'DUB', 1, 1, '11:30', '18:25', 'N301DV', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'EWR', 'DUB', 2, 2, '11:30', '18:25', 'N301DV', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'EWR', 'DUB', 3, 3, '11:30', '18:25', 'N301DV', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'EWR', 'DUB', 4, 4, '11:30', '18:25', 'N301DV', 550.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('DL', '160', 'EWR', 'DUB', 5, 5, '11:30', '18:25', 'N301DV', 520.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '219', 'YVR', 'JFK', 1, 1, '11:30', '14:40', 'N396AN', 270.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '218', 'JFK', 'YVR', 1, 1, '07:30', '10:20', 'N396AN', 280.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '218', 'JFK', 'YVR', 2, 2, '07:30', '10:20', 'N396AN', 280.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '218', 'JFK', 'YVR', 3, 3, '07:30', '10:20', 'N396AN', 280.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '218', 'JFK', 'YVR', 4, 4, '07:30', '10:20', 'N396AN', 250.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '218', 'JFK', 'YVR', 5, 5, '07:30', '10:20', 'N396AN', 280.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '212', 'MAD', 'JFK', 1, 2, '20:10', '04:50', 'N948UW', 980.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '211', 'JFK', 'MAD', 1, 1, '11:30', '18:20', 'N948UW', 980.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '211', 'JFK', 'MAD', 2, 2, '11:30', '18:20', 'N948UW', 980.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '211', 'JFK', 'MAD', 3, 3, '11:30', '18:20', 'N948UW', 980.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '211', 'JFK', 'MAD', 4, 4, '11:30', '18:20', 'N948UW', 950.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('AA', '211', 'JFK', 'MAD', 5, 5, '11:30', '18:20', 'N948UW', 980.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 1, 1, '16:50', '20:20', 'N14001', 420.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 3, 3, '16:50', '20:20', 'N14001', 420.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 5, 5, '16:50', '20:20', 'N14001', 420.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '101', 'BCN', 'EWR', 0, 0, '16:50', '20:20', 'N14001', 390.00);

INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '102', 'EWR', 'MUC', 1, 1, '11:20', '22:10', 'N34661', 880.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '102', 'EWR', 'MUC', 3, 3, '11:20', '22:10', 'N34661', 880.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '102', 'EWR', 'MUC', 5, 5, '11:20', '22:10', 'N34661', 880.00);
INSERT IGNORE INTO flight (airline_id, flight_id, departure_airport_id, arrival_airport_id, departure_weekday, arrival_weekday, departure_time, arrival_time, aircraft_id, price) 
VALUES ('UA', '102', 'EWR', 'MUC', 0, 0, '11:20', '22:10', 'N34661', 790.00);