insert into users (id, email, name, password, enabled, user_role) values (1, 'admin', 'admin', 'admin', 'true', 'ROLE_ADMIN');
insert into users (id, email, name, password, enabled, user_role) values (2, 'supervisor1', 'supervisor1', 'supervisor1', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (3, 'supervisor2', 'supervisor2', 'supervisor2', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (4, 'student1', 'student1', 'student1', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (5, 'student2', 'student2', 'student2', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (6, 'student3', 'student3', 'student3', 'true', 'ROLE_STUDENT');

insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1001, TRUE, 'opis1', 'tytuł1', 4, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1002, FALSE, 'opis2', 'tytuł2', NULL, 3, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1003, FALSE, 'opis3', 'tytuł3', NULL, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1004, TRUE, 'opis4', 'tytuł4', NULL, 3, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1005, TRUE, 'opis5', 'tytuł5', 5, 3, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1006, FALSE, 'opis6', 'tytuł6', NULL, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1007, TRUE, 'opis7', 'tytuł7', 6, 3, 'TYPE_MASTER');
