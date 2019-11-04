![Proyect banner](https://i.imgur.com/CZK02VJ.png)
### Demo API test automation solution builded with Java and Rest Assured

### Technologies used:

**Rest Assured** - Testing and validating REST services in Java is harder than in dynamic languages such as Ruby and Groovy. REST Assured brings the simplicity of using these languages into the Java domain.

**TestNG** - is a testing framework inspired from JUnit and NUnit but introducing some new functionalities that make it more powerful and easier to use.

**Maven** - is a software project management and comprehension tool. Based on the concept of a project object model (POM), Maven can manage a project's build, reporting and documentation from a central piece of information.

**Reporting** - 

**Notification** - Slack notification is enable through Jenkins, so every Jenkins run will be send it to Slack.

**Jenkins** - is a simple way to set up a continuous integration and continuous delivery environment for almost any combination of languages and source code repositories

### Reporting

### Notifications

#### Slack Notification
Slack and Jenkins are configured to notify build Success, Aborted, Unstable, on Every Failure and Include Test Summary, Include Failed Tests inside Slack notification, like below:
![Slack notification](https://i.imgur.com/YaouzJk.png)

### Project Structure used:

```
project
└───.idea
└───src
│   └───main
│   │   └───java
│   │       └───listeners
│   │       │   └─── listeners.java
│   │       └───utils
│   │           └─── SupporFactory.java
│   └───test
│       └───java
│           └─── *Test.java
└───target
│   └───classes  
│   └───generated-sources
│   └───generated-test-sources
│   └───maven-status
│   └───test-classes
└───pom.xml
└───README.md
└───testng.xml

```

### Parametrized tests
