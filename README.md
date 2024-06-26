# Swagger XDamah Examples #

This README documents whatever steps are necessary to get these example applications up and running.

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
If we look carefully - we will see that the swagger specifications- hand written or generated or for that matter the controller code hand written or generated are the API contract. They both specify the same things - 
* the url path.
* the validation constraints
* the media types for the request and response

It will not be incorrect to say that they both are request/response rules for request/response processing expressed in json/yaml or code.  

The main goal is to express, enforce those rules and invoke the business logic.

If we see the code each controller code will look very similar to another controller code. The main difference will be in the service method invoked.  

What we are trying to avoid here is generating or handwriting the controller and related code. The rules for that purpose including that of validations are already there in the swagger specs in json/yaml. 

Instead of generating/handwriting the controller code which mirrors the rules specified in the swagger specs is it possible to invoke apply those same rules leveraging whats specified in the swagger specs as source of truth for the rules and also achieve invoking of the service class method?  

The only code that is generated/written is that of the model POJO classes. We possibly will create the model classes when  we write the business logic in service classes.  
For this purpose we can choose one of the following:
1. To either write the model classes by hand along with the business service classes  
2. Or we can generate the model classes from the swagger specs. Use them in the servic classes.  

If we choose 1. we can even go for generating the corresponding swagger component schema specifications from the model classes

So that's the objective- Have less code. Rather just have swagger specifications + only that code which is not already expressed in the swagger specifications.   

What we have here is a variation of design first approach.
Design first usually implies code generation. Not so in our approach- Lets call it xdamah. X stands for Swagger extensions. Damah means self control. It's a word play implying that The swagger specs itself does the control without a need for coding or code-generating a controller.


### How do I get set up? ###

* Prerequisites
* Steps

*Prerequisites*  
* JDK 17
* Latest Maven

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

### Whats in the examples? ###

| The Examples                    | Description                                             | Link                                     |        
| :-----------------------------  | :-----------------------------------------------------  |:---------------------------------        |   
| 01-first-example                | A quick introductory demo with no controller nor codgen | [link](01-first-example)                 |
| 02-first-codegen-example        | A quick introductory demo  with  codegen of only models | [link](02-first-codegen-example)         | 
| 03-with-security                | A demo with spring security                             | [link](03-with-security)                 |
| 04-first-fqn-example            | Same as the introductory codegen demo but uses FQNs     | [link](04-first-fqn-example)             |
| 05-first-alt-example            | No controller, No codegen, only model schema-gen        | [link](05-first-alt-example)             |
| 06-first-alt-fqn-example        | No controller, No codegen, only model schema-gen & fqn  | [link](06-first-alt-fqn-example)         |
| 07-polymorphism-example         | A demo of Polymorphism in request and response POJO     | [link](07-polymorphism-example)          |
| 08-polymorphism-alt-example     | Polymorphism +  model schema-gen                        | [link](08-polymorphism-alt-example)      |
| 09-polymorphism-alt1-example    | Polymorphism +  model schema-gen cleaner variation      | [link](09-polymorphism-alt1-example)     | 
| 10-polymorphism-alt-fqn-example | Same as the introductory codegen demo but uses FQNs     | [link](10-polymorphism-alt-fqn-example)  |
| 11-parameter-examples           | A demo of parameters							        | [link](11-parameter-examples)            |
| 12-mixed                        | Mixed use of xdamah, code first, vanilla design first   | [link](12-mixed)                         |
| 13-misc                         | A bit cluttered -includes other request types           | [link](13-misc)                  |


In 01-first-example we are handwriting only the model class. We are not having any controller class code  in the project. It can be tedious handwriting model classes and matching them with swagger specs.  In 2-first-codegen-example and thereafter we therefore demonstrate how we can rely on code generation just for the model classes. In the "alt" approaches demonstrating a third approach where we do the reverse as far as model classes are concerned.  

### More Details ###
We have these swagger extensions examples of which are shown below.

