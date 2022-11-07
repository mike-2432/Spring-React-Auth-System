package com.server.service;
import com.server.entity.User;
import com.server.model.NewUserModel;
import com.server.repository.UserRepository;
import com.server.security.UserSecDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class UserService implements UserServiceInterface, UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    // FUNCTIONS //
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user == null) throw new UsernameNotFoundException("User not found in the database");
        return new UserSecDetails(user);
    }

    @Override
    public User registerUser(NewUserModel newUserModel) {
        User user = new User();
        user.setEmail(newUserModel.getEmail());
        user.setUsername(newUserModel.getUsername());
        user.setPassword(passwordEncoder.encode(newUserModel.getPassword()));
        try {
            userRepository.save(user);
        } catch(Exception e) {
            System.out.println("Something went wrong while saving a new user to the database...");
        }
        return user;
    }

    @Override
    public User findUserByEmail(String email) {
        return userRepository.findByEmailIgnoreCase(email);
    }

    @Override
    public User findUserByName(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public void changePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    @Override
    public boolean doesUsernameExist(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public boolean doesEmailExist(String email) {
        return userRepository.findByEmailIgnoreCase(email) != null;
    }

    @Override
    public boolean doesPasswordMatch(User user, String oldPassword) {
        return passwordEncoder.matches(oldPassword, user.getPassword());
    }

    @Override
    public boolean isPasswordValid(String password) {
        String regex = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,20}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher x = pattern.matcher(password);
        return x.matches();
    }

    @Override
    public boolean isEmailValid(String email) {
        String regex = "^(?=.{1,64}@)[\\p{L}0-9_-]+(\\.[\\p{L}0-9_-]+)*@[^-][\\p{L}0-9-]+(\\.[\\p{L}0-9-]+)*(\\.[\\p{L}]{2,})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher x = pattern.matcher(email);
        return x.matches();
    }

    @Override
    public boolean isEmailVerified(String email) {
        return userRepository.findByEmailIgnoreCase(email).isVerified();
    }
}
