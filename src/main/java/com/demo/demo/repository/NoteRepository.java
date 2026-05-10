package com.demo.demo.repository;

import com.demo.demo.model.Note;
import com.demo.demo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoteRepository extends JpaRepository<Note, Long> {
    // find all notes for specific user
    List<Note> findByUser_Id(Long userId);

    void deleteById(Long noteId);
}
