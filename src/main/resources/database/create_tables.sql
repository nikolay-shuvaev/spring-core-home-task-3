CREATE TABLE users (
  id       INTEGER PRIMARY KEY,
  name     VARCHAR(30),
 /* birthday DATE,*/
  email    VARCHAR(50)
);

CREATE TABLE counters (
  id INTEGER PRIMARY KEY AUTO_INCREMENT,
  event_id INTEGER,
  accessed_by_name INTEGER DEFAULT 0,
  price_queried INTEGER DEFAULT 0,
  ticket_booking INTEGER DEFAULT 0
)