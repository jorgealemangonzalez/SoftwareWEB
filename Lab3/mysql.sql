DROP DATABASE IF EXISTS login ;
CREATE DATABASE login;
USE login;
CREATE TABLE IF NOT EXISTS users (
    name CHAR(24) NOT NULL,
    surname CHAR(24) NOT NULL,
    username CHAR(24) NOT NULL,
    gender CHAR(24) NOT NULL,
    email VARCHAR(244) NOT NULL,
    password CHAR(24) NOT NULL,
    nickName CHAR(24),
    dateOfBirth CHAR(24),
    address VARCHAR(24),
    phoneNumber varchar(9),
    PRIMARY KEY (username),
    KEY (email)
);



CREATE TABLE IF NOT EXISTS tweets(
    id INT(10) unsigned NOT NULL AUTO_INCREMENT,
    tweet_text VARCHAR(120) NOT NULL,
    date    DATETIME  NOT NULL,
    username CHAR(24) NOT NULL,
    PRIMARY KEY(id),
    FOREIGN KEY(username) REFERENCES users(username),
    KEY(date)
);

CREATE TABLE IF NOT EXISTS likeTweet(
    tweet_id INT(10) unsigned NOT NULL,
    user_username CHAR(24) NOT NULL, #El usuario que le da like es diferente del que lo publica
    PRIMARY KEY(tweet_id,user_username),
    foreign key(tweet_id) references tweets(id),
    foreign key(user_username) references users(username)
);

CREATE TABLE IF NOT EXISTS follow(
    follower CHAR(24) NOT NULL,
    followed CHAR(24) NOT NULL,
    PRIMARY KEY(follower,followed),
    FOREIGN KEY(followed) REFERENCES users(username),
    FOREIGN KEY(follower) REFERENCES users(username)
);

INSERT INTO users(name,surname,username,gender,email,password) VALUES
('Arnau','Guinart','guini','Male','guinartarnau@gmail.com','pass1'),
('Jorge','Aleman','drako','Female','jale@gm.com','asda1@'),
('Arnau2','Guinart2','guini2','Male','2guinartarnau@gmail.com','pass1'),
('Jorge2','Aleman2','drako2','Female','2jale@gm.com','asda1@'),
('Arnau3','Guinart3','guini3','Male','3guinartarnau@gmail.com','pass1'),
('Jorge3','Aleman3','drako3','Female','3jale@gm.com','asda1@');

INSERT INTO tweets(tweet_text, date,username) VALUES
('first tweet of drako by db','2017-06-08','drako'),
('second tweet of guini by db','2017-06-09','guini'),
('third tweet of drako by db','2017-06-10','drako'),
('first tweet of guini by db','2017-06-08','guini'),
('second tweet of drako by db','2017-06-09','drako'),
('third tweet of guini by db','2017-06-10','guini'),
('first tweet of drako by db','2017-06-08','drako');

#INSERT INTO follow VALUES ('guini','drako');
#INSERT INTO follow VALUES ('drako','guini');


