package net.vedant.journalApp.service;

import net.vedant.journalApp.entity.Journal;
import net.vedant.journalApp.entity.User;
import net.vedant.journalApp.repository.JournalRepository;
import net.vedant.journalApp.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Component
public class JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public void createJournal(String userName, Journal journal) {
        try {
            User user = userRepository.findByUserName(userName);
            journal.setDate(LocalDateTime.now());
            Journal savedJournal = journalRepository.save(journal);
            user.getJournalEntries().add(savedJournal);
            userRepository.save(user);
        } catch (Exception e) {
            throw new RuntimeException("An error occurred while creating a journal.", e);
        }
    }

    public List<Journal> JournalsOfUser(String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getJournalEntries();
    }

    public Optional<Journal> getJournalById(UUID id) {
        return journalRepository.findById(id);
    }

    public void deleteJournalById(String userName, UUID id) {
        User user = userRepository.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userRepository.save(user);
        journalRepository.deleteById(id);
    }

    public Journal updateJournal(UUID id, Journal journal) {
        Optional<Journal> existingOpt = journalRepository.findById(id);
        if (existingOpt.isEmpty()) {
            return null;
        }
        Journal existing = existingOpt.get();
        existing.setTitle(journal.getTitle());
        existing.setContent(journal.getContent());
        existing.setDate(journal.getDate());
        // If you want to set/associate user: existing.setUser(journalEntry.getUser());
        return journalRepository.save(existing);
    }


}
