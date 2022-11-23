package com.server.entity;
import lombok.Data;
import javax.persistence.*;
import java.util.Calendar;
import java.util.Date;

@Data
@Entity
@Table(name = "password_reset_token")
public class PasswordResetToken {
    public static final int EXPIRATION_TIME = 10;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String token;
    private Date expiration;
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",
            nullable = false,
            foreignKey = @ForeignKey(name="PASSWORD_RESET_TOKEN"))
    public User user;

    // CONSTRUCTORS //
    public PasswordResetToken() {
    }

    public PasswordResetToken(User user, String token) {
        this.token = token;
        this.user = user;
        this.expiration = calculateExpirationDate(EXPIRATION_TIME);
    }

    // FUNCTION //
    private Date calculateExpirationDate(int expirationTime) {
        Date date = new Date();
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        cal.add(Calendar.MINUTE, expirationTime);
        return new Date(cal.getTime().getTime());
    }
}
