package com.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "notes") // Tablo adını açıkça belirtmek iyidir
public class Note {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String url;

    private LocalDateTime createdAt;

    // HATA PAYINI SIFIRLAMAK İÇİN:
    // private Long userId; -> Bu satırı SİLİYORUZ.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private User user;

    // Not kaydedilmeden hemen önce tarihi otomatik atayalım
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }
}