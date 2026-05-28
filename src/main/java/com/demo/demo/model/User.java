package com.demo.demo.model;

import com.demo.demo.model.enums.Role;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
//application.properties de "spring.jpa.hibernate.ddl-auto=update" ayarı var o yüzden table name güncellenirken hibernate hata vermetecek
@Table(name = "auth_user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String password;
    // User.java içinde ilgili kısım
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Note> notes = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Role role;
}

