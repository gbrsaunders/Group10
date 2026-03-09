package com.hackathon.hertrack.repository;

import com.hackathon.hertrack.model.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ChatRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findTop10ByRoomOrderByLocaldatetimeAsc(String room);
}
