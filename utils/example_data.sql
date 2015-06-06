insert into users (id, email, name, password, enabled, user_role) values (1, 'admin', 'admin', 'admin', 'true', 'ROLE_ADMIN');
insert into users (id, email, name, password, enabled, user_role) values (2, 'supervisor1', 'supervisor1', 'supervisor1', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (3, 'supervisor2', 'supervisor2', 'supervisor2', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (4, 'student1', 'student1', 'student1', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (5, 'student2', 'student2', 'student2', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (6, 'student3', 'student3', 'student3', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (7, 'sup3', 'sup3', 'sup3', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (8, 'sup4', 'sup4', 'sup4', 'true', 'ROLE_SUPERVISOR');
insert into users (id, email, name, password, enabled, user_role) values (9, 'student4', 'student4', 'student4', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (10, 'student5', 'student5', 'student5', 'true', 'ROLE_STUDENT');
insert into users (id, email, name, password, enabled, user_role) values (11, 'student6', 'student6', 'student6', 'true', 'ROLE_STUDENT');

insert into cumulative_averages (id, student_id, average) values (1, 4, 4.34);
insert into cumulative_averages (id, student_id, average) values (2, 5, 3.15);
insert into cumulative_averages (id, student_id, average) values (3, 6, 3.54);
insert into cumulative_averages (id, student_id, average) values (4, 9, 4.03);
insert into cumulative_averages (id, student_id, average) values (5, 10, 4.1);
insert into cumulative_averages (id, student_id, average) values (6, 11, 3.34);

insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1001, TRUE, 'opis1', 'tytuł1', 4, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1002, FALSE, 'opis2', 'tytuł2', NULL, 3, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1003, FALSE, 'opis3', 'tytuł3', NULL, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1004, TRUE, 'opis4', 'tytuł4', NULL, 3, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1005, TRUE, 'opis5', 'tytuł5', 5, 3, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1006, FALSE, 'opis6', 'tytuł6', NULL, 2, 'TYPE_ENGINEER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1007, TRUE, 'opis7', 'tytuł7', NULL, 3, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1008, TRUE, 'opis7', 'tytuł7', NULL, 7, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1009, TRUE, 'opis8', 'tytuł8', NULL, 8, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1010, TRUE, 'opis9', 'tytuł9', NULL, 7, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1011, TRUE, 'opis10', 'tytuł10', NULL, 2, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1012, TRUE, 'opis11', 'tytuł11', NULL, 8, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1013, TRUE, 'opis12', 'tytuł12', NULL, 3, 'TYPE_MASTER');
insert into topics(id, confirmed, description, title, student_id, supervisor_id, thesis_type) values (1014, TRUE, 'opiS13', 'tytuł13', NULL, 2, 'TYPE_MASTER');

insert into declarations(id, student_id, topic_id, rank) values (1, 4, 1008, 3);
insert into declarations(id, student_id, topic_id, rank) values (2, 4, 1009, 1);
insert into declarations(id, student_id, topic_id, rank) values (3, 4, 1010, 2);
insert into declarations(id, student_id, topic_id, rank) values (4, 4, 1014, 4);
insert into declarations(id, student_id, topic_id, rank) values (5, 5, 1013, 3);
insert into declarations(id, student_id, topic_id, rank) values (6, 5, 1009, 1);
insert into declarations(id, student_id, topic_id, rank) values (7, 5, 1010, 2);
insert into declarations(id, student_id, topic_id, rank) values (8, 6, 1014, 2);
insert into declarations(id, student_id, topic_id, rank) values (9, 6, 1013, 1);
insert into declarations(id, student_id, topic_id, rank) values (10, 6, 1011, 3);
insert into declarations(id, student_id, topic_id, rank) values (11, 6, 1007, 4);
insert into declarations(id, student_id, topic_id, rank) values (12, 10, 1009, 3);
insert into declarations(id, student_id, topic_id, rank) values (13, 10, 1012, 4);
insert into declarations(id, student_id, topic_id, rank) values (14, 10, 1004, 2);
insert into declarations(id, student_id, topic_id, rank) values (15, 10, 1014, 1);
insert into declarations(id, student_id, topic_id, rank) values (16, 11, 1008, 3);
insert into declarations(id, student_id, topic_id, rank) values (17, 11, 1009, 2);
insert into declarations(id, student_id, topic_id, rank) values (18, 11, 1011, 1);
insert into declarations(id, student_id, topic_id, rank) values (19, 9, 1006, 2);
insert into declarations(id, student_id, topic_id, rank) values (20, 9, 1007, 5);
insert into declarations(id, student_id, topic_id, rank) values (21, 9, 1011, 3);
insert into declarations(id, student_id, topic_id, rank) values (22, 9, 1013, 1);
insert into declarations(id, student_id, topic_id, rank) values (23, 9, 1002, 5);



