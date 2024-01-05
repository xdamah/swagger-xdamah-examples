# Swagger Xadamah Examples #

This README would normally document whatever steps are necessary to these example applications up and running.

### What is this repository for? ###

* This repository  demonstrates capabilities of  Swagger Xadamah - https://github.com/xdamah/swagger-xdamah

### Main Concept ###

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

The only code that is generated is that of the model POJO classes.  

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


### More Details ###
We have these swagger extensions examples of which are shown below.

"x-damah": true,   
"x-damah-service": "com.example.SampleService.doSomething(Person)".   

These swagger extensions can be applied to any of the operations in the swagger json adjacent to the operationId.  

Here by "x-damah": true we are saying we want to use this concept that we have been discussing.  
Using "x-damah": true or specifying "x-damah-service" class and method is enough to enable this.  

"x-damah-service" specifies post validation which is the service class method that must be invoked.

*What happens if we dont specify x-damah related attributes?*  
It will trigger regular design first code generation and use for that operation
Please see 3-mixed example for more information.

*What happens if we just specify x-damah=false ?*  
It will prevent code generation  for that operation.
Needless the developer must write the code in code first style and match the specs.
Please see 3-mixed example for more information.

*What about parameters?*  

Before discussing that lets be on same page with https://swagger.io/docs/specification/describing-request-body/   

"Unlike OpenAPI 2.0, where the request body was defined using body and formData parameters, OpenAPI 3.0 uses the requestBody keyword to distinguish the payload from parameters (such as query string). The requestBody is more flexible in that it lets you consume different media types, such as JSON, XML, form data, plain text, and others."

Naturaly it makes sense to map the request body irerspective of media types to POJOS or java beans generally speaking.

https://swagger.io/docs/specification/describing-parameters/

It is intended that all the parameter types as described in above url are supported when xdamah is enabled.  

However when using xdamah there are some additional concepts for these parameters.  

* Whenever there are more than one parameters mentioned for an operation the parameters are wrapped into a POJO. 
* If there is only one parameter for an operation its used as it is.  
* "x-damah-param-type": "PersonParam" - use this syntax to specify the name of the POJO to generate which wraps the parameters.
* If the same parameters are being used in different operations rather than generate another POJO can also leverage  this syntax- "x-damah-param-ref": "PersonParam"  


*What about Service methods?*  
* Service methods are written in same way as we would usually.  
* However a xdamah combatible service method will have at most 2 arguments.  
Listing below some examples.

| Service method examples                                   | Description                                                    |    
| :-------------------------------------------------------- | :------------------------------------------------------------- |   
| com.example.SampleService.savePerson(Person)              | Person is from RequestBody                                     |  
| com.example.SampleService.byid(long)                      | No request body just a single long type parameter              |   
| com.example.SampleService.doSomething(Person,PersonParam) | Person is from RequestBody, PersonParam is the parameter POJO  |   
| com.example.SampleService.doSomethingElse(Person,long)    | Person is from RequestBody, single long type parameter         | 


What if I need to access something not specified in the parameters or request body as defined in the swagger specifications?
* Not very clean but one can use 

```java
@Autowired
private HttpServletRequest request;

```













