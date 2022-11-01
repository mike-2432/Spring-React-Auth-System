package com.server.service;
import com.server.entity.User;
import com.server.entity.VerificationToken;
import org.springframework.security.core.Authentication;


public interface TokenServiceInterface {
    VerificationToken createVerificationToken(User user);
    VerificationToken recreateVerificationToken(String email);
    String validateVerificationToken(String verificationToken);
    String createJwtToken(Authentication authentication);

}
