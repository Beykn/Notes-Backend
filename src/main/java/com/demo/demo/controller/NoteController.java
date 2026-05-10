package com.demo.demo.controller;


import com.demo.demo.model.Note;
import com.demo.demo.service.NoteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private final NoteService noteService;

    public NoteController(NoteService noteService) {
        this.noteService = noteService;
    }

    @PostMapping("/create/{userId}")
    public ResponseEntity<Note> createNote(@RequestBody Note note, @PathVariable Long userId) {
        return ResponseEntity.ok(noteService.createNote(note, userId));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getUserNotes(@PathVariable Long userId) {
        return ResponseEntity.ok(noteService.getNotesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        noteService.deleteById(id);
        return ResponseEntity.ok().build();
    }
}