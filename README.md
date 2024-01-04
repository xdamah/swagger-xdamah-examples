# Swagger Xadamah Examples #

This README would normally document whatever steps are necessary to these example applications up and running.

### What is this repository for? ###

* This reository  demonstrates capabilities of  Swagger Xadamah - https://github.com/xdamah/swagger-xdamah

### Main Concept? ###

Swagger can be used with two approaches.
* Code First
* Design First  

Often in code first we write code of this type.
![Code First example](imgs/codefirsteg.png)
Then we generate swagger documents from this code.  

Not showing here the validation constraints and other best practices like Controller Advice usage for exception handling.  

Similarly in design first we write  swagger specifications similar to the below   
![Design First example](imgs/designfirsteg.png)

Then we generate code from the specifications.

Again not showing here details of validation constraints.

Will see more details in that regard later.   
If we look carefully and obviously we will see that the swagger specifications- hand written or generated or for that matter the controller code hand written or generated are the API contract. They both specify the same things - 
* the url path.
* the validation constraints
* the media types for the request and response

It will not be incorrect to say that they both are request/response rules for request/response processing expressed in json/yaml or code.  

The main goal is to express, enforce those rules and invoke the business logic.

What we have here is a variation of design first approach.
Obviously design first implies code generation. 

If we see the code each controller code will look very similar to another controller code. The only difference will be in the service method invoked.  What we are trying to avoid here is generating the controller and related code. The rules for that purpose including that of validations are already there in the swagger specs in json/yaml. 

Instead of generating the controller code which mirrors the rules specified in the swagger specs is it possible to invoke apply those same rules leveraging whats specified in the swagger specs as source of truth for the rules and also achieve invoking of the service class method?  

So that's the objective.   


### How do I get set up? ###

* Temporary Dependency
* Prerequisites
* Steps


*Temporary Dependency*   

* clone https://github.com/xdamah/swagger-xdamah
* In the project folder run "mvn clean install".
* Once this is deployed into maven central this step wont be needed. 

*Prerequisites*  
* JDK 18
* Latest Maven

*Steps*  
* clone https://github.com/xdamah/swagger-xdamah-examples
* In the project folder run "mvn clean package".
* That should build all the example projects.
* Each project builds into a jar file - target/demo.jar   
* In each project cd [example project name]
* for example cd 1-first-example
* then run from comamnd prompt "java -jar target/demo.jar"
* When that finishes launching we should run http://localhost:8080/swagger-ui.html  
* Will be able to follow along using swagger ui.   
* Where needed will also refer to postman collection for same.  
* More details in each project folder's Readme.MD.


