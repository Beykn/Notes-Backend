package com.demo.demo.service;

import com.demo.demo.model.Note;
import com.demo.demo.model.User;
import com.demo.demo.repository.NoteRepository;
import com.demo.demo.repository.UserRepository;
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
}
