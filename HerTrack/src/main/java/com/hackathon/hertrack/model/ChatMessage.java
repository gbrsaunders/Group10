package com.hackathon.hertrack.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table
@Data
@NoArgsConstructor
public class ChatMessage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String sender;
    private String content;
    private String room;
    private LocalDateTime localdatetime;

    public ChatMessage(String sender,
                       String content,
                       String room) {
        this.sender = sender;
        this.content = content;
        this.room = room;
    }
    @PrePersist
    protected void onCreate() {
        this.localdatetime = LocalDateTime.now();
    }

}