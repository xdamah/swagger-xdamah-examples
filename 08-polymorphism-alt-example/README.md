# 08-polymorphism-alt-example Demo #



Quite similar to 07-polymorphism-example.   
  
The difference is we dont generate the model classes.  
We write the model classes with the service API.   

We dont duplicate the model class definition in the swagger component schema specification.   

See pom.xml, "x-damah-models" in api-docs.json of this example for more on how this is configured.    

While "x-damah-models" can be used also for Trip.java and StoredTrip.java and it works somewhat but its better to handwrite that part.   

In 09-polymorphism-alt1-example this limitation is also overcome.  





