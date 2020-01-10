# flight-reservation-project
CS336 Final Project

## How to build and run this application

### Tools
1. Maven

2. Java 8 JDK

#### Maven
1. Verify that you have Maven installed on your system

  in your CLI, run `mvn -v`. If the output is a version number above 2.0, you should be fine.

2. If Maven is not installed on your system, either use your preferred package manager to install it OR follow the instructions in the link below (note: if you do not have a package manager on your system, installing one such as `brew` or `choco` will make everything much easier):

  https://www.tutorialspoint.com/maven/maven_environment_setup.htm

3. After installing Maven, run `mvn -v` to verify that it has installed correctly.

#### Java 8 JDK
1. Check your current Java version by running `java -version` and ensuring that the JDK version is Java 8. **Only Java 8 is compatiable with the project**

2. If you do not have the appropriate JDK/Java version, install it using your preferred package manager OR follow the instructions in the link below (as usual, using a package manager makes all of this much easier)

  https://www3.ntu.edu.sg/home/ehchua/programming/howto/JDK_Howto.html

3. Verify that your Java installation was successful (this may require a system reboot) with `java -version`

### Cloning, Building, and Running Locally
#### Cloning from Git
1. Clone the project repo by running `git clone https://github.com/alanfl/flight-reservation-project.git`

2. Ensure that you are in the top-level directory for the project

3. Run `mvn package` and ensure that the build was successful

If the build fails for some reason, please let me know by sending a screenshot of the exception that is thrown

#### Running the Application Locally
1. While remaining in the top-level directory, run `java -jar target/boot.jar`

It is critical that you remain in the top-level directory when running this command, otherwise the boot-up will fail.

2. You should see some script outputs, and after 20 seconds or so (depending on how powerful your machine is), the final output line should read "Application started in x seconds."

3. In your browser, navigate to https://localhost:8443

4. Your browser will warn you that the connection is not private. This is due to me being too lazy to sign the SSL certificate. You should proceed anyway because you're only connecting to your localhost.

5. If everything works, you can terminate the application with Ctrl+C, otherwise, post a screenshot/text of the exception in Discord

## How to best work on this application

### Cloning, Building, and Running Locally
#### Use a local mysql instance

It is preferable to install (if not present) and use a local mysql instance as opposed to the production database on Amazon RDS. The reason for this is that if multiple people are developing, the automated .sql scripts could potentially conflict. The best way to do this is to change the mysql credentials specified in the application.properties file to match whatever credentials you have set on your local machine.

#### Use mvn spring-boot:run instead of java -jar target/boot.jar

Using this command will allow the system to automatically "refresh" certain files whenever you make updates. This is especially helpful when seeing live changes to the front-end, or when debugging API gateways. The main downside to this is that it may not be fool-proof, and some changes may still require a full rebuild of the application.
