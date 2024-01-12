# 1-first-example Demo #

Will capture very quick brief some of the steps.
Hope that helps convey the idea.

### Steps ###
## Steps1 ##  

<img src="imgs/screen01.png" alt="Step1" width="400"/>

Press The blue execute button.
Response  
<img src="imgs/screen02.png" alt="Response" width="400"/>   

Lets change the input:  
From   
<img src="imgs/screen03.png" alt="Response" width="400"/>   
to    
<img src="imgs/screen04.png" alt="Response" width="400"/>  

Specifically changing these values   
From:   
"email1": "abc@abc.com",   
"age": 18,   
"creditCardNumber": "4444444444444448"    

To:   
"email1": "abcabc.com",   
"age": 1,   
"creditCardNumber": "444444444444444"   

Press The blue execute button.   
Validation Response   
<img src="imgs/screen15.png" alt="Response" width="400"/>



## Steps2 ##

Lets change the request body   

<img src="imgs/screen10.png" alt="Step1" width="400"/>   

So it looks like this  

<img src="imgs/screen11.png" alt="Step1" width="400"/>   

```json
{
  "id": 1,
  "firstName": "tx1",
  "lastName": "no1",
  "email": "abc@abc.com",
  "email1": "abc@abc.com",
  "age": 18,
  "creditCardNumber": "4444444444444448",
  "sampleCustomTypeData": "hello",
  "someTimeData": "2023-09-08T23:48:29.7075175+05:30",
  "pic": "picattributeistruncated",
  "anotherPerson": {
    "id": 1,
    "firstName": "F2",
    "lastName": "no1",
    "email": "abc@abc.com",
    "email1": "abc@abc.com",
    "age": 18,
    "creditCardNumber": "4444444444444448",
    "sampleCustomTypeData": "hello",
    "someTimeData": "2023-09-08T23:48:29.7075175+05:30"
  },
  "children": [
    {
      "id": 1,
      "firstName": "F3",
      "lastName": "no13",
      "email": "abc@abc.com",
      "email1": "abc@abc.com",
      "age": 18,
      "creditCardNumber": "4444444444444448",
      "sampleCustomTypeData": "hello",
      "someTimeData": "2023-09-08T23:48:29.7075175+05:30"
    }
  ]
}
```

Note: pic attribute was truncated just to fit the json.  
Note: Use the actual example than above.

Press The blue execute button.  
Response  
<img src="imgs/screen12.png" alt="Response" width="400"/>   

Lets change the input to :   

Lets do the same changes we did on Step1 but apply them to 

"anotherPerson" and "children"[0]   


Press The blue execute button.   
Validation Response   

```json
{
  "messages": [
    {
      "key": "validation.request.body.schema.minimum",
      "level": "ERROR",
      "message": "[Path '/anotherPerson/age'] Numeric instance is lower than the required minimum (minimum: 18, found: 17)",
      "context": {
        "requestPath": "/saveperson/",
        "apiRequestContentType": "application/json",
        "location": "REQUEST",
        "pointers": {
          "instance": "/anotherPerson/age",
          "schema": "/components/schemas/Person/properties/age"
        },
        "requestMethod": "POST"
      }
    },
    {
      "key": "validation.request.body.schema.minimum",
      "level": "ERROR",
      "message": "[Path '/children/0/age'] Numeric instance is lower than the required minimum (minimum: 18, found: 17)",
      "context": {
        "requestPath": "/saveperson/",
        "apiRequestContentType": "application/json",
        "location": "REQUEST",
        "pointers": {
          "instance": "/children/0/age",
          "schema": "/components/schemas/Person/properties/age"
        },
        "requestMethod": "POST"
      }
    },
    {
      "key": "x-Email",
      "level": "ERROR",
      "message": "Property email1 is not  valid",
      "context": {
        "requestPath": "/saveperson/",
        "location": "REQUEST",
        "pointers": {
          "instance": "/anotherPerson/email1",
          "schema": "/components/schemas/Person/email1"
        },
        "requestMethod": "POST"
      }
    },
    {
      "key": "x-CreditCardNumber",
      "level": "ERROR",
      "message": "Property creditCardNumber is not  valid",
      "context": {
        "requestPath": "/saveperson/",
        "location": "REQUEST",
        "pointers": {
          "instance": "/anotherPerson/creditCardNumber",
          "schema": "/components/schemas/Person/creditCardNumber"
        },
        "requestMethod": "POST"
      }
    },
    {
      "key": "x-Email",
      "level": "ERROR",
      "message": "Property email1 is not  valid",
      "context": {
        "requestPath": "/saveperson/",
        "location": "REQUEST",
        "pointers": {
          "instance": "/children/0/email1",
          "schema": "/components/schemas/Person/email1"
        },
        "requestMethod": "POST"
      }
    },
    {
      "key": "x-CreditCardNumber",
      "level": "ERROR",
      "message": "Property creditCardNumber is not  valid",
      "context": {
        "requestPath": "/saveperson/",
        "location": "REQUEST",
        "pointers": {
          "instance": "/children/0/creditCardNumber",
          "schema": "/components/schemas/Person/creditCardNumber"
        },
        "requestMethod": "POST"
      }
    }
  ]
}
```
### What about requests of other media types  ###

What about requests of type-   
* application/xml
* application/x-www-form-urlencoded
* multipart/form-data
* application/octet-stream

For application/xml we should be able to easily try out using the swagger ui.  

However for the other ones listed below please try using postman
* application/x-www-form-urlencoded
* multipart/form-data

While on the subject:   
Regular validatons will work  for application/x-www-form-urlencoded.   
Havent yet go around to implement the custom validations like credit card for for application/x-www-form-urlencoded.   

<img src="imgs/screen13.png" alt="Response" width="400"/>   

Some custom or extension validation rules are indicated by  the green rectangles. Those rules are currently enforced only for  application/json, application/xml. These custom validations can be enforced. Havent yet got around to implemting them for application/x-www-form-urlencoded.   


The validation rules  are however applied for the regular validations eg- the blue rectangle of age and all the others.  

In case of  multipart/form-data currently the validations are nor being applied.

A word on validations:  
Initially was coding the validations also. But for now decided to reuse for validations https://bitbucket.org/atlassian/swagger-request-validator.    
This is a very neat library but at times there are gaps in it. Usually there are workarounds.  
To some extent in this implemetation have enhanced its behaviour.   


We will be discussing the same also but using a postman collection.  
Also providing a postman collection demonstrating this complete set of examples.
Please use the provided postman collection and try it out.  

Already shared screen shots of how requests worked for media types of application/json, application/xml using swagger ui.   

When using xdamah its a lot easier to support additional media types of application/x-www-form-urlencoded and  multipart/form-data.  

However these are easier tried using postman.   
sharing here postman screenshots for the same.

<img src="imgs/postman-form.png" alt="postman form" width="400"/>    
 
application/x-www-form-urlencoded   


<img src="imgs/postman-form.png" alt="postman form" width="400"/>      

application/x-www-form-urlencoded with nested   

<img src="imgs/postman-form.png" alt="postman form" width="400"/>    

multipart/form-data   

<img src="imgs/postman-form.png" alt="postman form" width="400"/>    

multipart/form-data with nested   

 

Other request types eg application/octet-stream are also supported.
Please see kitchen-sink for them.
