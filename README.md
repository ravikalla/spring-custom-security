### Example1: Copy cookie content to header
 Check the unit test "[copyCookieContentToHTTPHeader()](https://github.com/ravikalla/spring-custom-security/blob/master/src/test/java/in/ravikalla/security/SecurityApplicationTests.java#L43)" in "[SecurityApplicationTests.java](https://github.com/ravikalla/spring-custom-security/blob/master/src/test/java/in/ravikalla/security/SecurityApplicationTests.java)"

 Steps to manually test from browser:
  1. Add cookie with Key="customHeader" and value="Value to be copied to header" (Browser -> Inspect -> Application -> Cookies -> Select domain and add cookie)
  2. Trigger URL : [http://localhost:8080/checkHeader/headerLookup/customHeader](http://localhost:8080/checkHeader/headerLookup/customHeader)
  3. View the response - <sub><sup>*"Value to be copied to header"*</sup></sub>

### Example2: Custom Authentication And Authorization - Positive Test Case
 Authentication logic is in "[CustomAuthenticationFilter.java#L29](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L29)" - commented

 Authorization related UserObject and Roles are manually created and added in "[CustomAuthenticationFilter.java#L36](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L36)"

 Steps to manually test from browser:
  1. Trigger URL : [http://localhost:8080/checkHeader/secured/all](http://localhost:8080/checkHeader/secured/all)
  2. View the response - <sub><sup>*"Secured All : User Object Content : true : 1 : test@test.com : test : test : 1 : :in.ravikalla.security.model.Role@221cf671"*</sup></sub>

 Notice the role required("ADMIN") to access "CheckHeader.securedHello()" method is there(added in "[CustomAuthenticationFilter.java#L36](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L36)"). So, access to the controller([CheckHeader.securedHello()](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/controller/CheckHeader.java#L55)) is given.

### Example3: Custom Authentication And Authorization - Negative Test Case
 Authentication logic is in "[CustomAuthenticationFilter.java#L29](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L29)" - commented

 Authorization related UserObject and Roles are manually created and added in "[CustomAuthenticationFilter.java#L36](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L36)"

 Steps to manually test from browser:
  1. Trigger URL : [http://localhost:8080/checkHeader/secured/alternate](http://localhost:8080/checkHeader/secured/alternate)
  2. View the response - <sub><sup>*"... Access is denied"*</sup></sub>

 As the role required("ADMIN1") to access "CheckHeader.alternate()" method is not there(to be added in "[CustomAuthenticationFilter.java#L36](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/config/CustomAuthenticationFilter.java#L36)"). So, access to the controller([CheckHeader.alternate()](https://github.com/ravikalla/spring-custom-security/blob/master/src/main/java/in/ravikalla/security/controller/CheckHeader.java#L69)) is denied.