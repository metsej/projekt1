DROP TABLE IF EXISTS "Logins";
DROP TABLE IF EXISTS "Worker";

CREATE TABLE "Worker" (
	login VARCHAR(40),
	name VARCHAR(40),
	lastName VARCHAR(40),
	password VARCHAR(40),
	isLogged BOOLEAN,
	isBlocked BOOLEAN,
	didLogedForTheFirstTime BOOLEAN,
	timeOfStart TIMESTAMP,
	timeofEnd TIMESTAMP,
	numOfFailedLogingAttempts INTEGER,
	PRIMARY KEY(login)
	 );
	 
	
CREATE TABLE "Logins" (
	logNum INTEGER,
	timeOfLog TIMESTAMP,
	userLogin VARCHAR(40),
	PRIMARY KEY(logNum, userLogin),
	FOREIGN KEY (userLogin) REFERENCES "Worker"(login)
);