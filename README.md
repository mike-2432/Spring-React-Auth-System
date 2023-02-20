# A Security Project

Please click [HERE](http://18.185.122.61) for a live version of the project. </br>
Please click [HERE](https://18.185.122.61) for a secure connection (HTTPS). This uses a self-signed certificate, so the site might not be trusted.

#### Used Technologies:
- ReactJS
- Spring Boot 
- MySQL 
- Docker (Compose)
- AWS Cloud 
- Nginx

#### About
The goal was to build a **secure role based login system**. To accomplish this, React is used for the front-end and Spring Boot for the back-end. Nginx is used to set up a reverse-proxy and create a secure HTTPS connection. The application is containerized and deployed on AWS.

The **registration process** first performs all the necessary safety checks. After that, the password gets encrypted and the user and password will be added to the database. A verification token with an expiration time will then automatically be sent to the email of the user. If the user clicks on the given link, the user will get verified and the token will be deleted. In case the user forgets his password, the user has the option to reset it.

The **login system** uses stateless token-based authentication. When logging in, the password gets encrypted and is checked against the encrypted password in the database. After a successful login, a JWT is send and stored on the front-end. The front-end continuously sends the token to the back-end for validation and will be deleted from local storage if the token is not valid anymore. This excludes the user from using the secured endpoints, like the endpoint for changing the password, and the frontend also limits the pages if the tokens are not validated or present.
