package kraine.app.eq_inventory.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.RandomPasswordGenerator;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.UserRepoInterface;

@Service
@Transactional
public class UserService {

    @Autowired
    UserRepoInterface userRepo;

    @Autowired
    private BCryptPasswordEncoder bpe;


    public User addUser(User user) {
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("Email is already in use. Please try again.");
        }

        if (user.getPassword() == null) {
            user.setPassword(RandomPasswordGenerator.generatePassword(12));
        }
        user.setPassword(bpe.encode(user.getPassword()));
        user.setIsTemporaryPassword(true);
        user.setIsSuspended(false);

        return userRepo.save(user);
    }


    public User findByEmail(User user) {
        User retrievedUser = userRepo.findByEmail(user.getEmail());

        if (retrievedUser == null) {
            throw new UserNotFoundException("User with email " + user.getEmail() + " not found.");
        }

        if (!bpe.matches(user.getPassword(), retrievedUser.getPassword())) {
            throw new PasswordNotFoundException("Invalid password for user with email " + user.getEmail());
        }

        return retrievedUser;
    }

}
