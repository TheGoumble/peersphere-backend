# Peer Sphere

# Abstract
Peer Sphere is a desktop-based application that provides college students with a collaborative study environment tailored to course-specific academic needs. The application will allow users to register and log in, create or join study groups, share and edit notes, generate flashcards, and organize review sessions in a secure and structured interface. The app is intended to simulate a real-time academic support network, allowing students in the same class to collaborate outside the classroom.

# Team members
Daphne Acher, Izze Lino, Vladimir Pierre, Javier Vargas, Bridjet Walker

# Prerequisites!!! make sure you have all of these on your ‘puter:
- JDK 17+ (java development kit)
- Apache NetBeans
-	MySQL Server 8.0 & MySQL Workbench 8.0

# Set Up
1. Copy and paste this command in the command line to download the repo
```bash
$ git clone https://github.com/TheGoumble/peersphere-backend.git
```
2. Open the project in netbeans (under file > open…)

3.	create the database on your machine
-	open MySQL workbench, connect to LOCAL MySql.
-	at the top of the script, write:
```bash
$ CREATE DATABASE peersphere_db;
``` 

-	On the next line, write:
```bash
$  USE peersphere_db;
``` 
-	Below that, paste the code from the sql script that already is on the github repo. make sure you hit the refresh arrow icon in the schemas pane after running this script or else the tables wont show up
-	IMPORTANT: the app will connect to your local MySQL instance (localhost:3306). meaning, the data individually lives on each of our computers, NOT the cloud (yet).

5.	configure db credentials:
-	in netbeans, open the package that says “util” and select DatabaseConnection.java
-	change the String USER and String PASSWORD to reflect your own credentials. user is usually “root” and password is whatever you made your local password on MySQL.

6.	build & test
-	right click the peersphere project in the pane on the left. select the tab that says “run/compile” and put app/SeedDemo.java as the main class, and hit run. if it builds with no errors, yay everything is working!!!

```bash
project structure
model: plain java objects
dao: data access objects
service: app logic
util: helpers such as db connection and password hasher
app: testers
```
