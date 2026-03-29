CREATE TABLE station
(
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    name      VARCHAR(255)  NOT NULL,
    latitude  DECIMAL(9, 6) NOT NULL,
    longitude DECIMAL(9, 6) NOT NULL
);

INSERT INTO station (name, latitude, longitude)
VALUES ('Lõunakeskus, Tartu', 58.365000, 26.687000);

INSERT INTO station (name, latitude, longitude)
VALUES ('Sagrada Família, Barcelona', 41.403600, 2.174400);

INSERT INTO station (name, latitude, longitude)
VALUES ('Hallgrímskirkja, Reykjavík', 64.142500, -21.926600);

