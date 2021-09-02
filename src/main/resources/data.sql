drop table if exists meetingrooms CASCADE ;

drop sequence if exists hibernate_sequence;

create sequence hibernate_sequence start with 1 increment by 1;

create table meetingrooms (
        id INT PRIMARY KEY AUTO_INCREMENT,
        date varchar(255) not null,
        end_hour varchar(255) not null,
        name varchar(25) not null,
        start_hour varchar(255) not null
);

INSERT INTO meetingrooms (name, date, start_hour, end_hour) VALUES
('Sala Principal', '15/08/2021', '09:00', '12:00'),
('Sala 2', '15/08/2021', '09:00', '11:30'),
('Sala 3', '15/08/2021', '10:00', '14:00');