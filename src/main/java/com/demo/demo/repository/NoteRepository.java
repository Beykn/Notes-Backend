package com.demo.demo.repository;

import com.demo.demo.model.Note;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // find all notes for specific user
    List<Note> findByUserId(Long userId);

    void deleteById(Long noteId);
}
