# 03-with-security Demo #

Quite similar to 1-first-example  and 2-first-codegen-example.   
Just a little added security.   

In api-docs.json search for the word security and securitySchemes.   
Also look at src\main\java\com\example\config\WebSecurityConfig.java   

http://localhost:8080/swagger-ui.html   

<img src="imgs/screen01.png" alt="Step1" width="400"/>

Click Authorize button.   
<img src="imgs/screen02.png" alt="Step2" width="400"/>

Credentials are username:user and password:password    

Press the Authorize button after inputting the credentials.   
Then click the close button on next screen.   

<img src="imgs/screen03.png" alt="Step2" width="600"/>    

Try out the secured/saveperson operation the one with the lock symbol.

Verify that security works.  


This is just a simple proof of security working.

Further details for deeper examples - https://swagger.io/docs/specification/authentication/   




