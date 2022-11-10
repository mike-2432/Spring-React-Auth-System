package com.server.controller;
import com.server.entity.User;
import com.server.model.ChangePasswordModel;
import com.server.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

// CONTROLLER //
// This controller is accessible by authorized users only //
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    private UserServiceInterface userServiceInterface;

    // ENDPOINTS //
    // ========= //

    // CHECK LOGIN STATUS //
    @PostMapping("/allowed")
    public ResponseEntity<String> allowed(Authentication authentication){
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }

    // GET USERNAME //
    @PostMapping("/getDetails")
    public Map<String, String> getUsername(Authentication authentication) {
        User user = userServiceInterface.findUserByName(authentication.getName());
        HashMap<String, String> details = new HashMap<>();
        details.put("username", user.getUsername());
        details.put("email", user.getEmail());
        return details;
    }

    // CHANGE PASSWORD //
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        if(!userServiceInterface.isEmailVerified(changePasswordModel.getEmail())) {
            return new ResponseEntity<>("This user is not yet verified", HttpStatus.BAD_REQUEST);
        }
        if(!userServiceInterface.isPasswordValid(changePasswordModel.getNewPassword())) {
            return new ResponseEntity<>("Password is not valid", HttpStatus.BAD_REQUEST);
        }
        if(!changePasswordModel.getNewPassword().equals(changePasswordModel.getRepeatPassword())) {
            return new ResponseEntity<>("Passwords do not match", HttpStatus.BAD_REQUEST);
        }
        User user = userServiceInterface.findUserByEmail(changePasswordModel.getEmail());
        if(!userServiceInterface.doesPasswordMatch(user, changePasswordModel.getOldPassword())) {
            return new ResponseEntity<>("Incorrect old password", HttpStatus.BAD_REQUEST);
        }
        if(userServiceInterface.doesPasswordMatch(user, changePasswordModel.getNewPassword())) {
            return new ResponseEntity<>("Nothing has been changed", HttpStatus.BAD_REQUEST);
        }
        userServiceInterface.changePassword(user, changePasswordModel.getNewPassword());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}
