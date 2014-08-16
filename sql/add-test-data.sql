INSERT INTO Users (name, password, salt, admin, last_login) VALUES ('Admin', 'Password', '', true, now());
INSERT INTO Users (name, password, salt, admin, last_login) VALUES ('Jallu', 'Jallu', '', false, now());

INSERT INTO Ingredients (name) VALUES ('Jallu');
INSERT INTO Ingredients (name) VALUES ('ullaJ');

\set Jallu_u '(SELECT user_id FROM Users WHERE name = \'Jallu\')'

INSERT INTO Drinks(name, description, picture_url, owner, date) VALUES ('Jallu', 'Opiskelijan herkku', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now());
INSERT INTO Drinks(name, description, picture_url, owner, date) VALUES ('ullaJ', 'Salanimellinen mysteerijuoma', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now());

\set Jallu1_d '(SELECT drink_id FROM Drinks WHERE name = \'Jallu\')'
\set Jallu1_i '(SELECT ingredient_id FROM Ingredients WHERE name = \'Jallu\')'

\set Jallu2_d '(SELECT drink_id FROM Drinks WHERE name = \'ullaJ\')'
\set Jallu2_i '(SELECT ingredient_id FROM Ingredients WHERE name = \'ullaJ\')'

INSERT INTO Drink_Ingredients(amount, drink_id, ingredient_id) VALUES (50, :Jallu1_d, :Jallu1_i);
INSERT INTO Drink_Ingredients(amount, drink_id, ingredient_id) VALUES (50, :Jallu2_d, :Jallu2_i);

INSERT INTO Favourites(user_id, drink_id) VALUES (:Jallu_u, :Jallu1_d);
INSERT INTO Favourites(user_id, drink_id) VALUES (:Jallu_u, :Jallu2_d);
