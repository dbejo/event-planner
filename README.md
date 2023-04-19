# Event planner
[![CodeFactor](https://www.codefactor.io/repository/github/dbejo/event-planner/badge/main)](https://www.codefactor.io/repository/github/dbejo/event-planner/overview/main)
## Get started

* Start a MySQL database. I used [XAMPP](https://www.apachefriends.org/hu/index.html) for this
* Configure [application.yaml](/src/main/resources/application.yaml) according to your database
* Run main in [EventPlannerApplication](src/main/java/com/purpleelephant/eventplanner/EventPlannerApplication.java)


IMPORTANT CHANGES for make it releaseable to PROD:
1) you need to make sure that your database service is accessible via the MARIADBTEST hostname.
For that you need to add the following line to the hosts file in Windows (located in C:\Windows\System32\drivers\etc folder)
127.0.0.1	mariadbtest

To be able to write to the hosts file you need to launch the text editor (notepad is good enough) as Admin (Run As Admin)

To check you need to be able to ping mariadbtest from your command prompt and it need to return response

2) you need to make sure that the DB server runs on port 3306

