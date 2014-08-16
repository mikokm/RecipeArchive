CREATE TABLE Users (
	user_id SERIAL PRIMARY KEY
	name TEXT,
	password TEXT,
	salt TEXT,
	admin BOOLEAN,
	last_login TIMESTAMP
);

CREATE TABLE Drinks (
	drink_id SERIAL PRIMARY KEY,
	owner INTEGER REFERENCES Users(user_id) ON DELETE NULL,
	date TIMESTAMP,
	description TEXT,
	picture_url TEXT,
	ingredients_id INTEGER REFERENCES Drink_Ingredients(drink_id)
);

CREATE TABLE Drink_Ingredients (
	amount INTEGER (CHECK amount > 0)
	drink_id INTEGER REFERENCES Drinks(drink_id) PRIMARY KEY ON DELETE CASCADE,
	ingredient_id INTEGER REFERENCES Ingredients (ingredient_id) ON DELETE NULL,
);

CREATE TABLE Ingredients(
	ingredient_id INTEGER SERIAL
	description TEXT,

CREATE TABLE Favourites (
	user_id INTEGER REFERENCES Users (user_id),
	drink_id INTEGER REFERENCES Drinks (drink_id),
	PRIMARY KEY(user_id, drink_id)
);