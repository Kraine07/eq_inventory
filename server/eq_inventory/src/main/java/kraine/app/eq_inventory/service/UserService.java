package kraine.app.eq_inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import kraine.app.eq_inventory.PasswordServices;
import kraine.app.eq_inventory.SessionHandler;
import kraine.app.eq_inventory.DTO.UserDTO;
import kraine.app.eq_inventory.Email.EmailService;
import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.PasswordNotFoundException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.RegisterModel;
import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.UserRepoInterface;

@Service
@Transactional
@CacheConfig(cacheNames = "user")
public class UserService {

    @Autowired
    UserRepoInterface userRepo;

    @Autowired
    private EmailService emailService;

    @Autowired
    private BCryptPasswordEncoder bpe;



    @CacheEvict(cacheNames = { "user", "property" }, allEntries = true)
    public User addUser(RegisterModel registerModel, HttpServletRequest request) throws DuplicateUserException {

        User user = RegisterModel.toUser(registerModel);

        // check for duplicate email
        if (userRepo.existsByEmail(user.getEmail())) {
            throw new DuplicateUserException("The email address " + user.getEmail() + " is already in use.");
        }

        // generate, set password
        String genPass = PasswordServices.generatePassword(12);

        // hash password
        user.setPassword(bpe.encode(genPass));
        // set remaining fields
        user.setFailedAttempts(0);

        user.setIsTemporaryPassword(true);
        user.setIsSuspended(false);
        User savedUser = userRepo.save(user);

        if (savedUser != null) {
            // attributes to be used when sending email
            SessionHandler.addAttribute(request, "rawPass", genPass);
            SessionHandler.addAttribute(request, "recipient", registerModel.getEmail());

            try {
                emailService.sendPassword(SessionHandler.getAttribute(request, "recipient", String.class),
                        SessionHandler.getAttribute(request, "rawPass", String.class));
            } catch (MessagingException e) {
                System.out.println("Error sending email...");
                e.printStackTrace();
            }
        }
        return savedUser;
    }






    public User attemptLogin(LoginModel loginModel) {
        User retrievedUser = userRepo.findByEmail(loginModel.getEmail());

        if (retrievedUser == null) {
            throw new UserNotFoundException("This user does not exist.");
        }

        // increment failed attempts if password does not match
        if(!bpe.matches(loginModel.getPassword(), retrievedUser.getPassword())){
            retrievedUser.setFailedAttempts(retrievedUser.getFailedAttempts() + 1);

            // suspend account if login attempts is 5 or more
            if(retrievedUser.getFailedAttempts() >= 5)retrievedUser.setIsSuspended(true);
            updateUser(retrievedUser);

            throw new UserNotFoundException("This user does not exist.");
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




    @CacheEvict(cacheNames = { "user", "property" }, allEntries = true)
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





    public List<UserDTO> getAllUserDTOs() {
        List<User> users = getUsers();
        return users.stream().map(UserDTO::from).toList();
    }
}



