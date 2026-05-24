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

    @PostMapping("/create")
    public ResponseEntity<Note> createNote(@RequestBody Note note) {
        // SecurityContext ' te kullanıcıyı kontrol ediyorum
        String authenticatedUserEmail = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();

        // Servis katmanına ID yerine bu güvenli email/username bilgisini geçiyoruz
        return ResponseEntity.ok(noteService.createNoteForAuthenticatedUser(note, authenticatedUserEmail));
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Note>> getUserNotes(@PathVariable Long userId) {
        return ResponseEntity.ok(noteService.getNotesByUserId(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        String authenticatedUserName = org.springframework.security.core.context.SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getName();
        noteService.deleteNoteIfOwned(id, authenticatedUserName);
        return ResponseEntity.ok().build();
    }
}