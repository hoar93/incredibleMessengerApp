package edu.progmatic.messageapp.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@Controller
public class MessengerHomeController {
    @RequestMapping(value = {"/messengerHome"}, method = GET)
    public String messengerHome(Model model){
        model.addAttribute("greeting", "szia" );
        return "messengerHome";
    }

    @RequestMapping(value = {"/messengerMessages"}, method = GET)
    public String messengerMessages() {
        return "messengerMessages";
    }
}


