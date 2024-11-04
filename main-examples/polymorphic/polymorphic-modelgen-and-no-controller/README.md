[![Introduction](https://img.shields.io/badge/Introduction-grey?style=for-the-badge)](../README.md)
[![Polymorphic No Controller](https://img.shields.io/badge/Polymorphic_No_Controller-grey?style=for-the-badge)](../polymorphic-no-controller/README.md)
[![Polymorphic Modelgen And No Controller](https://img.shields.io/badge/Polymorphic_Modelgen_And_No_Controller-lightgrey?style=for-the-badge)](README.md)

# Polymorphic Modelgen And No Controller #


main-examples\polymorphic\polymorphic-modelgen-and-no-controller:
This is a very regular spring-boot project by itself. Its a bit different from  the basic example we must have seen earlier. main-examples\extend\extend-modelgen-and-no-controller:  
This is not a very regular spring-boot swagger codegen project by itself.  

It uses a xdamah-maven-codegen-plugin for generating the model code.  
This swagger code generation plugin extends the usual swagger-codegen-maven-plugin. 

Otherwise the only unusual dependencies which you can find in this project by means of the parent pom.xmls is

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
</dependency>
```		
Now lets discuss the code:  

```java
@SpringBootApplication(scanBasePackages = { "io.github.xdamah", "com.example" })
public class PolymorphicNoControllerAndModelGenExampleApplication {
	public static void main(String[] args) {

		SpringApplication.run(PolymorphicNoControllerAndModelGenExampleApplication.class, args);
	}

}
```	

Now showing snippet of service class.   

```java
@Service
public class SampleService {
	private Map<Long, StoredTrip> trips = new LinkedHashMap<>();
	private final AtomicLong counter = new AtomicLong();

	public StoredTrip createTrip(Trip trip) {
		StoredTrip storedTrip = new StoredTrip();

		try {
			BeanUtils.copyProperties(storedTrip, trip);
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new IllegalArgumentException("Unable to store trip");
		}
		long newId = counter.incrementAndGet();
		storedTrip.setTripId(newId);
		trips.put(newId, storedTrip);
		return storedTrip;

	}

	public StoredTrip addRequest(long tripid, BaseRequest request) {

		StoredTrip trip = trips.get(tripid);
		if (trip == null) {
			throw new IllegalArgumentException("invalid trip id of " + tripid);
		} else {
			List<BaseRequest> requests = trip.getRequests();
			if (requests == null) {
				requests = new ArrayList<BaseRequest>();
				trip.setRequests(requests);
			}
			requests.add(request);
		}

		return trip;
	}

	public StoredTrip getTrip(long tripId) {
		StoredTrip trip = trips.get(tripId);
		if (trip == null) {
			throw new IllegalArgumentException("invalid trip id of " + tripId);
		}

		return trip;
	}

}

```	

There is one more code. 

[Modified Atlassian RequestBodyValidator](src/main/java/com/atlassian/oai/validator/interaction/request/RequestBodyValidator.java)
But its really just a means of extending the Atlassian validation components by overwriting that one class. In future it is hoped this can be done in a cleaner manner. 


Till now most of this has been already discussed in the Basic Examples. 


Please see [swagger specifications](api-docs.json).

Lets discuss it a little.

Under "paths" we have briefly below structure (Omitting many details here for brevity).
    
```json
"paths": {
		"/createtrip": {
			"post": {
				"tags": [
					"trip"
				],
				"operationId": "createtrip",
				"x-damah-service": "sampleService.createTrip(com.example.trip.Trip)"
			}
		},
		"/trips/{tripid}": {
			"get": {
				"tags": [
					"trip"
				],
				"operationId": "triprequests",
				"x-damah-service": "sampleService.getTrip(long)"
			}
		},
		"/addrequest/{tripid}": {
			"post": {
				"tags": [
					"trip"
				],
				"operationId": "addrequest",
				"x-damah-service": "sampleService.addRequest(long, com.example.request.BaseRequest)"
			}
		}
	}
```	
We see here that the swagger specs is a regular specifications file which uses a x-damah-service to indicate the service bean method that will be invoked by the endpoint.

Thats one concept.  
The model class used by the service class is generated based on "components/schemas" of the swagger-specifications.

Namely:
    
```json
"components": {
		"schemas": {
			"Trip": {
				"required": [
					"tripName"
				],
				"type": "object",
				"properties": {
					"tripName": {
						"type": "string"
					},
					"requests": {
						"type": "array",
						"items": {
							"oneOf": [
								{
									"$ref": "#/components/schemas/FlightRequest"
								},
								{
									"$ref": "#/components/schemas/CarRequest"
								},
								{
									"$ref": "#/components/schemas/HotelRequest"
								}
							],
							"discriminator": {
								"propertyName": "type"
							}
						}
					}
				}
			},
			"StoredTrip": {
				"allOf": [
					{
						"$ref": "#/components/schemas/Trip"
					},
					{
						"type": "object",
						"required": [
							"tripId"
						],
						"properties": {
							"tripId": {
								"type": "integer",
								"format": "int64"
							}
						}
					}
				]
			},
			"BaseRequest": {
				"required": [
					"startDateTime",
					"endDateTime",
					"type"
				],
				"type": "object",
				"properties": {
					"type": {
						"type": "string"
					},
					
					"startDateTime": {
						"type": "string",
						"format": "date-time"
					},
					"endDateTime": {
						"type": "string",
						"format": "date-time"
					}
				}
			},
			"FlightRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/BaseRequest"
					},
					{
						"type": "object",
						"required": [
							"fromAirport",
							"toAirport"
						],
						"properties": {
							"fromAirport": {
								"type": "string"
							},
							"toAirport": {
								"type": "string"
							},
							"seatType": {
								"type": "string"
							}
						}
					}
				]
			},
			"CarRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/BaseRequest"
					},
					{
						"type": "object",
						"required": [
							"from",
							"to"
						],
						"properties": {
							"from": {
								"type": "string"
							},
							"to": {
								"type": "string"
							},
							"vehicleType": {
								"type": "string"
							}
						}
					}
				]
			},
			"HotelRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/BaseRequest"
					},
					{
						"type": "object",
						"required": [
							"from",
							"to"
						],
						"properties": {
							"hotelName": {
								"type": "string"
							},
							"roomType": {
								"type": "string"
							},
							"location": {
								"type": "string"
							}
						}
					}
				]
			}
		}
	}
