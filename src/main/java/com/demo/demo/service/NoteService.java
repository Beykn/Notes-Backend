package com.demo.demo.service;

import com.demo.demo.exception.ResourceNotFoundException;
import com.demo.demo.model.Note;
import com.demo.demo.model.User;
import com.demo.demo.repository.NoteRepository;
import com.demo.demo.repository.UserRepository;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;
    private final UserRepository userRepository;

    public NoteService(NoteRepository noteRepository, UserRepository userRepository){
        this.noteRepository = noteRepository;
        this.userRepository = userRepository;
    }

    public Note createNote(Note note, Long userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        note.setUser(user);

        return noteRepository.save(note);
    }

    public List<Note> getNotesByUserId(Long userId){
        return noteRepository.findByUser_Id(userId);
    }

    public void deleteById(Long noteId){
        noteRepository.deleteById(noteId);
    }

    public Note createNoteForAuthenticatedUser(Note note, String authenticatedUsername) {
        //Username bilgisine göre veritabanından kullanıcıyı buluyoruz
        User user = userRepository.findByUsername(authenticatedUsername)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + authenticatedUsername));

        //Not nesnesine, bulduğumuz kullanıcıyı bağlıyoruz
        note.setUser(user);

        // Notu veritabanına kaydediyoruz
        return noteRepository.save(note);
    }

    public void deleteNoteIfOwned(Long noteId, String authenticatedUserName) {

        // 1 . Silinmek istenen notu veritabanından buluyoruz.
        Note note = noteRepository.findById(noteId)
                .orElseThrow(() -> new ResourceNotFoundException("Note not found! ID: " + noteId));
                // 2. Güvenlik kontrolü : Notun sahibinin kulllanıcı adıyla istek atanın adıyla eşleşiyor mu ?
                if (!note.getUser().getUsername().equals(authenticatedUserName)){
                    throw new org.springframework.security.access.AuthorizationServiceException(
                            "You have not permission for delete this note !"
                    );
                }
                noteRepository.deleteById(noteId);

    }
}
