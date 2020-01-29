package edu.progmatic.messageapp.controllers;

import edu.progmatic.messageapp.services.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ModifyController {

    private MessageService messageService;

    /*@GetMapping("/modifymessage")
    public String modifyMessage(
            @RequestParam(name = "id", required = false) Long id,
            @RequestParam(name = "text", required = false) String text,
            @RequestParam(name = "sleep", required = false) Long sleep,
            Model model) throws InterruptedException {
        messageService.modifyMessage(id, text, sleep);
        return "redirect:/messages";
    }*/

    @Autowired
    public ModifyController(MessageService messageService) {
        this.messageService = messageService;
    }
}
