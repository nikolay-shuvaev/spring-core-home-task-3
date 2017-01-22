CREATE TABLE users (
  id       INTEGER PRIMARY KEY AUTO_INCREMENT,
  name     VARCHAR(40),
  birthday DATE,
  email    VARCHAR(50)
);

CREATE TABLE events (
  id         INTEGER PRIMARY KEY AUTO_INCREMENT,
  name       VARCHAR(150),
  base_price DOUBLE,
  rating     VARCHAR(10)
);

CREATE TABLE counters (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  event_id INTEGER,
  accessed_by_name INTEGER DEFAULT 0,
  price_queried INTEGER DEFAULT 0,
  ticket_booking INTEGER DEFAULT 0
);

CREATE TABLE discount_counters (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  class_name VARCHAR(255),
  user_id INTEGER,
  counter INTEGER DEFAULT 0
);

CREATE TABLE tickets (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  user_id INTEGER,
  event_id INTEGER,
  date_time TIMESTAMP,
  seat_number INTEGER,
  seat_type VARCHAR(20)
);
