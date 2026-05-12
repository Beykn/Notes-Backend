package com.demo.demo.service;

import com.demo.demo.model.User;
import com.demo.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class AuthServiceTest {
    
    @Mock
    private UserRepository userRepository;
    
    @Mock
    private PasswordEncoder passwordEncoder;
    
    @Mock
    private JwtService jwtService;
    
    @Mock
    private CustomUserDetailService customUserDetailService;

    //IMPORTANT
    @InjectMocks
    private AuthService authService;
    
    @Test
    void register_ShouldSaveUser_WhenUsernameDoesNotExists(){
        //ARRANGE
        String username = "new_user";
        String password = "123";
        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.empty());
        Mockito.when(passwordEncoder.encode(password)).thenReturn("encoded_123");
        
        //ACT
        String result = authService.register(username, password);
        
        //ASSERT
        assertEquals("User created!", result);
        Mockito.verify(userRepository, Mockito.times(1)).save(any(User.class));
    }

    @Test
    void login_ShouldReturnToken_WhenCredentialsAreCorrect() {
        // ARRANGE
        String username = "test_user";
        String password = "correct_password";
        User mockUser = new User();
        mockUser.setUsername(username);
        mockUser.setPassword("encoded_password");

        UserDetails mockDetails = Mockito.mock(UserDetails.class);

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        Mockito.when(passwordEncoder.matches(password, "encoded_password")).thenReturn(true);
        Mockito.when(customUserDetailService.loadUserByUsername(username)).thenReturn(mockDetails);
        Mockito.when(jwtService.generateToken(mockDetails)).thenReturn("fake-jwt-token");

        // ACT
        String token = authService.login(username, password);

        // ASSERT
        assertNotNull(token);
        assertEquals("fake-jwt-token", token);
    }

    @Test
    void login_ShouldThrowException_WhenPasswordIsWrong() {
        // ARRANGE
        String username = "test_user";
        String password = "wrong_password";
        User mockUser = new User();
        mockUser.setPassword("encoded_password");

        Mockito.when(userRepository.findByUsername(username)).thenReturn(Optional.of(mockUser));
        Mockito.when(passwordEncoder.matches(password, "encoded_password")).thenReturn(false);

        // ACT & ASSERT
        RuntimeException exception = assertThrows(RuntimeException.class, () ->
                authService.login(username, password)
        );
        assertEquals("Wrong Password", exception.getMessage());
    }



}