"x-damah": true,   
"x-damah-service": "sampleService.doSomething(Person)".   

These swagger extensions can be applied to any of the operations in the swagger json adjacent to the operationId.  

Here by "x-damah": true we are saying we want to use this concept that we have been discussing.  
Using "x-damah": true or specifying "x-damah-service" bean name and method is enough to enable this.  

"x-damah-service" specifies post validation which is the service bean method that must be invoked.

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

Naturally it makes sense to map the request body irrespective of media types to POJOS or java beans generally speaking.

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

| Service bean method examples                                   | Description                                                    |    
| :-------------------------------------------------------- | :------------------------------------------------------------- |   
| sampleService.savePerson(Person)              | Person is from RequestBody                                     |  
| sampleService.byid(long)                      | No request body just a single long type parameter              |   
| sampleService.doSomething(Person,PersonParam) | Person is from RequestBody, PersonParam is the parameter POJO  |   
| sampleService.doSomethingElse(Person,long)    | Person is from RequestBody, single long type parameter         | 

 


What if I need to access something not specified in the parameters or request body as defined in the swagger specifications?
* One can use below approach which is certainly neater than passing the request object to a service method in any case. Keep in mind while we are referring to this as a service class it could also be any Spring Bean. 

```java
@Autowired
private HttpServletRequest request;

```

### What is known  WIP ###

a) Initially was coding the validations using custom code entirely. Later thought it better to reuse https://bitbucket.org/atlassian/swagger-request-validator/  Have been working around some of its current limitations.  
For example- For json meadiatype there is good validation support.  
Have added support for xml, multi-part media type requests to be validated using just swagger specifications by extending its behaviour.  This can be done in multiple ways. Used a valid quick approach for now.  
~~Have more thoughts on how it can be taken further.~~  
~~For the other mediatypes this is WIP on my part. If this goes well overall can put effort in that direction.~~    


b) Am currently working with Swagger 3.0. The external swagger examples - are being merged in to the final swagger specifications as part of a value add in this library. Once this is upgraded to Swagger 3.1 this extra programmatic feature  wont be needed because 3.1 is supposed to take care of that.  


c) In the example projects also demonstrate form and multi-part form submission. This gets interesting especially when the forms have nested models and nested array type models. This is working. There is however room for making this a bit more flexible. Eg - should "." be used to traverse to a nested bean. What about for eg "/", Similarly more variations on how to identify indexing. Can be done.  

### Any thing extra that is available here and not elsewhere? ###


There are a few different concepts here.
1. Not generating the controller code.  
2. Generating only the model code if so desired.  Can work without any codegen.
3. If Parameters are more than one in number wrapping them into a java bean.  
4. Mixing code-first, design first and xdamah.  
5. Reuse of parameter definitions. (Pretty minor but can be handy) 


But there is one more interesting feature.   
A. **For the same model its much easier to support requests in multiple media types - json, xml, form, multipart as swagger request body compared to regular spring approaches.  In 1-first-example this is also demonstrated and discussed more**.   


### Also ###
- Demonstrates custom types.  
- Demonstrates custom validation.  


### TODOs ###

1. Allow the request validator to be turned off on some paths and rely on code first and  vanilla design first for validations.  
2. ~~Add a better first example that shows how to start without code generation.~~   
3. ~~Add a better first example that shows how to start without code generation but with autogeneeration of model schema.~~  
4. ~~Upgrade the spring version.~~ Upgraded for now.   
5. ~~Add Tests for the examples projects.~~  
6. Seperate form parameters from query parameters
7. Add examples for Map, ObjectNode requestBodies 
8. Update the tests now that validations are working all around.  
9. ~~Add an example that illustrates how to hand-write model and auto-generate and  the model's schema.~~ 
10. If possible work with the request validator for better integration.  
11. Generate the json when writing code first
12. Upgrade the generator versions.   
13. Upgrade from swagger 3.0 to 3.1. 
14. Work on the WIPs mentioned earlier.  
15. Caching for performance.  


