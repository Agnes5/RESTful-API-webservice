#!/bin/bash

mysql -u root -p<<MYSQL_SCRIPT
CREATE DATABASE notes_app;
CREATE USER 'username'@'localhost' IDENTIFIED BY 'password';
GRANT ALL PRIVILEGES ON notes_app.* TO 'username'@'localhost';
FLUSH PRIVILEGES;
MYSQL_SCRIPT
