INSERT INTO Users (username, password, salt, admin, last_login) VALUES
('Admin', '8be3c943b1609fffbfc51aad666d0a04adf83c9d', '', true, now()),
('Jallu', '3af962c190331044d866358d0652bdefbdf165eb', '', false, now());

\set Jallu_u '(SELECT user_id FROM Users WHERE username = \'Jallu\')'

INSERT INTO Drinks(name, description, image_url, owner, date) VALUES
('Jallu', 'Opiskelijan herkku', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now()),
('ullaJ', 'Salanimellinen mysteerijuoma', 'http://t-mikomynt.users.cs.helsinki.fi/html-demo/images/jallu.jpg', :Jallu_u, now());

\set Jallu1_d '(SELECT drink_id FROM Drinks WHERE name = \'Jallu\')'
\set Jallu2_d '(SELECT drink_id FROM Drinks WHERE name = \'ullaJ\')'

INSERT INTO Ingredients(name, drink_id) VALUES
('25cl Jallu', :Jallu1_d),
('50cl Jallu', :Jallu2_d);

INSERT INTO Favourites(user_id, drink_id) VALUES
(:Jallu_u, :Jallu1_d),
(:Jallu_u, :Jallu2_d);
