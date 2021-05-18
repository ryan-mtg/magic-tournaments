CREATE DATABASE IF NOT EXISTS tournamentdb;
USE tournamentdb;

CREATE TABLE IF NOT EXISTS user (id INTEGER AUTO_INCREMENT PRIMARY KEY, flags INTEGER NOT NULL, twitch_id BIGINT,
        twitch_name VARCHAR(50));

CREATE TABLE IF NOT EXISTS organization (id INTEGER AUTO_INCREMENT PRIMARY KEY, name VARCHAR (200) NOT NULL);

CREATE TABLE IF NOT EXISTS organization_user (organization_id INTEGER NOT NULL, user_id INTEGER NOT NULL,
        roles INTEGER NOT NULL, PRIMARY KEY (organization_id, user_id),
        FOREIGN KEY (organization_id) references organization(id), FOREIGN KEY (user_id) references user(id));
                                            );
