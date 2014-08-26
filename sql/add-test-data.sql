INSERT INTO Users (username, password, salt, admin, last_login) VALUES
('Admin', 'Password', '', true, now()),
('Jallu', 'Jallu', '', false, now());

\set Jallu_u '(SELECT user_id FROM Users WHERE username = \'Jallu\')'

INSERT INTO Drinks(name, description, image_url, owner, date) VALUES
('Jallu', 'Opiskelijan herkku', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now()),
('ullaJ', 'Salanimellinen mysteerijuoma', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now());

\set Jallu1_d '(SELECT drink_id FROM Drinks WHERE name = \'Jallu\')'
\set Jallu2_d '(SELECT drink_id FROM Drinks WHERE name = \'ullaJ\')'

INSERT INTO Ingredients(name, amount, drink_id) VALUES
('Jallu1', 500, :Jallu1_d),
('Jallu2', 250, :Jallu2_d);

INSERT INTO Favourites(user_id, drink_id) VALUES
(:Jallu_u, :Jallu1_d),
(:Jallu_u, :Jallu2_d);
