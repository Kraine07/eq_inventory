package kraine.app.eq_inventory;

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

    @BeforeEach
    void setup() {
        MockitoAnnotations.openMocks(this);
        mockUser = new User();
        mockUser.setFirstName("John");
        mockUser.setLastName("Doe");
        mockUser.setEmail("john@example.com");
        mockUser.setPassword("plaintext123");
    }


    // CREATION

    @Test
    void shouldAddUserSuccessfully() {
        //Arrange
        when(userRepo.existsByEmail("john@example.com")).thenReturn(false);
        when(bpe.encode("plaintext123")).thenReturn("hashedPassword");
        when(userRepo.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User savedUser = userService.addUser(mockUser);

        // Assert
        assertThat(savedUser.getPassword()).isEqualTo("hashedPassword");
        assertThat(savedUser.getIsTemporaryPassword()).isTrue();
        assertThat(savedUser.getIsSuspended()).isFalse();

        verify(userRepo).save(any(User.class));
    }


    @Test
    void shouldThrowExceptionWhenEmailAlreadyExists() {
        // Arrange
        when(userRepo.existsByEmail("john@example.com")).thenReturn(true);

        // Act & Assert
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.addUser(mockUser);
        });

        assertThat(exception.getMessage()).isEqualTo("Email is already in use.");
        verify(userRepo, never()).save(any());
    }


    // RETRIEVE
    @Test
    void userFoundAndCredentialsMatch() {
        User inputUser = new User();
        inputUser.setEmail("test@example.com");
        inputUser.setPassword("password123");

        User retrievedUser = new User();
        retrievedUser.setEmail("test@example.cm");
        retrievedUser.setPassword("$2a$12$hashedPassword"); // Example of bcrypt hash

        when(userRepo.findByEmail(inputUser.getEmail())).thenReturn(retrievedUser);
        when(bpe.matches(inputUser.getPassword(), retrievedUser.getPassword())).thenReturn(true);

        User result = userService.findByEmail(inputUser);

        assertNotNull(result);
        assertEquals(retrievedUser, result);
        verify(userRepo, times(1)).findByEmail(inputUser.getEmail());
        verify(bpe, times(1)).matches(inputUser.getPassword(), retrievedUser.getPassword());
    }


    @Test
    void testFindByEmail_UserNotFound() {
        User inputUser = new User();
        inputUser.setEmail("test@example.com");
        inputUser.setPassword("password123");

        when(userRepo.findByEmail(inputUser.getEmail())).thenReturn(null);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByEmail(inputUser);
        });

        assertEquals("User not found.", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(inputUser.getEmail());
        verifyNoInteractions(bpe);
    }


    @Test
    void testFindByEmail_InvalidPassword() {
        User inputUser = new User();
        inputUser.setEmail("test@example.com");
        inputUser.setPassword("password123");

        User retrievedUser = new User();
        retrievedUser.setEmail("test@example.com");
        retrievedUser.setPassword("$2a$12$hashedPassword"); // Example of bcrypt hash

        when(userRepo.findByEmail(inputUser.getEmail())).thenReturn(retrievedUser);
        when(bpe.matches(inputUser.getPassword(), retrievedUser.getPassword())).thenReturn(false);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            userService.findByEmail(inputUser);
        });

        assertEquals("Invalid Password.", exception.getMessage());
        verify(userRepo, times(1)).findByEmail(inputUser.getEmail());
        verify(bpe, times(1)).matches(inputUser.getPassword(), retrievedUser.getPassword());
    }


}
