package com.demo.demo.repository;

import com.demo.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    //find by user name , we will use this for login part
    Optional<User> findByUsername(String username);

    // Kullanıcıyı ID'sine göre silmek için:
    void deleteById(Long id);

    // Kullanıcıyı kullanıcı adına göre silmek için:
    void deleteByUsername(String username);
}
