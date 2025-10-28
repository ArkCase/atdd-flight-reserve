CREATE DATABASE IF NOT EXISTS flight_atdd;

CREATE USER IF NOT EXISTS 'fl_advisor'@'%' IDENTIFIED BY 'fl_advisor';

GRANT ALL PRIVILEGES ON flight_atdd.* TO 'fl_advisor'@'%';

FLUSH PRIVILEGES;
