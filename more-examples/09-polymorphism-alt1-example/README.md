# 09-polymorphism-alt1-example Demo #



Quite similar to 07-polymorphism-example and 08-polymorphism-alt-example.   
  
The difference is we dont generate the model classes.  
We write the model classes with the service API.   

We dont duplicate the model class definition in the swagger component schema specification.   

See pom.xml, "x-damah-models" in api-docs.json of this example for more on how this is configured.    

We saw how in 08-polymorphism-alt-example "x-damah-models" was not enough for Trip.java and StoredTrip.java and it was better to handwrite that part in swagger specs.   

This limitation is also overcome. See how this is achieved using PolymorphismAlt1ExampleApplication 





