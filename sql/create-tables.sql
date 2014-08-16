CREATE TABLE Users (
	user_id SERIAL PRIMARY KEY,
	name TEXT UNIQUE,
	password TEXT,
	salt TEXT,
	admin BOOLEAN,
	last_login TIMESTAMP
);

CREATE TABLE Drinks (
	drink_id SERIAL PRIMARY KEY,
	name TEXT UNIQUE,
	description TEXT,
	picture_url TEXT,
	owner INTEGER REFERENCES Users(user_id) ON DELETE SET NULL,
	date TIMESTAMP
);

CREATE TABLE Ingredients (
	ingredient_id SERIAL PRIMARY KEY,
	name TEXT UNIQUE
);

CREATE TABLE Drink_Ingredients (
	amount INTEGER CHECK(amount > 0),
	drink_id INTEGER REFERENCES Drinks(drink_id) ON DELETE CASCADE,
	ingredient_id INTEGER REFERENCES Ingredients (ingredient_id) ON DELETE CASCADE,
	PRIMARY KEY(drink_id, ingredient_id)
);

CREATE TABLE Favourites (
	user_id INTEGER REFERENCES Users (user_id),
	drink_id INTEGER REFERENCES Drinks (drink_id),
	PRIMARY KEY(user_id, drink_id)
);
