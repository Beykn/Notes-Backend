package com.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;
import java.time.Instant;



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

    private Instant createdAt;

    // HATA PAYINI SIFIRLAMAK İÇİN:
    // private Long userId; -> Bu satırı SİLİYORUZ.

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false) //name silindi, parametre güncellendi arka planda user_id eşlemesini kendi kendine yapcağı için sorun yaratmaz
    @JsonBackReference
    private User user;

    // Not kaydedilmeden hemen önce tarihi otomatik atayalım
    @PrePersist
    protected void onCreate() {
        //Sunucu nerede olursa olsun her zaman ortak saat olan UTC yi kaydeder
        createdAt = Instant.now();
    }
}