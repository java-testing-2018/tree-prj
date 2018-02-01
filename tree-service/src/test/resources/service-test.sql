DELETE FROM node;

INSERT INTO node(id,name,parent_id) VALUES (8, 'head', null);
INSERT INTO node(id,name,parent_id) VALUES (1, 'first', 8);
INSERT INTO node(id,name,parent_id) VALUES (5, 'second', 8);
INSERT INTO node(id,name,parent_id) VALUES (3, 'level2_1', 1);
INSERT INTO node(id,name,parent_id) VALUES (2, 'level2_2', 5);
INSERT INTO node(id,name,parent_id) VALUES (4, 'level2_3', 5);
INSERT INTO node(id,name,parent_id) VALUES (7, 'level3_1', 4);
