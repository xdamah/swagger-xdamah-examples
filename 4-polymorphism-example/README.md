# 4-polymorphism-example Demo #



Referred to this.

https://swagger.io/docs/specification/data-models/inheritance-and-polymorphism/

Have supported discriminator/propertyName.

Yet to support discriminator/mapping.  (Its a WIP for now on my part)

Swagger-code-gen for spring has a hardcoding of discriminator/propertyName as "type".

Instead of relying on the generated @JsonTypeInfo because all information is also present in the swagger json/yaml. Might later try not depending on @JsonTypeInfo.  

Having said all this will proceed: 

<img src="imgs/screen01.png" alt="create trip" width="400"/>   

Press "Try out". Press "Execute". 

<img src="imgs/screen02.png" alt="create trip response" width="400"/>   

This is one way of creating a trip.   
Lets create another trip in a different way.   

<img src="imgs/screen03.png" alt="create trip response" width="400"/>  

That looks like this.   

<img src="imgs/screen04.png" alt="create trip response" width="500"/>  

And response looks like the below.    
<img src="imgs/screen05.png" alt="create trip response" width="400"/>   

So far we have created two trips and the trip ids can be seen in the responsees.  

Lets try fetching a trip using a non existent trip id.   

<img src="imgs/screen06.png" alt="create trip response" width="400"/>   

Getting below response.  
<img src="imgs/screen07.png" alt="create trip response" width="400"/>   

In this example have chosen to not reveal the actual problem.  

We have seen in this example so far two trip ids of 1 and 2.

Lets try with them.

<img src="imgs/screen08.png" alt="create trip response" width="400"/>    

we get above for tripId of 1.  

<img src="imgs/screen09.png" alt="create trip response" width="400"/>    

we get above for tripId of 2.  


Lets now try the add request functionality.  









