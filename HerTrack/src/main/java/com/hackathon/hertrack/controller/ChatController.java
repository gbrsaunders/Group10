package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.Account;
import com.hackathon.hertrack.model.ChatMessage;
import com.hackathon.hertrack.model.Comment;
import com.hackathon.hertrack.model.ForumPost;
import com.hackathon.hertrack.repository.ChatRepository;
import com.hackathon.hertrack.repository.CommentRepo;
import com.hackathon.hertrack.repository.PostRepo;
import jakarta.servlet.http.HttpSession;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    private final PostRepo postRepo;
    private final CommentRepo commentRepo;

    public ChatController(ChatRepository chatRepository,
                          PostRepo postRepo,
                          CommentRepo commentRepo) {
        this.chatRepository = chatRepository;
        this.postRepo = postRepo;
        this.commentRepo = commentRepo;
    }

    @MessageMapping("/sendMessage/general")
    @SendTo("/topic/messages/general")
    public ChatMessage sendMessageGeneral(@Payload ChatMessage message) {
        message.setRoom("general");
        return chatRepository.save(message);
    }

    @MessageMapping("/sendMessage/beauty")
    @SendTo("/topic/messages/beauty")
    public ChatMessage sendMessageBeauty(@Payload ChatMessage message) {
        message.setRoom("beauty");
        return chatRepository.save(message);
    }

    @MessageMapping("/sendMessage/relationships")
    @SendTo("/topic/messages/relationships")
    public ChatMessage sendMessageRelationships(@Payload ChatMessage message) {
        message.setRoom("relationships");
        return chatRepository.save(message);
    }

    @MessageMapping("/sendMessage/endometriosis")
    @SendTo("/topic/messages/endometriosis")
    public ChatMessage sendMessageEndo(@Payload ChatMessage message) {
        message.setRoom("endometriosis");
        return chatRepository.save(message);
    }

    @GetMapping("/chat/{roomName}")
    public String chat(@PathVariable String roomName,
                       Model model) {
        switch (roomName) {
            case "beauty" -> {
                List<ChatMessage> allBeautyWellChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("beauty");
                model.addAttribute("allChats", allBeautyWellChats);
                return "beautyChat";
            }
            case "relationships" -> {
                List<ChatMessage> allRelationshipChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("relationships");
                model.addAttribute("allChats", allRelationshipChats);
                return "relationshipsChat";
            }
            case "endometriosis" -> {
                List<ChatMessage> allEndoChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("endometriosis");
                model.addAttribute("allChats", allEndoChats);
                return "endoChat";
            }
            default -> {
                List<ChatMessage> allGeneralChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("general");
                model.addAttribute("allChats", allGeneralChats);
                return "GeneralChat";
            }
        }

    }

    // Forum page
    @RequestMapping("/forum")
    public String forum(HttpSession session,
                        Model model) {

        /* Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        } */

        List<ForumPost> allPosts = postRepo.findAllByOrderByScoreDesc();

        model.addAttribute("posts", allPosts);
        return "forum";
    }

    @GetMapping("/forum/create-post")
    public String createPostForm(HttpSession session) {
        /* Account account = (Account) session.getAttribute("account");
        if (account == null) {
            return "redirect:/login";
        } */

        return "/app/create-post";
    }

    @PostMapping("/forum/create-post")
    public String createPost(@RequestParam String title,
                             @RequestParam String content,
                             @RequestParam String category,
                             HttpSession session,
                             RedirectAttributes redirectAttributes) {

        /*
        Account sessionAccount = (Account) session.getAttribute("account");
        if (sessionAccount == null) {
            return "redirect:/login";
        }
         */

        ForumPost post = new ForumPost();
        post.setTitle(title);
        post.setContent(content);
        post.setCategory(category);
        postRepo.save(post);

        redirectAttributes.addFlashAttribute("success",
                "Post created successfully");
        return "redirect:/forum";
    }

    @PostMapping("/forum/vote")
    public String votePost(@RequestParam Long postId,
                           @RequestParam int vote) {
        postRepo.findById(postId).ifPresent(post -> {
            post.setScore(post.getScore() + vote);
            postRepo.save(post);
        });
        return "redirect:/forum";
    }

    @PostMapping("/forum/comment")
    public String addComment(@RequestParam Long postId,
                             @RequestParam String text) {
        postRepo.findById(postId).ifPresent(post -> {
            Comment comment = new Comment();
            comment.setText(text);
            comment.setForumPost(post);
            commentRepo.save(comment);
        });
        return "redirect:/forum/" + postId;
    }

    @PostMapping("/forum/comment-vote")
    public String voteComment(@RequestParam Long postId,
                              @RequestParam Long commentId,
                              @RequestParam int vote) {
        commentRepo.findById(commentId).ifPresent(comment -> {
            comment.setScore(comment.getScore() + vote);
            commentRepo.save(comment);
        });
        return "redirect:/forum/" + postId;
    }

    @GetMapping("/forum/{id}")
    public String viewPost(@PathVariable Long id,
                           Model model) {
        List<ForumPost> allPosts = postRepo.findAllByOrderByScoreDesc();
        model.addAttribute("posts", allPosts);
        model.addAttribute("expandedPostId", id);
        return "forum";
    }

}
