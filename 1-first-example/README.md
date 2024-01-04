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
Press The blue execute button.
Response  
<img src="imgs/screen12.png" alt="Response" width="400"/>   

Lets change the input:  
From   
<img src="imgs/screen13.png" alt="Response" width="400"/>   
to    
<img src="imgs/screen14.png" alt="Response" width="400"/>  

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

