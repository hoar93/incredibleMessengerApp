package edu.progmatic.messageapp.controllers;

import edu.progmatic.messageapp.modell.ConversationMessage;
import edu.progmatic.messageapp.services.MessengerConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
        ConversationMessage conversationMessage = messengerConversationService.getMessageList(convId);

        model.addAttribute(conversationMessage);

        //TODO nincs KÉSZ az oldal
        return "oneConversation";
    }
}
