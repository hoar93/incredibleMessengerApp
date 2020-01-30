package edu.progmatic.messageapp.controllers;

import edu.progmatic.messageapp.modell.Conversation;
import edu.progmatic.messageapp.services.MessengerConversationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;


@Controller
public class MessengerHomeController {

    private MessengerConversationService messengerConversationService;

    @Autowired
    public MessengerHomeController(MessengerConversationService messengerConversationService) {
        this.messengerConversationService = messengerConversationService;
    }

    //TODO kinda jo, de a bejelentkezés után is ide kéne redirectelni
    @RequestMapping(value = {"/", "/messengerHome"}, method = GET)
    public String messengerHome(Model model) {
        model.addAttribute("greeting", "szia");
        return "messengerHome";
    }

    //TODO kinda jo
    @RequestMapping(value = "/messengerMessages", method = GET)
    public String messengerMessages(Model model) {
        List<Conversation> allConvs = messengerConversationService.getAllConvs();
        model.addAttribute("allconvs", allConvs);

        return "messengerMessages";
    }

    //TODO jónak jó, de dupla. msgs controllerben.
    /*
    @RequestMapping(value = {"/createConversation"}, method = GET)
    public String createConversation() {
        return "createConversation";
    }
    */


}


