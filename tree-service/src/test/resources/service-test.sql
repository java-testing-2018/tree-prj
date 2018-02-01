DELETE FROM node;

INSERT INTO node(id, name, parent_id, child_string) VALUES (8, 'head', null, '|1|5|3|2|4|7|');
INSERT INTO node(id, name, parent_id, child_string) VALUES (1, 'first', 8, '|3|');
INSERT INTO node(id, name, parent_id, child_string) VALUES (5, 'second', 8, '|2|4|7|');
INSERT INTO node(id, name, parent_id, child_string) VALUES (3, 'level2_1', 1, null);
INSERT INTO node(id, name, parent_id, child_string) VALUES (2, 'level2_2', 5, null);
INSERT INTO node(id, name, parent_id, child_string) VALUES (4, 'level2_3', 5, '|7|');
INSERT INTO node(id, name, parent_id, child_string) VALUES (7, 'level3_1', 4, null);
