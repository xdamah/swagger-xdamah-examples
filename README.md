[![Start here](https://img.shields.io/badge/Start_here-lightgrey?style=for-the-badge)](README.md)
[![Basic No Controller](https://img.shields.io/badge/Basic_No_Controller-grey?style=for-the-badge)](main-examples/basic/basic-no-controller/README.md)
[![Basic Modelgen And No Controller](https://img.shields.io/badge/Basic_Modelgen_And_No_Controller-grey?style=for-the-badge)](main-examples/basic/basic-modelgen-and-no-controller/README.md)

# Swagger XDamah Examples #

This README documents whatever steps are necessary to get these example applications up and running.

### What is this repository for? ###

* This repository  demonstrates capabilities of  Swagger Xadamah - https://github.com/xdamah/swagger-xdamah

## Prerequisites ##
Below are the prerequisites I would recommend before getting into this
### General Prerequisites ###
* Knowledge of Spring Boot
* Knowledge and experience of Swagger - Code First and Design First


### Technical Prerequisites ###

* Java 17
* Maven latest

### Main Concept ###

Swagger can be used with two approaches.
* Code First
* Design First  


What we have here is a variation of design first approach.
Design first usually implies code generation. Not so in our approach- Lets call it xdamah. X stands for Swagger extensions. Damah means self control. It's a word play implying that The swagger specs itself does the control without a need for coding or code-generating a controller.

In this variation of Design first we have the following:
* We have the swagger specs as starting point.   
* We have two sub variations. We either have 0 code generation or code generation only for model classes.  What we don't have is the spring controllers.   
* We do have a spring extension which can makes writing/generating of controllers redundant at least for most use cases.  
* we also have automatic validation based on swagger specs.  

Before you say "Oh no not another OSS project" will like to add this is just a way of reducing code, getting things done, following some best practices by extending existing OSS frameworks.

Frameworks extended are:
* Spring-Boot
* Swagger
* Atlassian Request Validator



Lets quickly get started with.
### Quick start ###


### How do I get set up? ###


*Steps*  
* Run "git clone -b main https://github.com/xdamah/swagger-xdamah-examples.git"
* In the project folder run "mvn clean package".
* That should build all the example projects.
* Each project builds into a jar file - target/demo.jar   
* In each project cd [example project name]
* for example cd main-examples\basic\basic-no-controller    
* then run from comamnd prompt "java -jar target/demo.jar"
* When that finishes launching we should run http://localhost:8080/swagger-ui.html  
* Will be able to follow along using swagger ui.   
* Where needed will also refer to [postman collection](swagger-xdamah-postman-collection.json)  for same.  Do import the postman collection and try where needed.  (The postman collection- Might be getting a bit out of synch as f now) 
* More details in each project folder's Readme.MD.

### Whats in the examples? ###

The examples are organized into two folders   
* main-examples
* more-examples

Will discuss the main examples here first and understand what that is about.  
The main-examples are illustrated with 2 use cases- basic and with-poly(morphism).  
In each we have two options -no-controller and -modelgen-and-no-controller  

So we basically have 6 projects of interest here in the main examples.  
* swagger-xdamah-examples\main-examples\basic\basic-no-controller
* swagger-xdamah-examples\main-examples\basic\basic-modelgen-and-no-controller
* swagger-xdamah-examples\main-examples\extend\extend-no-controller
* swagger-xdamah-examples\main-examples\extend\extend-modelgen-and-no-controller
* swagger-xdamah-examples\main-examples\polymorphic\polymorphic-no-controller
* swagger-xdamah-examples\main-examples\polymorphic\polymorphic-modelgen-and-no-controller

While these projects are organized in a tree structure to avoid repeating same pom.xml maven details - example - dependencies and plugins there is no reason why each cannot be implemented as a stand alone maven project.  

Each example here will be described in main-examples\README.md   

However for a very quick introduction will discuss the examples under "main-examples\basic" here.  


| Link         | Description    |
| :----------- | :------------- |
| [Basic No Controller](main-examples/basic/basic-no-controller/README.md)      | Demonstrates a basic spring boot application that eliminates controller code.        | 
| [Basic Modelgen And No Controller](main-examples/basic/basic-modelgen-and-no-controller/README.md)      | Demonstrates a basic spring boot application that eliminates controller code.  Also leverages code generation only of the model classes.        | 


After finishing with these can look at going beyond the basics.    

Please see [Main Examples](main-examples/README.md)  

We will find here the same basic examples we have seen earlier above.  
We will also find examples explaining how to bring in custom schemas and go beyond the basic swagger validations and brig in custom validations. 
We will also see how to work with polymorphic models.  

After that has been dealt with please see  [More Examples](more-examples/README.md)  

Again we will see the previously covered examples. We will also see some additional examples. 


If interested can go into more-examples folder later to understand what other features are also there for a more complete picture.


