package edu.progmatic.messageapp.controllers;

import edu.progmatic.messageapp.dto.MessageAllDto;
import edu.progmatic.messageapp.dto.MessageDto;
import edu.progmatic.messageapp.modell.Message;
import edu.progmatic.messageapp.modell.Topic;
import edu.progmatic.messageapp.services.MessageService;
import edu.progmatic.messageapp.services.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@RestController
@RequestMapping("/rest/messages")
public class RestMessengerController {

    private MessageService messageService;
    private TopicService topicService;

    @Autowired
    public RestMessengerController(MessageService messageService, TopicService topicService) {
        this.messageService = messageService;
        this.topicService = topicService;
    }

    @RequestMapping(method = RequestMethod.PUT, path = ("/{id}"))
    public void editMessage(@PathVariable("id") Long msgId,
                            @RequestBody MessageDto m) throws InterruptedException {
        messageService.modifyMessage(msgId, m.getText());

    }

    @RequestMapping(method = RequestMethod.POST)
    public void newMessage(@RequestBody MessageDto m) {
        //Message newM = new Message();
        messageService.createMessage(m);
    }

    @RequestMapping(method = RequestMethod.GET, path = ("/{id}"))
    public MessageAllDto oneMessageById(@PathVariable("id") Long msgId) {

        MessageAllDto thisIsTheMessage = messageService.getMessage(msgId);
        return thisIsTheMessage;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<MessageAllDto> allMessages() {
        List<MessageAllDto> allMessages = messageService.getAllMessages();
        return allMessages;
    }
}
