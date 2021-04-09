INSERT INTO location (name) VALUES ('Itt');
INSERT INTO location (name) VALUES ('Itten');
INSERT INTO location (name) VALUES ('Ott');
INSERT INTO location (name) VALUES ('Amott');

INSERT INTO user (name, username, password, role) VALUES ('Adminbácsi', 'admin', 'madmin', 'ADMIN');
INSERT INTO user (name, username, password, role) VALUES ('Tesztusz', 'test', '123123', 'USER');

INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('adminisztracio', 'asd', 'ACTIVE', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP(), 1);
INSERT INTO event (title, description, status, created_at, start_at, ORGANIZER_ID) VALUES ('teszteles', 'hullapelyhes feherho', 'UNPUBLISHED', CURRENT_TIMESTAMP(),  CURRENT_TIMESTAMP(), 2);

INSERT INTO event_locations (events_id, locations_id) VALUES (1, 2);
INSERT INTO event_locations (events_id, locations_id) VALUES (2, 4);

INSERT INTO participation (approved, created_at, event_id, user_id) VALUES ('ACCEPTED', CURRENT_TIMESTAMP(), 1, 1);
INSERT INTO participation (approved, created_at, event_id, user_id) VALUES ('REJECTED', CURRENT_TIMESTAMP(), 1, 2);
INSERT INTO participation (approved, created_at, event_id, user_id) VALUES ('ACCEPTED', CURRENT_TIMESTAMP(), 2, 2);