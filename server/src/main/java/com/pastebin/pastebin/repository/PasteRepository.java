package com.pastebin.pastebin.repository;

import com.pastebin.pastebin.entity.Paste;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface PasteRepository extends JpaRepository<Paste,String> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT p FROM Paste p WHERE p.id = :id")
    Optional<Paste> findByIdForUpdate(String id);

}
