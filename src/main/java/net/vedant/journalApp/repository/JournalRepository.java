package net.vedant.journalApp.repository;

import net.vedant.journalApp.entity.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface JournalRepository extends JpaRepository<Journal, UUID> {
}
