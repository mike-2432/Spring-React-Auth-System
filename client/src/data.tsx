export const info = [
    <>The goal was to build a <span className="highlight">secure role based login system</span>. To accomplish this, 
    <span className="highlight"> React</span> is used for the front-end and <span className="highlight">Spring Boot </span>
    for the back-end. <span className="highlight">Nginx</span> is used to set up a reverse-proxy with HTTPS. The application 
    is containerized and deployed on an EC2 instance of the <span className="highlight">AWS Cloud</span>. Finally, the 
    application uses a <span className="highlight">MySQL</span> database with three tables. A table for the user, for the 
    verification token and for the password reset token.</>,

    <>The <span className="highlight">back-end endpoints</span> are divided into two groups. One group for the registration system 
    that is accessible for everyone, and one group for the login process that is only accessible for verified and authenticated users.</>,

    <>The <span className="highlight">registration process</span> first performs all the necessary safety checks. After that, the 
    <span className="highlight"> password gets encrypted</span> and the user and password will be added to the database. A verification 
    link with an expiration time will then automatically be <span className="highlight">sent to the user by email</span>. The token gets 
    deleted after the user gets verified. In case the user forgets his password, the user has the option to reset it with a password 
    reset token.</>,

    <>The <span className="highlight">login system</span> uses <span className="highlight">stateless token-based authentication</span>. 
    When logging in, the password gets encrypted and is checked against the encrypted passwords in the database. After a successful 
    login, a <span className="highlight">JWT</span> is sent and stored on the front-end. The front-end continuously sends the token to
    the back-end for validation and will be deleted from local storage if the token is not valid anymore. This excludes the user from 
    using the secured endpoints and limits the accesible pages on the front-end.</>,
]