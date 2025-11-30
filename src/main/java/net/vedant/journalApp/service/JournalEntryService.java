package net.vedant.journalApp.service;

import net.vedant.journalApp.entity.JournalEntry;
import net.vedant.journalApp.repository.JournalEntryRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalEntryService {

    @Autowired
    private JournalEntryRepository journalEntryRepository;

    public void createEntry(JournalEntry journalEntry) {
        journalEntryRepository.save(journalEntry);
    }

    public List<JournalEntry> getAll() {
        return journalEntryRepository.findAll();
    }

    public Optional<JournalEntry> getJournalById(ObjectId id) {
        return journalEntryRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id) {
        journalEntryRepository.deleteById(id);
    }

    public JournalEntry updateJournal(ObjectId id, JournalEntry journalEntry) {
        JournalEntry existing = journalEntryRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setTitle(journalEntry.getTitle());
        existing.setContent(journalEntry.getContent());
        existing.setDate(LocalDateTime.now());

        return journalEntryRepository.save(existing);
    }


}
