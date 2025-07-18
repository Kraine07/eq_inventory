package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.UserRepoInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    UserRepoInterface userRepo;

    @Autowired
    private BCryptPasswordEncoder bpe;


    public User addUser(User user) {

        // check for duplicate email
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("The email address " + user.getEmail() + " is already in use.");
        }
        // hash password
        user.setPassword(bpe.encode(user.getPassword()));
        // set remaining fields
        user.setFailedAttempts(0);
        user.setIsTemporaryPassword(true);
        user.setIsSuspended(false);
        return userRepo.save(user);
    }


    public User attemptLogin(LoginModel loginModel) {
        User retrievedUser = userRepo.findByEmail(loginModel.getEmail());

        if (retrievedUser == null) {
            throw new UserNotFoundException("This user does not exist.");
//            return null;
        }

        // increment failed attempts if password does not match
        if(!bpe.matches(loginModel.getPassword(), retrievedUser.getPassword())){
            retrievedUser.setFailedAttempts(retrievedUser.getFailedAttempts() + 1);

            // suspend account if login attempts is 5 or more
            if(retrievedUser.getFailedAttempts() >= 5)retrievedUser.setIsSuspended(true);
            updateUser(retrievedUser);

            throw new UserNotFoundException("This user does not exist.");
//            return null;
        }


        // reset failed attempts on successful login
        retrievedUser.setFailedAttempts(0);

        return updateUser(retrievedUser);
    }



    @CacheEvict(cacheNames = { "user", "property" }, allEntries = true)
    public User updateUser(User user) throws UserNotFoundException{
        // prevent a new user form being created
        if(user == null || user.getId() == null || !userRepo.existsById(user.getId())) throw new UserNotFoundException("This user does not exist.");
        return userRepo.save(user);
    }




    public User updatePassword(User user, String oldPassword) {
        //check if old password matches
        User userInDb = userRepo.findByEmail(user.getEmail());
        if (!bpe.matches(oldPassword, userInDb.getPassword())) {
            throw new PasswordNotFoundException("The old password is not correct.");
        }
        user.setPassword(bpe.encode(user.getPassword()));
        return userRepo.saveAndFlush(user);
    }



    public User findById(Long id) {
        return userRepo.findById(id).orElseThrow(()-> new UserNotFoundException("User not found."));
    }


    @Cacheable
    public List<User> getUsers() {
        return userRepo.findAllWithDetails();
    }

    @CacheEvict(key = "#id")
    public void deleteUser(Long id) {
        userRepo.deleteById(id);
    }
}



