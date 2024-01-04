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
