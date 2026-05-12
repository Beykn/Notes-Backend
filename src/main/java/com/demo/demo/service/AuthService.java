package com.demo.demo.service;

import com.demo.demo.model.User;
import com.demo.demo.model.enums.Role;
import com.demo.demo.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final CustomUserDetailService customUserDetailService; // Bunu ekledik

    public AuthService(UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService,
                       CustomUserDetailService customUserDetailService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.customUserDetailService = customUserDetailService;
    }

    public String register(String username, String password) {
        // Kontrol: Kullanıcı adı zaten alınmış mı?
        if (userRepository.findByUsername(username).isPresent()) {
            throw new RuntimeException("Kullanıcı adı zaten mevcut!");
        }

        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password));
        user.setRole(Role.USER);
        userRepository.save(user);

        return "User created!";
    }

    public String login(String username, String password) {
        // 1. Kullanıcıyı bul
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found!"));

        // 2. Şifreyi kontrol et
        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new RuntimeException("Wrong Password");
        }

        // 3. UserDetails oluşturmak için daha önce yazdığımız servisi kullan (Kod tekrarından kaçın)
        UserDetails userDetails = customUserDetailService.loadUserByUsername(username);

        // 4. Token üret ve dön
        return jwtService.generateToken(userDetails);
    }
}