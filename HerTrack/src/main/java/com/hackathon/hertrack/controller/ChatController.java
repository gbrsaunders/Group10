package com.hackathon.hertrack.controller;

import com.hackathon.hertrack.model.ChatMessage;
import com.hackathon.hertrack.repository.ChatRepository;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class ChatController {

    private final ChatRepository chatRepository;
    public ChatController(ChatRepository chatRepository) {
        this.chatRepository = chatRepository;
    }
    // Gets the chat message from the json and saves it to the chat rep
    @MessageMapping("/sendMessage/general")
    @SendTo("/topic/messages/general")
    public ChatMessage sendMessageGeneral(@Payload ChatMessage message) {
        message.setRoom("general");
        System.out.println("Message is recieved" + message);
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
    public String chat(@PathVariable String roomName, Model model) {
        // Switches to the different channels including the AI
        switch (roomName) {
            case "beauty" -> {
                List<ChatMessage> allBeautyWellChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("beauty");
                model.addAttribute("allChats", allBeautyWellChats);
                //model.addAttribute("accountID", accountID);
                return "beautyChat";
            }
            case "relationships" -> {
                List<ChatMessage> allRelationshipChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("relationships");
                model.addAttribute("allChats", allRelationshipChats);
                //model.addAttribute("accountID", accountID);
                return "relationshipsChat";
            }
            case "endometriosis" -> {
                List<ChatMessage> allEndoChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("endometriosis");
                model.addAttribute("allChats", allEndoChats);
                //model.addAttribute("accountID", accountID);
                return "endoChat";
            }
            case "ai" -> { return "aiChat"; }
            default -> {
                List<ChatMessage> allGeneralChats = chatRepository.findTop10ByRoomOrderByLocaldatetimeAsc("general");
                model.addAttribute("allChats", allGeneralChats);
                //model.addAttribute("accountID", accountID);
                return "GeneralChat";
            }
        }

    }

    @RequestMapping("/forum")
    public String forum() {
        return "forum";
    }
}
