package net.vedant.journalApp.controller;

import net.vedant.journalApp.entity.Journal;
import net.vedant.journalApp.service.JournalService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalController {

    @Autowired
    private JournalService journalService;

    @PostMapping
    public ResponseEntity<Journal> createJournalEntry(@RequestBody Journal journalEntity) {
        try {
            journalEntity.setDate(LocalDateTime.now());
            journalService.createEntry(journalEntity);
            return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<Journal>> getAll() {
        List<Journal> all = journalService.getAll();

        if(all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<Journal> getJournalById(@PathVariable ObjectId id) {
        Optional<Journal> journalById = journalService.getJournalById(id);
        if (journalById.isPresent()) {
            return new ResponseEntity<>(journalById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<Journal> deleteJournalById(@PathVariable ObjectId id) {
        journalService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<Journal> updateJournal(@PathVariable ObjectId id, @RequestBody Journal journal) {
        Journal journal1 = journalService.updateJournal(id, journal);
        if (journal1 == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(journal1);
    }
}
