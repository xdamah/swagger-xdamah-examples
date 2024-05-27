# Swagger XDamah Not So Basic Examples #

This README documents whatever steps are necessary to get these not so basic example applications up and running.


## Prerequisites ##
Below are the prerequisites I would recommend before getting into this
### General Prerequisites ###
* Knowledge of Spring Boot
* Knowledge and experience of Swagger - Code First and Design First
*  [![Quick Start](https://img.shields.io/badge/Quick_Start-grey?style=for-the-badge)](../README.md)  Please go through these basics.

### Technical Prerequisites ###

* Java 17
* Maven latest

### How do I get set up? ###


*Steps*  
* Run "git clone -b main https://github.com/xdamah/swagger-xdamah-examples.git"
* In the project folder run "mvn clean package".
* That should build all the example projects.
* Each project builds into a jar file - target/demo.jar   
* In each project cd [example project name]
* for example cd 1-first-example
* then run from comamnd prompt "java -jar target/demo.jar"
* When that finishes launching we should run http://localhost:8080/swagger-ui.html  
* Will be able to follow along using swagger ui.   
* Where needed will also refer to [postman collection](swagger-xdamah-postman-collection.json)  for same.  Do import the postman collection and try where needed.  (The postman collection- Might be getting a bit out of synch as f now) 
* More details in each project folder's Readme.MD.

### Main Concept here ###

In this we by extending swagger in built validations and demonstrate validations beyond OOTB validations.
We also demonstrate custom schemas.

### Whats in the main examples? ###





If interested can also go into more-examples folder later to understand what other features are also there for a more complete picture.


