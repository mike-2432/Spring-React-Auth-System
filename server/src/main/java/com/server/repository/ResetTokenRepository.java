package com.server.repository;
import com.server.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.Collection;

@Repository
public interface ResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    PasswordResetToken findByToken(String token);

    @Query(
           value = "SELECT * FROM password_reset_token WHERE user_id = ?1",
            nativeQuery = true)
    Collection<PasswordResetToken> findAllOldTokensNative(Long userId);
}
