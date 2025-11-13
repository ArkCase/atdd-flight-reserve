CREATE DATABASE IF NOT EXISTS flight_atdd character set utf8mb4 collate utf8mb4_bin;

CREATE USER IF NOT EXISTS 'fl_advisor'@'%' IDENTIFIED WITH caching_sha2_password BY 'fl_advisor';

GRANT ALL PRIVILEGES ON flight_atdd.* TO 'fl_advisor'@'%';

FLUSH PRIVILEGES;
