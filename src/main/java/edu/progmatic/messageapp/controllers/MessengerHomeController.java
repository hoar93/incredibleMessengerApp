package edu.progmatic.messageapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MessengerHomeController {
    @RequestMapping(value = {"/", "/messengerHome"}, method = GET)
    public String messengerHome(Model model){
        model.addAttribute("greeting", "szia" );
        return "messengerHome";
    }

    //TODO nincs KÉSZ az oldal, ez a beszélgetések listája
    @RequestMapping(value = "/messengerMessages", method = GET)
    public String messengerMessages() {
        return "messengerMessages";
    }

    //TODO nincs KÉSZ az oldal
    @RequestMapping(value = {"/createConversation"}, method = GET)
    public String createConversation() {
        return "createConversation";
    }



}


