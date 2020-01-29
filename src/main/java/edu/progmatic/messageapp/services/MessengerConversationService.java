package edu.progmatic.messageapp.services;

import edu.progmatic.messageapp.modell.ConversationMessage;
import org.springframework.stereotype.Service;

@Service
public class MessengerConversationService {
    public ConversationMessage getMessageList(Long convId) {
        return null; //TODO egy beszélgetéshez.
                     // id alapján üzeneteket összeszed,
                     // időrendbe szed(úgy van alapból?)
    }
}
