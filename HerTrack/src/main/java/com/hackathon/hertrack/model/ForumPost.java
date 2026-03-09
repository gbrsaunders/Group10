package com.hackathon.hertrack.model;
import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table
@Data
@NoArgsConstructor
public class ForumPost {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;
    private String content;
    private String category;
    private int score = 0;
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Account account;

    @OneToMany(mappedBy = "forumPost",
    cascade = CascadeType.ALL,
    fetch = FetchType.LAZY)
    private List<Comment> comments = new ArrayList<>();

    @Transient
    public String getAuthor() {
        return account != null ? account.getUsername() : "Anonymous";
    }

    @Transient
    public String getTime() {
        return createdAt != null ? createdAt.toLocalDate().toString() : "";
    }

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
