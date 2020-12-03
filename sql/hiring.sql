DROP DATABASE IF EXISTS hiring;
CREATE DATABASE hiring;
USE hiring;

CREATE TABLE user (
id INT(11) NOT NULL AUTO_INCREMENT,
lastname VARCHAR(50) NOT NULL,
firstname VARCHAR(50) NOT NULL,
middlename VARCHAR(50) DEFAULT NULL,
login VARCHAR(50) NOT NULL,
password VARCHAR(50) NOT NULL,
active BOOLEAN DEFAULT FALSE,
company VARCHAR(100),
address VARCHAR(300),
email VARCHAR(254),
PRIMARY KEY (id),
KEY password (password),
KEY active (active),
UNIQUE KEY login (login)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE session (
id INT(11) NOT NULL AUTO_INCREMENT,
userid INT(11) NOT NULL,
token VARCHAR(36) NOT NULL,
PRIMARY KEY (id),
KEY token (token),
UNIQUE KEY userid (userid),
FOREIGN KEY (userid) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE skill_name (
id INT(11) NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
PRIMARY KEY (id),
UNIQUE KEY name (name)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE skill (
id INT(11) NOT NULL AUTO_INCREMENT,
nameid INT(11) NOT NULL,
level INT(1) NOT NULL,
userid INT(11) NOT NULL,
PRIMARY KEY (id),
KEY nameid (nameid),
KEY level (level),
KEY userid (userid),
FOREIGN KEY (nameid) REFERENCES skill_name (id),
FOREIGN KEY (userid) REFERENCES user (id) ON DELETE CASCADE,
UNIQUE KEY nameid_userid (nameid, userid)
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE vacancy (
id INT(11) NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
salary INT (11) NOT NULL,
activity BOOLEAN DEFAULT TRUE,
userid INT(11) NOT NULL,
PRIMARY KEY (id),
KEY activity (activity),
KEY userid (userid),
FOREIGN KEY (userid) REFERENCES user (id) ON DELETE CASCADE
) ENGINE=INNODB DEFAULT CHARSET=utf8;

CREATE TABLE requirement (
id INT(11) NOT NULL AUTO_INCREMENT,
nameid INT(11) NOT NULL,
level INT(11) NOT NULL,
obligatory BOOLEAN,
vacancyid INT(11) NOT NULL,
PRIMARY KEY (id),
KEY nameid (nameid),
KEY level (level),
KEY obligatory (obligatory),
KEY vacancyid (vacancyid),
FOREIGN KEY (nameid) REFERENCES skill_name (id),
FOREIGN KEY (vacancyid) REFERENCES vacancy (id) ON DELETE CASCADE,
UNIQUE KEY nameid_vacancyid (nameid, vacancyid)
) ENGINE=INNODB DEFAULT CHARSET=utf8;


