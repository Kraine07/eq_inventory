package kraine.app.eq_inventory;

import kraine.app.eq_inventory.exception.DuplicateUserException;
import kraine.app.eq_inventory.exception.UserNotFoundException;
import kraine.app.eq_inventory.model.LoginModel;
import kraine.app.eq_inventory.model.RegisterModel;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import kraine.app.eq_inventory.model.User;
import kraine.app.eq_inventory.repository.UserRepoInterface;
import kraine.app.eq_inventory.service.UserService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest {

    @Mock
    private UserRepoInterface userRepo;

    @Mock
    private BCryptPasswordEncoder bpe;

    @InjectMocks
    private UserService userService;

    private User mockUser;

    private LoginModel mockLoginModel;
    private RegisterModel mockRegisterModel;

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("plaintext123");

        mockLoginModel = new LoginModel();
        mockLoginModel.setEmail("john@example.com");
        mockLoginModel.setPassword("plaintext123");

        mockRegisterModel = new RegisterModel();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockRegisterModel.setEmail("john@example.com");
    }


    // CREATE USER

    @Test
    void shouldAddUserSuccessfully() {
        //Arrange
        when(userRepo.existsByEmail("john@example.com")).thenReturn(false);
        when(bpe.encode("plaintext123")).thenReturn("hashedPassword");
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User savedUser = userService.addUser(mockRegisterModel, null);

        // Assert
        assertThat(savedUser.getPassword()).isEqualTo("hashedPassword");
        assertThat(savedUser.getIsTemporaryPassword()).isTrue();
        assertThat(savedUser.getIsSuspended()).isFalse();

        verify(userRepo).save(any(User.class));
    }






    // DUPLICATE EMAIL
    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userRepo.existsByEmail("john@example.com")).thenReturn(true);

        // Act & Assert
        DuplicateUserException exception = assertThrows(DuplicateUserException.class, () -> {
            userService.addUser(mockRegisterModel, null);
        });

        assertThat(exception.getMessage()).isEqualTo("The email address john@example.com is already in use.");
        verify(userRepo, never()).save(any());
    }









    // SUCCESSFULL LOGIN
    @Test
    void successfulLogin_ReturnsUserAndResetsFailedAttempts() {
        // Arrange
        LoginModel loginModel = new LoginModel("test@example.com", "rawPassword");
        User mockUser =  User.builder()
            .id(1L)
            .email("test@example.com")
            .password("encodedPassword")
            .failedAttempts(3)
            .isSuspended(false)
            .isTemporaryPassword(false)
            .build();

        // mock repo
        when(userRepo.findByEmail("test@example.com")).thenReturn(mockUser);
        when(bpe.matches("rawPassword", "encodedPassword")).thenReturn(true);
//        when(userRepo.save(mockUser)).thenReturn(mockUser); // Return updated user
        when(userRepo.existsById(1L)).thenReturn(true);
        when(userRepo.saveAndFlush(any(User.class))).thenReturn(mockUser);


        // Act
        User result = userService.attemptLogin(loginModel);

        // Assert
        assertNotNull(result, "The returned user should not be null");
        assertEquals(0, result.getFailedAttempts(), "Failed attempts should be reset to 0");
        assertEquals("test@example.com", result.getEmail(), "Email should match");
        verify(userRepo).saveAndFlush(mockUser);
    }










    // INVALID EMAIL
    @Test
    void testFindByEmail_UserNotFound() {
        LoginModel inputLoginModel = new LoginModel();
        inputLoginModel.setEmail("test@example.com");
        inputLoginModel.setPassword("password123");

        when(userRepo.findByEmail(inputLoginModel.getEmail())).thenReturn(null);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.attemptLogin(inputLoginModel);
        });

        assertEquals("This user does not exist.", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(inputLoginModel.getEmail());
        verifyNoInteractions(bpe);
    }












    // INVALID PASSWORD
    @Test
    void testFindByEmail_InvalidPassword() {
        LoginModel inputLoginModel = new LoginModel();
        inputLoginModel.setEmail("test@example.com");
        inputLoginModel.setPassword("password123");

        User retrievedUser = new User();
        retrievedUser.setEmail("test@example.com");
        retrievedUser.setPassword("$2a$12$hashedPassword"); // Example of bcrypt hash

        when(userRepo.findByEmail(inputLoginModel.getEmail())).thenReturn(retrievedUser);
        when(bpe.matches(inputLoginModel.getPassword(), retrievedUser.getPassword())).thenReturn(false);

        UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
            userService.attemptLogin(inputLoginModel);
        });

        assertEquals("This user does not exist.", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(inputLoginModel.getEmail());
        verify(bpe, times(1)).matches(inputLoginModel.getPassword(), retrievedUser.getPassword());
    }


}
