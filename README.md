RESTful API webservice, which managing and storing in database simple notes.

a. Requirements: 
  Java 1.8
  MySql

b. setup database: 
  1. start serwer database
    e.g. sudo systemctl start mariadb
  2. to setup database run script setup.bash (when script ask you to enter password press enter)

c. to run project type the command in terminal in the root directory of project:
	mvn spring-boot:run

d. In terminal you can write lines to:
  - show all notes:
	  curl -X GET localhost:8080/api/notes/

  - create note:
	  curl -d '{"title":"welcome", "content":"hello, everyone"}' -H "Content-Type: application/json" -X POST localhost:8080/api/notes/

  - update note:
	  curl -d '{"title":"welcome", "content":"hello, everyone!"}' -H "Content-Type: application/json" -X PUT localhost:8080/api/notes/3
	
  - delete note:
	  curl -X DELETE localhost:8080/api/notes/1
