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

Lets quickly get started with.
### Quick start ###


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

Lets start with main-examples\basic\basic-no-controller and then will discuss main-examples\basic\basic-modelgen-and-no-controller.  

main-examples\basic\basic-no-controller:
This is a very regular spring-boot project by itself.  

The only unusual dependencies which you can find in this project by means of the parent pom.xmls is
```xml
<dependency>

			<groupId>io.github.xdamah</groupId>
			<artifactId>xdamah-lib</artifactId>
			<version>${xdamah-version}</version>
		</dependency>
```		
and
```xml
<dependency>
			<groupId>com.atlassian.oai</groupId>
			<artifactId>swagger-request-validator-spring-webmvc</artifactId>
			<version>2.37.0</version>
</dependency>
```		
Now lets discuss the code:  

```java
@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class BasicNoControllerApplication {
	private static final Logger logger = LoggerFactory.getLogger(BasicNoControllerApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(BasicNoControllerApplication.class, args);
	}

	@PostConstruct
	void adjustModelConverters() {
		TypeNameResolver.std.setUseFqn(true);
		ModelConverters.getInstance().addConverter(new ByteArrayPropertyConverter());

	}

}
```	
For all other details of main examples please see main-examples\README.md.    



If interested can go into more-examples folder later to understand what other features are also there for a more complete picture.


