package com.server.controller;
import com.server.entity.User;
import com.server.model.ChangePasswordModel;
import com.server.service.UserServiceInterface;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


// CONTROLLER //
// This controller is accessible by logged-in users only //
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceInterface userServiceInterface;


    // CHANGE PASSWORD //
    @PostMapping("/changePassword")
    public ResponseEntity<String> changePassword(@RequestBody ChangePasswordModel changePasswordModel) {
        if(!userServiceInterface.isEmailVerified(changePasswordModel.getEmail())) {
            return new ResponseEntity<>("This user is not yet verified", HttpStatus.BAD_REQUEST);
        }
        if(!userServiceInterface.isPasswordValid(changePasswordModel.getNewPassword())) {
            return new ResponseEntity<>("Password is not valid", HttpStatus.BAD_REQUEST);
        }
        User user = userServiceInterface.findUserByEmail(changePasswordModel.getEmail());
        if(!userServiceInterface.doesPasswordMatch(user, changePasswordModel.getOldPassword())) {
            return new ResponseEntity<>("Invalid old password", HttpStatus.BAD_REQUEST);
        }
        userServiceInterface.changePassword(user, changePasswordModel.getNewPassword());
        return new ResponseEntity<>("Success", HttpStatus.OK);
    }
}