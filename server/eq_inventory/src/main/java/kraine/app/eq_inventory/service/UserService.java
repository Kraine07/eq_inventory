package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.RegisterModel;
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
        // check for duplicate email
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException(user.getEmail() + " is already in use.");
        }
        // hash password
        user.setPassword(bpe.encode(user.getPassword()));
        // set remaining fields
        user.setIsTemporaryPassword(true);
        user.setIsSuspended(false);
        return userRepo.save(user);
    }



    public User findByEmail(LoginModel loginModel) {
        User retrievedUser = userRepo.findByEmail(loginModel.getEmail());

        if (retrievedUser == null) {
            throw new UserNotFoundException("User with email " + loginModel.getEmail() + " not found.");
        }

        if (!bpe.matches(loginModel.getPassword(), retrievedUser.getPassword())) {
            throw new PasswordNotFoundException("Invalid password for user with email " + loginModel.getEmail());
        }
        return retrievedUser;
    }

    
    
    
    public User updateUser(User user) {
        // prevent a new user form being created
        if(user == null || user.getId() == null) throw new UserNotFoundException("This user does not exist.");
        return userRepo.save(user);
    }




    public User updatePassword(User user, String oldPassword) {
        //check if old password matches
        User userInDb = userRepo.findByEmail(user.getEmail());
        if (!bpe.matches(oldPassword, userInDb.getPassword())) {
            throw new PasswordNotFoundException("The old password provided is not correct.");
        }
        user.setPassword(bpe.encode(user.getPassword()));
        return userRepo.save(user);
    }




    public List<User> getUsers() {
        return userRepo.findAll();
    }

}