```	

Referred to this.

https://swagger.io/docs/specification/data-models/inheritance-and-polymorphism/

Have supported discriminator/propertyName.

Yet to support discriminator/mapping.  (Its a WIP for now on my part)

Lets now discuss the differences and new code.



Polymorphism in model design is demonstrated here.

But the main concept here is how when we write the Model classes by hand the swagger documentation is communicated in the swagger specifications.   



That is all the code we write.  


We do not have to write the Rest controller.
We do not write it in this example because all the information that we code in the rest controller is present in the swagger specs and its extension.



Lets try this out and see:

Visit http://localhost:8080/swagger-ui.html 
 
<img src="imgs/swagger-ui-home.png" alt="swagger-ui" width="400" height="400">  

Lets visit http://localhost:8080/api-docs/ and scroll down. 

```json  
"schemas": {
			"com.example.request.BaseRequest": {
				"discriminator": {
					"propertyName": "type"
				},
				"properties": {
					"type": {
						"type": "string"
					},
					"startDateTime": {
						"type": "string",
						"format": "date-time"
					},
					"endDateTime": {
						"type": "string",
						"format": "date-time"
					}
				},
				"required": [
					"type"
				]
			},
			"com.example.request.CarRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/com.example.request.BaseRequest"
					},
					{
						"type": "object",
						"properties": {
							"from": {
								"type": "string"
							},
							"to": {
								"type": "string"
							},
							"vehicleType": {
								"type": "string"
							}
						}
					}
				],
				"required": [
					"type"
				]
			},
			"com.example.request.FlightRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/com.example.request.BaseRequest"
					},
					{
						"type": "object",
						"properties": {
							"fromAirport": {
								"type": "string"
							},
							"toAirport": {
								"type": "string"
							},
							"seatType": {
								"type": "string"
							}
						}
					}
				],
				"required": [
					"type"
				]
			},
			"com.example.request.HotelRequest": {
				"allOf": [
					{
						"$ref": "#/components/schemas/com.example.request.BaseRequest"
					},
					{
						"type": "object",
						"properties": {
							"hotelName": {
								"type": "string"
							},
							"roomType": {
								"type": "string"
							},
							"location": {
								"type": "string"
							}
						}
					}
				],
				"required": [
					"type"
				]
			},
			"com.example.trip.StoredTrip": {
				"allOf": [
					{
						"$ref": "#/components/schemas/com.example.trip.Trip"
					},
					{
						"type": "object",
						"properties": {
							"tripId": {
								"type": "integer",
								"format": "int64"
							}
						}
					}
				]
			},
			"com.example.trip.Trip": {
				"properties": {
					"tripName": {
						"type": "string"
					},
					"requests": {
						"type": "array",
						"items": {
							"discriminator": {
								"propertyName": "type"
							},
							"oneOf": [
								{
									"$ref": "#/components/schemas/com.example.request.FlightRequest"
								},
								{
									"$ref": "#/components/schemas/com.example.request.CarRequest"
								},
								{
									"$ref": "#/components/schemas/com.example.request.HotelRequest"
								}
							]
						}
					}
				}
			}
		}
