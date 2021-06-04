INSERT INTO location (name) VALUES ('Itt');
INSERT INTO location (name) VALUES ('Itten');
INSERT INTO location (name) VALUES ('Ott');
INSERT INTO location (name) VALUES ('Amott');

INSERT INTO user (name, username, password, role) VALUES ('Adminbácsi', 'admin', '$2a$10$Xtv0umJn1DDZ3ds5FTWHUOHuwJnRQ0qrYpTA1ANaRl8vdiwo1R1iW', 'ROLE_ADMIN');
INSERT INTO user (name, username, password, role) VALUES ('Tesztusz', 'user', '$2a$10$QUhsliTs8Ufe9nSQgIwzzeDktWOdDw8WY77lN.3AAXL5vVDI2EoVO', 'ROLE_USER');

INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('adminisztracio', 'asd', 'ACTIVE', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP(), 1);
INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('teszteles', 'hullapelyhes feherho', 'UNPUBLISHED', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP(), 2);

INSERT INTO event_locations (events_id, locations_id) VALUES (1, 2);
INSERT INTO event_locations (events_id, locations_id) VALUES (2, 4);

INSERT INTO participation (username,approval, created_at, event_id, user_id) VALUES ('admin', 'ACCEPTED', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO participation (username, approval, created_at, event_id, user_id) VALUES ('user', 'REJECTED', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO participation (username, approval, created_at, event_id, user_id) VALUES ('user', 'ACCEPTED', CURRENT_TIMESTAMP(), 2, 2);