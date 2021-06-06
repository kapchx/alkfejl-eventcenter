INSERT INTO location (name) VALUES ('1146 Budapest, Allatkerti krt. 6-12');
INSERT INTO location (name) VALUES ('1162 Budapest, Bekecs u. 22.');


INSERT INTO user (name, username, password, role) VALUES ('Adminbácsi', 'admin', '$2a$10$Xtv0umJn1DDZ3ds5FTWHUOHuwJnRQ0qrYpTA1ANaRl8vdiwo1R1iW', 'ROLE_ADMIN');
INSERT INTO user (name, username, password, role) VALUES ('Tesztusz', 'user', '$2a$10$QUhsliTs8Ufe9nSQgIwzzeDktWOdDw8WY77lN.3AAXL5vVDI2EoVO', 'ROLE_USER');


INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('Budapest Zoo & Botanical Garden', 'The Budapest Zoo, built in 1866, is the oldest and largest zoo park of Hungary',
'ACTIVE', CURRENT_TIMESTAMP(),  '2018-02-12 - 2050-12-31', 1);

INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('The Praline Deluxe Tour', 'It is an excellent weekend program for the whole family! The tour stars at the entrance of the museum, at the chocolate fountain.',
'UNPUBLISHED', CURRENT_TIMESTAMP(), 'H-P: 9:00-18:00  Sz-V: 9:00-19:00', 2);

INSERT INTO event_locations (events_id, locations_id) VALUES (1, 1);
INSERT INTO event_locations (events_id, locations_id) VALUES (2, 2);

INSERT INTO participation (username, eventname, approval, created_at, event_id, user_id) VALUES ('admin', 'adminisztracio', 'ACCEPTED', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO participation (username, eventname, approval, created_at, event_id, user_id) VALUES ('user', 'adminisztracio', 'REJECTED', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO participation (username, eventname, approval, created_at, event_id, user_id) VALUES ('user', 'teszteles', 'ACCEPTED', CURRENT_TIMESTAMP(), 2, 2);