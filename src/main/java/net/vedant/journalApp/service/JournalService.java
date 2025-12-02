package net.vedant.journalApp.service;

import net.vedant.journalApp.entity.Journal;
import net.vedant.journalApp.entity.User;
import net.vedant.journalApp.repository.JournalRepository;
import net.vedant.journalApp.repository.UserRepository;
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

    @Autowired
    private UserRepository userRepository;

    public void createJournal(String userName, Journal journal) {
        User user = userRepository.findByUserName(userName);
        journal.setDate(LocalDateTime.now());
        Journal savedJournal = journalRepository.save(journal);
        user.getJournalEntries().add(savedJournal);
        userRepository.save(user);
    }

    public List<Journal> JournalsOfUser(String userName) {
        User user = userRepository.findByUserName(userName);
        return user.getJournalEntries();
    }

    public Optional<Journal> getJournalById(ObjectId id) {
        return journalRepository.findById(id);
    }

    public void deleteJournalById(String userName, ObjectId id) {
        User user = userRepository.findByUserName(userName);
        user.getJournalEntries().removeIf(x -> x.getId().equals(id));
        userRepository.save(user);
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
