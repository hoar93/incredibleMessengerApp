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

@Controller
public class MessengerMessagesController {

    private MessengerConversationService messengerConversationService;

    @Autowired
    public MessengerMessagesController(MessengerConversationService messengerConversationService) {
        this.messengerConversationService = messengerConversationService;
    }


    @RequestMapping(path = "/messengerMessages/{convId}", method = RequestMethod.GET)
    public String showOneMessages(
            @PathVariable("convId") Long convId,
            Model model) {

        //TODO nincs KÉSZ a metódus
        //List<ConversationMessage> conversationMessages = messengerConversationService.getMessageList(convId);
        Conversation conversation = messengerConversationService.getConversation(convId);
        ConversationMessage convm = new ConversationMessage();
        model.addAttribute("conversationMessage", convm);
        model.addAttribute("conversation",conversation);

        return "oneConversation";
    }



    @PostMapping("/createConvMessage")
    public String showCreateConvMess(ConversationMessage conversationMessage, Model model) {
        model.addAttribute("conversationMessage", conversationMessage);
        //conversationMessage.setConversation(); TODO itt tartok
        messengerConversationService.createConvMessage(conversationMessage);
        System.out.println("mivan");
        return "messengerMessages" /*conversationMessage.getConversation().getId()*/;
    }


    @GetMapping("/createconversation")
    public String showCreateConv(Model model) {
        model.addAttribute("conversation", new Conversation());
        return "createConversation";
    }

    @PostMapping("/createconversation")
    public String createConv(@Valid @ModelAttribute("conversation")Conversation conversation) {

        messengerConversationService.createConv(conversation);

        return "messengerMessages" + conversation.getId();
    }

}
