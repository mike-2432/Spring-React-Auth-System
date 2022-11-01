package com.server.service;
import com.server.entity.User;
import com.server.model.NewUserModel;


public interface UserServiceInterface {
    User registerUser(NewUserModel newUserModel);
    User findUserByEmail(String email);
    void changePassword(User user, String newPassword);
    boolean doesUsernameExist(String username);
    boolean doesEmailExist(String email);
    boolean doesPasswordMatch(User user, String oldPassword);
    boolean isPasswordValid(String password);
    boolean isEmailValid(String email);
    boolean isEmailVerified(String email);
}
