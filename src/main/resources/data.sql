CREATE TABLE meetingrooms (
    id INTEGER(10) AUTO_INCREMENT PRIMARY KEY NOT NULL,
    name VARCHAR(25) NOT NULL,
    date VARCHAR(15) NOT NULL,
    startHour VARCHAR(6) NOT NULL,
    endHour VARCHAR(6) NOT NULL
);

INSERT INTO meetingrooms (name, date, startHour, endHour) VALUES
('Sala Principal', '15/08/2021', '09:00', '12:00'),
('Sala 2', '15/08/2021', '09:00', '11:30'),
('Sala 3', '15/08/2021', '10:00', '14:00');