```	
The above shows the result of "x-damah-models": ["com.example.request.BaseRequest", "com.example.trip.Trip", "com.example.trip.StoredTrip"] in the actual swagger specifications.  


Having said all this will proceed: 

<img src="imgs/screen01.png" alt="create trip" width="400"/>   

Press "Try out". Press "Execute". 

<img src="imgs/screen02.png" alt="create trip response" width="400"/>   

This is one way of creating a trip.   
Lets create another trip in a different way.   

<img src="imgs/screen03.png" alt="change request" width="400"/>  

That looks like this.   

<img src="imgs/screen04.png" alt="create trip with requests" width="500"/>  

And response looks like the below.    
<img src="imgs/screen05.png" alt="create trip with requests response" width="400"/>   

So far we have created two trips and the trip ids can be seen in the responsees.  

Lets try fetching a trip using a non existent trip id.   

<img src="imgs/screen06.png" alt="bad trip id" width="400"/>   

Getting below response.  
<img src="imgs/screen07.png" alt="bad trip id response" width="400"/>   

In this example have chosen to not reveal the actual problem.  

We have seen in this example so far two trip ids of 1 and 2.

Lets try with them.

<img src="imgs/screen08.png" alt="trip id 1 response" width="400"/>    

we get above for tripId of 1.  

<img src="imgs/screen09.png" alt="trip id 2 response" width="400"/>    

we get above for tripId of 2.  


Lets now try the add request functionality.  


<img src="imgs/screen10.png" alt="add Flight Request" width="400"/>  

Trip Id of 1 which did not have any requests earlier now has the flight request added.  

<img src="imgs/screen11.png" alt="trip id with flight request" width="400"/>  

Lets now select a car request to add.   


<img src="imgs/screen12.png" alt="trip id with flight request" width="400"/>  

That looks like this.   

<img src="imgs/screen13.png" alt="trip id with flight request" width="400"/> 

Response looks like below

<img src="imgs/screen14.png" alt="trip id with flight request" width="400"/> 

Similarly try with a Hotel Request.   


Please try the other [polymorphic no controller example](../polymorphic-no-controller/README.md).  

After that please try the [Main Examples](../../README.md).       

If interested can go into more-examples folder later to understand what other features are also there for a more complete picture.






