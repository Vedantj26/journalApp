package net.vedant.journalApp.controller;

import net.vedant.journalApp.entity.JournalEntry;
import net.vedant.journalApp.service.JournalEntryService;
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
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry journalEntity) {
        try {
            journalEntity.setDate(LocalDateTime.now());
            journalEntryService.createEntry(journalEntity);
            return new ResponseEntity<>(journalEntity, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping
    public ResponseEntity<List<JournalEntry>> getAll() {
        List<JournalEntry> all = journalEntryService.getAll();

        if(all != null && !all.isEmpty()) {
            return ResponseEntity.ok(all);
        }

        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getJournalById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalById = journalEntryService.getJournalById(id);
        if (journalById.isPresent()) {
            return new ResponseEntity<>(journalById.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<JournalEntry> deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteJournalById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {
        JournalEntry journalEntry1 = journalEntryService.updateJournal(id, journalEntry);
        if (journalEntry1 == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(journalEntry1);
    }
}
