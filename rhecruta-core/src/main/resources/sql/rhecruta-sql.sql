CREATE TABLE user (
	#User data
	id SERIAL,
	username VARCHAR(255) NOT NULL,
	password VARCHAR(255) NOT NULL,
	role TEXT NOT NULL,
	approved BOOLEAN NOT NULL,
	#PK
	PRIMARY KEY(id)
);

CREATE TABLE candidate (
	#Candidate data
	id SERIAL,
	cpf VARCHAR(11) UNIQUE,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	#Embedded Address
	country VARCHAR(255) NOT NULL,
	state VARCHAR(255) NOT NULL,
	neighborhood VARCHAR(255) NOT NULL,
	street VARCHAR(255) NOT NULL,
	complement VARCHAR(255) NOT NULL,
	number VARCHAR(255) NOT NULL,
	#Social
	linkedin_url TEXT,
	github_url TEXT,
	#User
	user_id NUMBER NOT NULL,
	#FK
	FOREIGN KEY(user) REFERENCES user(id),
	#PK
	PRIMARY KEY(id)
);

CREATE TABLE administrator (
	#Administrator Data
	id SERIAL,
	cpf VARCHAR(11) UNIQUE,
	firstname VARCHAR(255) NOT NULL,
	lastname VARCHAR(255) NOT NULL,
	#Embedded Address
	country VARCHAR(255) NOT NULL,
	state VARCHAR(255) NOT NULL,
	neighborhood VARCHAR(255) NOT NULL,
	street VARCHAR(255) NOT NULL,
	complement VARCHAR(255) NOT NULL,
	number VARCHAR(255) NOT NULL,
	#FK
	user_id NUMBER NOT NULL,
	FOREIGN KEY(user) REFERENCES user(id),
	#PK
	PRIMARY KEY(id)
);

CREATE TABLE offer (
	#Offer Data
	id SERIAL,
	offer_type VARCHAR(255) NOT NULL,
	description TEXT NOT NULL,
	vacancies INTEGER DEFAULT 1,
	status VARCHAR(255) NOT NULL,
	#PK
	PRIMARY KEY(id)
);

CREATE TABLE invite (
	#Invite Data
	offer_id INT NOT NULL,
	invited_id INT NOT NULL,
	inviter_id INT NOT NULL,
	accepted BOOLEAN default FALSE,
	#FK
	FOREIGN KEY(offer_id) REFERENCES offer(id),
	FOREIGN KEY(invited_id) REFERENCES candidate(id)
	FOREIGN KEY(inviter_id) REFERENCES administrator(id)
	#PK
	PRIMARY KEY(offer_id, invited_id, inviter_id)
);

CREATE TABLE enterview (
	#Enterview Data
	id SERIAL,
	offer_id INT NOT NULL,
	candidate_id INT NOT NULL,
	applier_id INT NOT NULL,
	score DOUBLE PRECISION default 0,
	start_time TIMESTAMP NOT NULL,
	end_time TIMESTAMP,
	#Embedded Address
	country VARCHAR(255) NOT NULL,
	state VARCHAR(255) NOT NULL,
	neighborhood VARCHAR(255) NOT NULL,
	street VARCHAR(255) NOT NULL,
	complement VARCHAR(255) NOT NULL,
	number VARCHAR(255) NOT NULL,
	#FK
	FOREIGN KEY(offer_id) REFERENCES offer(id),
	FOREIGN KEY(candidate_id) REFERENCES candidate(id),
	FOREIGN KEY(applier_id) REFERENCES administrator(id),
	#PK
	PRIMARY KEY(id)
);

CREATE TABLE system_evaluation (
	#System_Evaluation data
	id SERIAL,
	score DOUBLE PRECISION NOT NULL,
	candidate_id INT NOT NULL,
	offer_id INT NOT NULL,
	#FK
	FOREIGN KEY(candidate_id) REFERENCES candidate(id),
	FOREIGN KEY(offer_id) REFERENCES offer(id),
	#PK
	PRIMARY KEY(id)
);

