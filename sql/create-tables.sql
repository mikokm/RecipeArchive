CREATE TABLE Users (
	user_id SERIAL PRIMARY KEY,
	username TEXT UNIQUE,
	password TEXT,
	salt TEXT,
	admin BOOLEAN,
	last_login TIMESTAMPTZ
);

CREATE TABLE Drinks (
	drink_id SERIAL PRIMARY KEY,
	name TEXT UNIQUE,
	description TEXT,
	image_url TEXT,
	date TIMESTAMPTZ,
	owner_id INTEGER REFERENCES Users(user_id) ON DELETE SET NULL
);

CREATE TABLE Ingredients (
	ingredient_id SERIAL PRIMARY KEY,
	name TEXT,
	drink_id INTEGER REFERENCES Drinks(drink_id) ON DELETE CASCADE
);

CREATE TABLE Favourites (
	user_id INTEGER REFERENCES Users (user_id),
	drink_id INTEGER REFERENCES Drinks (drink_id),
	PRIMARY KEY(user_id, drink_id)
);
