INSERT INTO user (username, password, status, name, last_name, email, create_for, create_at, update_for, update_at) VALUES ('ROOT', 'ROOT', 'ENABLE', 'ROOT', 'ROOT', 'ROOT@EMAIL.COM', 'ROOT', NOW(), NULL, NULL);
INSERT INTO user (username, password, status, name, last_name, email, create_for, create_at, update_for, update_at) VALUES ('USER', 'USER', 'ENABLE', 'USER', 'USER', 'USER@EMAIL.COM', 'ROOT', NOW(), NULL, NULL);

INSERT INTO role (name, create_for, create_at, update_for, update_at) VALUES ('ADMIN', 'ROOT', NOW(), NULL, NULL);
INSERT INTO role (name, create_for, create_at, update_for, update_at) VALUES ('USER', 'ROOT', NOW(), NULL, NULL);

INSERT INTO user_role (id_role, id_user, create_for, create_at, update_for, update_at) VALUES (1, 1, 'ROOT', NOW(), NULL, NULL);
INSERT INTO user_role (id_role, id_user, create_for, create_at, update_for, update_at) VALUES (2, 1, 'ROOT', NOW(), NULL, NULL);
INSERT INTO user_role (id_role, id_user, create_for, create_at, update_for, update_at) VALUES (2, 2, 'ROOT', NOW(), NULL, NULL);

