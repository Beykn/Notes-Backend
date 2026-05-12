package com.demo.demo.service;

import com.demo.demo.model.Note;
import com.demo.demo.model.User;
import com.demo.demo.repository.NoteRepository;
import com.demo.demo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

@ExtendWith(MockitoExtension.class) // Sahte nesne
class NoteServiceTest {

    @Mock // Veritabanının sahtesini oluşturuldu
    private UserRepository userRepository;

    @Mock // Veritabanının sahtesini oluşturuldu
    private NoteRepository noteRepository;

    @InjectMocks // Sahteleri NoteService'in içine enjekte edildi
    private NoteService noteService;

    @Test
    void createNote_ShouldReturnNote() {
        // 1. Sahte veriyi ayarla
        User user = new User();
        user.setId(1L);
        Note note = new Note();

        // Sahte userRepository'e "1L id'si gelirse bu user'ı dön"
        Mockito.when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        // Sahte noteRepository'e "kayıt gelirse aynen geri dön"
        Mockito.when(noteRepository.save(any())).thenReturn(note);

        // 2. Metodu çağır
        Note sonuc = noteService.createNote(note, 1L);

        // 3. KONTROL: Sonuç boş değilse ve hata vermediyse YEŞİL TİK!
        assertNotNull(sonuc);
        assertEquals(user, sonuc.getUser());
    }
}