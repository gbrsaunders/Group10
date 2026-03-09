package com.hackathon.hertrack.repository;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.ForumPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostRepo extends JpaRepository<ForumPost, Long> {
    List<ForumPost> findAllByOrderByScoreDesc();
    List<ForumPost> findByAccount(Account account);
}
