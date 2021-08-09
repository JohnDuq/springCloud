INSERT INTO user (username, password, status, name, last_name, email, create_for, create_at, update_for, update_at) VALUES ('ROOT', '$2a$10$f2Px8SsdXP3DzDAaIkxsPuVd8EpL4qyynPdvlh5pEUqf/5NikaQCG', 'ENABLE', 'ROOT', 'ROOT', 'ROOT@EMAIL.COM', 'ROOT', NOW(), NULL, NULL);
INSERT INTO user (username, password, status, name, last_name, email, create_for, create_at, update_for, update_at) VALUES ('USER', '$2a$10$4XgmB0V/FkatYIHMew5Ip.U1rtjK3L30rBDXe7YKzPbC78iQwBRWK', 'ENABLE', 'USER', 'USER', 'USER@EMAIL.COM', 'ROOT', NOW(), NULL, NULL);

INSERT INTO role (name, create_for, create_at) VALUES ('ROLE_ADMIN', 'ROOT', NOW());
INSERT INTO role (name, create_for, create_at) VALUES ('ROLE_USER', 'ROOT', NOW());

INSERT INTO user_role (id_role, id_user, create_for, create_at) VALUES (1, 1, 'ROOT', NOW());
INSERT INTO user_role (id_role, id_user, create_for, create_at) VALUES (2, 1, 'ROOT', NOW());
INSERT INTO user_role (id_role, id_user, create_for, create_at) VALUES (2, 2, 'ROOT', NOW());

