CREATE DATABASE  IF NOT EXISTS `flight_search_db`;
USE `flight_search_db`;


CREATE TABLE Airport (
    iata_code VARCHAR(3) NOT NULL PRIMARY KEY,
    city VARCHAR(255) NOT NULL
);

INSERT INTO Airport (iata_code, city) VALUES
('IST', 'Istanbul'),
('FRA', 'Frankfurt'),
('BER', 'Berlin'),
('AYT', 'Antalya'),
('CDG', 'Paris'),
('LHR', 'London'),
('AMS', 'Amsterdam'),
('MAD', 'Madrid'),
('FCO', 'Rome'),
('ATH', 'Athens'),
('BCN', 'Barcelona'),
('ZRH', 'Zurich'),
('CPH', 'Copenhagen'),
('VIE', 'Vienna');

CREATE TABLE Flight (
    schedule_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    origin_ap VARCHAR(3) NOT NULL,
    destination_ap VARCHAR(3) NOT NULL,
    departure_date DATE NOT NULL,
    price INT NOT NULL,
    FOREIGN KEY (origin_ap) REFERENCES Airport(iata_code),
    FOREIGN KEY (destination_ap) REFERENCES Airport(iata_code)
);

INSERT INTO flight (origin_ap, destination_ap, departure_date, price) VALUES

('IST', 'FRA', '2024-02-10', 120),
('BER', 'AYT', '2024-02-11', 135),
('CDG', 'LHR', '2024-02-12', 90),
('AMS', 'MAD', '2024-02-13', 110),
('FCO', 'ATH', '2024-02-14', 100),
('BCN', 'ZRH', '2024-02-15', 95),
('CPH', 'VIE', '2024-02-16', 105);
