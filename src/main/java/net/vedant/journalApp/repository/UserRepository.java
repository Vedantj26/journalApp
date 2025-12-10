package net.vedant.journalApp.repository;

import net.vedant.journalApp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    User findByUserName(String userName);
    void deleteByUserName(String userName);
}
