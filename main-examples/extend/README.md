[![Introduction](https://img.shields.io/badge/Introduction-lightgrey?style=for-the-badge)](README.md)
[![Not So Basic No Controller](https://img.shields.io/badge/Not_So_Basic_No_Controller-grey?style=for-the-badge)](extend-no-controller/README.md)
[![Not So Basic Modelgen And No Controller](https://img.shields.io/badge/Not_So_Basic_Modelgen_And_No_Controller-grey?style=for-the-badge)](extend-modelgen-and-no-controller/README.md)

# Swagger XDamah Not So Basic Examples #

This README documents whatever steps are necessary to get these not so basic example applications up and running.


## Prerequisites ##
Below are the prerequisites I would recommend before getting into this
### General Prerequisites ###
* Knowledge of Spring Boot
* Knowledge and experience of Swagger - Code First and Design First
* Please go through these basics using this  [Quick Start](../../README.md).

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

In this we by extending swagger in built validations and demonstrate validations beyond OOTB validations. We use swagger extensions to achieve this.  
We also demonstrate custom schemas. (For custom schemas used an approach that works well with both hand-written models and code-generated models. Will explore other approaches also in future.)  
These two as mentioned above are our goals here. 

| Link         | Description    |
| :----------- | :------------- |
| [![Not So Basic No Controller](https://img.shields.io/badge/Not_So_Basic_No_Controller-grey?style=for-the-badge)](extend-no-controller/README.md)      | Demonstrates custom schemas and custom validation while eliminating controller code.        | 
| [![Not So Basic Modelgen And No Controller](https://img.shields.io/badge/Not_So_Basic_Modelgen_And_No_Controller-grey?style=for-the-badge)](extend-modelgen-and-no-controller/README.md)      | Same as above and additional code generation of model classes.     | 

### What next? ###

Please see  [Main Examples](../README.md)  

We will find here the same basic examples we have seen in the Quick Start.  
We will also find examples explaining how to bring in custom schemas and go beyond the basic swagger validations and brig in custom validations as discussed above here. 
** We will also see how to work with polymorphic models.**  

When finished with the main examples please see  [More Examples](../../more-examples/README.md)  to understand what other features are also there for a more complete picture.


