-- Database: "Pracownicy"

DROP TABLE "Worker";

CREATE TABLE "Worker" (
	login VARCHAR(40) NOT NULL,
	name VARCHAR(40),
	lastName VARCHAR(40),
	password VARCHAR(40),
	isLogged BOOLEAN,
	isBlocked BOOLEAN,
	didLogedForTheFirstTime BOOLEAN,
	timeOfStart VARCHAR(40),
	timeofEnd VARCHAR(40),
	numOfFailedLogingAttempts INTEGER,
	PRIMARY KEY(login)
	 );

