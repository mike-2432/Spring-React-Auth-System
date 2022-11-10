package com.server.controller;
import com.server.entity.Email;
import com.server.entity.PasswordResetToken;
import com.server.entity.User;
import com.server.entity.VerificationToken;
import com.server.model.ChangePasswordModel;
import com.server.model.NewUserModel;
import com.server.service.EmailServiceInterface;
import com.server.service.TokenServiceInterface;
import com.server.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

// CONTROLLER //
// This controller is accessible by everyone //
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    private UserServiceInterface userServiceInterface;
    @Autowired
    private EmailServiceInterface emailServiceInterface;
    @Autowired
    private TokenServiceInterface tokenServiceInterface;

    public String applicationUrl(HttpServletRequest request){
        return request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
    }

    // ENDPOINTS //
    // ========= //

    // GET AUTHENTICATION TOKEN //
    @PostMapping("/loginToken")
    public String getLoginToken(Authentication authentication) {
        String token = tokenServiceInterface.createJwtToken(authentication);
        return token;
    }

    // REGISTER //
    @PostMapping("/registration")
    public ResponseEntity<String> registerUser(@RequestBody @Valid NewUserModel newUserModel, HttpServletRequest request) {
        // Validate payload
        if(userServiceInterface.doesUsernameExist(newUserModel.getUsername())) {
            return new ResponseEntity<>("Username already exists", HttpStatus.BAD_REQUEST);
        }
        if(userServiceInterface.doesEmailExist(newUserModel.getEmail())) {
            return new ResponseEntity<>("Email already exists", HttpStatus.BAD_REQUEST);
        }
        if(!userServiceInterface.isEmailValid(newUserModel.getEmail())) {
            return new ResponseEntity<>("Email is not valid", HttpStatus.BAD_REQUEST);
        }
        if(!userServiceInterface.isPasswordValid(newUserModel.getPassword())) {
            return new ResponseEntity<>("Password is not valid", HttpStatus.BAD_REQUEST);
        }
        if(!newUserModel.getPassword().equals(newUserModel.getRepeatedPassword())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
        // Create user and token
        User user = userServiceInterface.registerUser(newUserModel);
        VerificationToken token = tokenServiceInterface.createVerificationToken(user);
        // Create and send mail
        String url = applicationUrl(request);
        Email email = new Email(user.getEmail(),
                "Verification Token",
                "http://" + url + "/api/auth/confirmRegistration?token=" + token.getToken());
        emailServiceInterface.sendEmail(email);
        return new ResponseEntity<>("Success", HttpStatus.CREATED);
    }

    // RESEND VERIFICATION TOKEN //
    @GetMapping("/resendVerificationToken")
    public void resendVerificationToken(@RequestParam("email") String emailAddress, HttpServletRequest request) {
        if(!userServiceInterface.doesEmailExist(emailAddress)) return;
        if(userServiceInterface.isEmailVerified(emailAddress)) return;
        VerificationToken newToken = tokenServiceInterface.recreateVerificationToken(emailAddress);
        if(newToken==null) return;

        String url = applicationUrl(request);
        Email email = new Email(emailAddress,
                "Verification Token",
                "http://" + url + "/api/auth/confirmRegistration?token=" + newToken.getToken());
        emailServiceInterface.sendEmail(email);
    }

    // VERIFY REGISTRATION //
    @GetMapping("/confirmRegistration")
    public ResponseEntity<String> registerVerification(@RequestParam("token") String tryToken) {
        String result = tokenServiceInterface.validateVerificationToken(tryToken);
        if(result == "Incorrect token") {
            return new ResponseEntity<>("Incorrect Token", HttpStatus.BAD_REQUEST);
        }
        if(result == "Expired token") {
            return new ResponseEntity<>("Expired Token", HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>("Your email is verified! You can now log in", HttpStatus.OK);
    }

    // GET PASSWORD RESET TOKEN //
    @PostMapping("/getPasswordResetToken")
    public void getPasswordResetToken(@RequestBody ChangePasswordModel email, HttpServletRequest request) {
        User user = userServiceInterface.findUserByEmail(email.getEmail());
        if(user==null) return;
        PasswordResetToken token = tokenServiceInterface.createResetToken(user);

        String url = applicationUrl(request);
        Email newEmail = new Email(user.getEmail(),
                "Reset Token",
                token.getToken());
        emailServiceInterface.sendEmail(newEmail);
    }

    // RESET PASSWORD //
    @PostMapping("/resetPassword")
    public ResponseEntity<String> resetPassword(@RequestBody ChangePasswordModel changePasswordModel) {
        String tryToken = changePasswordModel.getResetToken();
        String email = changePasswordModel.getEmail();
        String newPassword = changePasswordModel.getNewPassword();
        // Check if password is valid
        if(!userServiceInterface.isPasswordValid(newPassword)) {
            return new ResponseEntity<>("Password is not valid", HttpStatus.BAD_REQUEST);
        }
        // Validate Token
        String result = tokenServiceInterface.validateResetToken(tryToken, email);
        if(!result.equals("Success")) return new ResponseEntity<>("Invalid token email combination", HttpStatus.BAD_REQUEST);
        // Change the password
        User user = userServiceInterface.findUserByEmail(email);
        userServiceInterface.changePassword(user, newPassword);
        return new ResponseEntity<>("Success",HttpStatus.OK);
    }
}