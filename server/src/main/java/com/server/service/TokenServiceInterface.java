package com.server.service;
import com.server.entity.PasswordResetToken;
import com.server.entity.User;
import com.server.entity.VerificationToken;
import org.springframework.security.core.Authentication;

public interface TokenServiceInterface {
    VerificationToken createVerificationToken(User user);
    VerificationToken recreateVerificationToken(String email);
    String validateVerificationToken(String verificationToken);

    PasswordResetToken createResetToken(User user);
    String validateResetToken(String tryToken, String email);

    String createJwtToken(Authentication authentication);
}
