package com.server.service;
import com.server.entity.User;
import com.server.entity.VerificationToken;
import com.server.repository.UserRepository;
import com.server.repository.VerificationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class TokenService implements TokenServiceInterface {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private VerificationTokenRepository verificationTokenRepository;

    private final JwtEncoder jwtEncoder;
    public TokenService(JwtEncoder jwtEncoder) {
        this.jwtEncoder = jwtEncoder;
    }


    // FUNCTIONS //
    @Override
    public VerificationToken createVerificationToken(User user) {
        String token = UUID.randomUUID().toString();
        VerificationToken verificationToken = new VerificationToken(user, token);
        verificationTokenRepository.save(verificationToken);
        return verificationToken;
    }

    @Override
    public VerificationToken recreateVerificationToken(String email) {
        // Find old token
        User user = userRepository.findByEmailIgnoreCase(email);
        if(user==null || user.isVerified()) return null;
        Optional<VerificationToken> foundToken = verificationTokenRepository.findById(user.getId());
        if(foundToken.isEmpty()) return null;
        VerificationToken newToken = foundToken.get();

        // New expiration date
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, 15);
        newToken.setExpiration(new Date(cal.getTime().getTime()));

        // Create new token
        newToken.setToken(UUID.randomUUID().toString());
        verificationTokenRepository.save(newToken);
        return newToken;
    }

    @Override
    public String validateVerificationToken(String tryToken) {
        VerificationToken verificationToken = verificationTokenRepository.findByToken(tryToken);
        if(verificationToken == null) return "Incorrect token";

        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        if(cal.getTime().getTime() - verificationToken.getExpiration().getTime() >= 0) {
            return "Expired token";
        }
        User user = verificationToken.getUser();
        user.setVerified(true);
        verificationTokenRepository.delete(verificationToken);
        return "Success";
    }

    @Override
    public String createJwtToken(Authentication authentication) {
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(""));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plus(10, ChronoUnit.MINUTES))
                .subject(authentication.getName())
                .claim("scope", scope)
                .build();
        return this.jwtEncoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }
}
