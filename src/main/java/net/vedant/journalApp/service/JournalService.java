package net.vedant.journalApp.service;

import net.vedant.journalApp.entity.Journal;
import net.vedant.journalApp.repository.JournalRepository;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    public void createEntry(Journal journal) {
        journalRepository.save(journal);
    }

    public List<Journal> getAll() {
        return journalRepository.findAll();
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    public void deleteJournalById(ObjectId id) {
        journalRepository.deleteById(id);
    }

    public Journal updateJournal(ObjectId id, Journal journal) {
        Journal existing = journalRepository.findById(id).orElse(null);

        if (existing == null) {
            return null;
        }

        existing.setTitle(journal.getTitle());
        existing.setContent(journal.getContent());
        existing.setDate(LocalDateTime.now());

        return journalRepository.save(existing);
    }


}
