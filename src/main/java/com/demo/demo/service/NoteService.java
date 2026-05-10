package com.demo.demo.service;

import com.demo.demo.model.Note;
import com.demo.demo.repository.NoteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NoteService {

    private final NoteRepository noteRepository;

    public NoteService(NoteRepository noteRepository){
        this.noteRepository = noteRepository;
    }

    public Note createNote(Note note){
        return noteRepository.save(note);
    }

    public List<Note> getNotesByUserId(Long userId){
        return noteRepository.findByUserId(userId);
    }

    public void deleteById(Long noteId){
        noteRepository.deleteById(noteId);
    }
}
