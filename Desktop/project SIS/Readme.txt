
->Required Software
-VS CODE
-MySQL Server
-SQLTOOLS EXTENSION IN VS CODE
- My SQL CONNECTOR JAR FILE
- Download link: [MySQL Connector/J](https://dev.mysql.com/downloads/connector/j/)

-> Steps to Run the Project
- Open MySQL Workbench or MySQL Command Line.
- Create the database:

   CREATE DATABASE student_management;

-> Import the database structure (`SIS copydatabase.sql` file provided in the folder):
   
   $ mysql -u root -p student_management < SIS copydatabase.sql

-After creating a database, using the SQLTools extension in VSCode, connection should be  established.

-Just build it using ctrl+shift+build

-> Open the `DatabaseConnection.java` file.
   Update the database credentials:
  
   private static final String URL = "jdbc:mysql://localhost:3306/student_management";
   private static final String USER = "root"; //should be Replaced with MySQL  username
   private static final String PASSWORD = "password";//should be Replaced with MySQL password

-> Compile and Run 

video link demonstrating how to run the project:- https://www.youtube.com/watch?v=MLS-paiCQto

-Navigate to the project directory:
   
   $ cd /path/to/file where code is saved/project SIS/src/com/company
   
-Compile the project:
  
   $ javac -cp .:/path to where project is saved/project SIS/mysql-connector-j-9.1.0.jar" *.java
   
-Run the project:
 
  $ java -cp .:/path to where project is saved/project SIS/mysql-connector-j-9.1.0.jar" LoginPage

-> login Instructions(entering into interface)

-Student Login:Test Username:-Sachethan
               password:-1234

-Admin Login: Username:-admin1
              password:-test123

->how the project folder looks
|
|.vs code --- settings.json 
|              tasks.json
|bin---
|src --|
|      |---com--|
|               |--company---|
|                            |---├── Account.java
|                                ├── AttendanceGUI.java
                                 ├── CourseRegistrationGUI.java
                                 ├── DatabaseConnection.java
                                 ├── FeeTransactionGUI.java
                                 ├── Hostel.java
                                 ├── LeaveDetails.java
                                 ├── LoginPage.java
                                 ├── MainMenu.java
                                 ├── Marks.java
                                 ├── PasswordManager.java
                                 ├── Profile.java
                                 ├── ReportsGUI.java
                                 ├── Syllabus.java
                                 ├── SystemSettingsGUI.java
                                 ├── Timetable.java
                                 ├── UserManagementGUI.java  
                    
|mysql-connector-j-9.1.0




