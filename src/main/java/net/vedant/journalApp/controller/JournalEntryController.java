package net.vedant.journalApp.controller;

import net.vedant.journalApp.entity.JournalEntry;
import net.vedant.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @PostMapping
    public JournalEntry createJournalEntry(@RequestBody JournalEntry journalEntity) {
        journalEntity.setDate(LocalDateTime.now());
        journalEntryService.createEntry(journalEntity);
        return journalEntity;
    }

    @GetMapping
    public List<JournalEntry> getAll() {
        return journalEntryService.getAll();
    }

    @GetMapping("/id/{id}")
    public JournalEntry getJournalById(@PathVariable ObjectId id) {
        return journalEntryService.getJournalById(id).orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteJournalById(@PathVariable ObjectId id) {
        journalEntryService.deleteJournalById(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateJournal(@PathVariable ObjectId id, @RequestBody JournalEntry journalEntry) {
        return journalEntryService.updateJournal(id, journalEntry);
    }
}
