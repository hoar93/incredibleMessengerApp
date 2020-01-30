package edu.progmatic.messageapp.controllers;

import edu.progmatic.messageapp.modell.Conversation;
import edu.progmatic.messageapp.modell.ConversationMessage;
import edu.progmatic.messageapp.services.MessengerConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Controller
public class MessengerMessagesController {

    private MessengerConversationService messengerConversationService;
    private Long conversationIdThatINeed;

    @Autowired
    public MessengerMessagesController(MessengerConversationService messengerConversationService) {
        this.messengerConversationService = messengerConversationService;
    }

    @PostMapping(path = "/createconversationmessage")
    public String createConversationMessage(
            @ModelAttribute("conversationMessage") ConversationMessage m) {

        messengerConversationService.createConvMessage(conversationIdThatINeed, m);
        return ("redirect:/messengerMessages/" + conversationIdThatINeed); //TODO redirect
    }


    @GetMapping(path = "/messengerMessages/{convId}") //TODO JO
    public String showOneMessages(
            @PathVariable("convId") Long convId,
            Model model) {
        conversationIdThatINeed = convId;
        List<ConversationMessage> convMessages = messengerConversationService.getMessageList(convId);
        Conversation conversation = messengerConversationService.getConversation(convId);
        //ConversationMessage convm = new ConversationMessage();
        //convm.setConversation(conversation);
        model.addAttribute("conversationMessages", convMessages);
        model.addAttribute("conversation", conversation);

        return "oneConversation";
    }

    @GetMapping("/createConversation") //TODO kinda jo, egyiket törölni. talán nem ezt?
    public String showCreateConv(Model model) {
        model.addAttribute("conversation", new Conversation());
        return "createConversation";
    }

    @PostMapping("/createconversation") //TODO kinda jo
    public String createConv(@Valid @ModelAttribute("conversation") Conversation conversation) {
        messengerConversationService.createConv(conversation);
        return "messengerMessages" + conversation.getId();
    }

}
