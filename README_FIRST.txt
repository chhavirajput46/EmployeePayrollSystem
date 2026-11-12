EMPLOYEE ATTENDANCE AND PAYROLL MANAGEMENT SYSTEM
Java 21 + Apache Tomcat 11 + Oracle Database 21c XE + PL/SQL

IMPORTANT
- This download contains the Java web application only.
- It does NOT contain SQL or PL/SQL scripts and does NOT recreate your tables.
- It expects the Oracle schema and PL/SQL objects you created earlier:
  DEPARTMENT, EMPLOYEE, ATTENDANCE, PAYROLL
  PR_ADD_EMPLOYEE, PR_MARK_ATTENDANCE, PR_GENERATE_PAYROLL
  FN_CALCULATE_NET_SALARY
- Database user expected: EMPADMIN
- Database service expected: XEPDB1

1. CHECK THE DATABASE SETTINGS
Open:
src/main/resources/db.properties

Current local settings:
db.url=jdbc:oracle:thin:@//127.0.0.1:1521/XEPDB1
db.username=EMPADMIN
db.password=EmpPayroll2026

If you changed your Oracle password, update db.password.
This file contains a local development password. Do not publish this project or commit the password to Git.

2. REQUIRED SOFTWARE
- JDK 21
- Apache Maven 3.9 or later
- Apache Tomcat 11
- Oracle Database 21c XE running, with XEPDB1 open
- Internet connection for Maven to download dependencies on the first build

3. BUILD THE WEB APPLICATION
Open the EmployeePayrollSystem folder in VS Code.
Open Terminal > New Terminal.
Run:
    mvn clean package

If the build succeeds, Maven creates:
    target/EmployeePayrollSystem.war

4. DEPLOY TO TOMCAT 11
Find your Tomcat installation folder. Earlier you mentioned:
    E:\Program Files\Tomcat 11.0

Copy:
    target\EmployeePayrollSystem.war
to:
    E:\Program Files\Tomcat 11.0\webapps\

Make sure Tomcat is running. If it is installed as a Windows service, start it from Windows Services.
Tomcat will deploy the WAR automatically.

5. OPEN THE APPLICATION
Open:
    http://localhost:8080/EmployeePayrollSystem/

If your Tomcat uses a different port, replace 8080 with that port.

6. FEATURES INCLUDED
- Dashboard counts
- Employee list and Add Employee form (calls PR_ADD_EMPLOYEE)
- Department list and employee totals
- Attendance list and Mark Attendance form (calls PR_MARK_ATTENDANCE)
- Payroll list and Generate Payroll form (calls PR_GENERATE_PAYROLL)
- Department and attendance summary reports

7. TROUBLESHOOTING
A. ORA-12514 / ORA-12541 / connection refused:
   Check that OracleServiceXE and the Oracle listener are running.
   Confirm the service name is XEPDB1 and port is 1521.

B. ORA-01017:
   Username/password is incorrect. Update db.properties.

C. ORA-06550 / PLS-00201 / procedure not declared:
   The expected PL/SQL procedure/package/function is missing, invalid, or was created in a different Oracle schema.
   Confirm you are connected as EMPADMIN in XEPDB1 and that the required objects are VALID.

D. HTTP 404:
   Check that the WAR is in Tomcat's webapps folder and use the context path
   /EmployeePayrollSystem/.

E. Build says 'mvn' is not recognized:
   Maven is not installed or is not on PATH. Install Apache Maven 3.9+ and reopen VS Code.

8. IMPORTANT PROJECT LIMITATIONS
- This is a local academic starter application, not a production payroll system.
- It does not yet include administrator authentication, edit/delete screens, salary slips,
  or role-based security.
- The app uses your existing PL/SQL procedures. It does not modify the database schema.
