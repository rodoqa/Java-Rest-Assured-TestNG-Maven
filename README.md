![Proyect banner](https://i.imgur.com/CZK02VJ.png)
### Demo API test automation solution builded with Java and Rest Assured

### Technologies used:

**Rest Assured** - Testing and validating REST services in Java is harder than in dynamic languages such as Ruby and Groovy. REST Assured brings the simplicity of using these languages into the Java domain.

**TestNG** - is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful and easier to use.

**Maven** - is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

**Log4j2** - Apache Log4j2 is a Java-based logging utility

**Reporting** - Locally and through Jenkins there is TestNG results report available

**Notification** - Slack notification is enable through Jenkins, so every Jenkins run will be send it to Slack.

**Jenkins** - is a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories

### Design Patterns used:

**Data Access Object** basically consists of a class that is the one that interacts with the database. The methods of this class depend on the application and what we want to do. But CRUD methods are generally implemented to perform the "4 basic operations" of a database.

**Data Transfer Object** are used by DAO to transport data from the database to the business logic layer and vice versa. For example, when the business logic layer calls the create () method, what does DAO do? insert a new data ... but what data? that the business logic layer passes as a parameter… and how does this information pass? well, through a DTO.

### Retry Policy

Retry policy is applied through **IRetryAnalyzer** interface which allow us retry a failed test the number of times that we decide. More info [here.](https://static.javadoc.io/org.testng/testng/6.11/org/testng/IRetryAnalyzer.html)

```Java
public class TestRetryAnalyzer implements IRetryAnalyzer{
    int counter = 1;
    int retryMaxLimit = 3;

    public boolean retry(ITestResult result) {
        if (counter < retryMaxLimit) {
            counter++;
            return true;
        }
        return false;
    }
}
```
### Logging

**log4j2** is implemented to logg events happennig while running tests, this solution is configured to show logs in console and as well as save log in files (.log and .html).

Console
![JRTM_console log](https://i.imgur.com/PlGkE7Y.png)

.log file
![JRTM log file](https://i.imgur.com/wiEXQKJ.png)

.html file
![JRTM_html file](https://i.imgur.com/Ra1psuF.png)

### Reporting

#### TestNG results
![JRTM_testng](https://i.imgur.com/EYOHMGL.png)

#### Jenkins TestNG results
![JRTM_jenkins_testng](https://i.imgur.com/gqofqCA.png)

### Notifications

#### Slack Notification
Slack and Jenkins are configured to notify build Success, Aborted, Unstable, on Every Failure and Include Test Summary, Include Failed Tests inside Slack notification, like below:
![Slack notification](https://i.imgur.com/jfRGfzW.png)

### Project Structure used:

```
project
└───.idea
└───src
│   └───main
│   │   └───java
│   │   |   └───dao
│   │   |   │   └─── *DAO.java
│   │   |   └───dto
│   │   |   │   └─── *DTO.java
│   │   |   └───listeners
│   │   |   │   └─── listeners.java
│   │   |   └───utils
│   │   |       └─── SupporFactory.java
│   │   └───resources
│   │       └───log4j2.xml
│   └───test
│       └───java
│           └─── *Test.java
└───target
│   └───classes  
│   └───generated-sources
│   └───generated-test-sources
│   └───maven-status
│   └───test-classes
│   └───log4j2.log
│   └───log4j2.html
└───pom.xml
└───README.md
└───testng.xml

```

### Parametrized tests

This solution retrieve data from excel file using POI through DTO 'n DAO objects, like this:
```java
    // This method is to read the test data from the Excel cell, in this we are
    // passing parameters as Row num and Col num
    public static String getCellData(int RowNum, int ColNum) {
        try {
            Cell = ExcelWSheet.getRow(RowNum).getCell(ColNum);
            String CellData = Cell.getStringCellValue();
            return CellData;
        } catch (Exception e) {
            return "";
        }
    }
```