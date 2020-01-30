package edu.progmatic.messageapp.services;

import edu.progmatic.messageapp.modell.Conversation;
import edu.progmatic.messageapp.modell.ConversationMessage;
import edu.progmatic.messageapp.modell.Topic;
import edu.progmatic.messageapp.modell.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessengerConversationService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = em.find(Conversation.class, convId);
        return oneConversation;
    }

    @Transactional
    public void createConvMessage(Long convId, ConversationMessage conversationMessage) {

        conversationMessage.setConversation(em.find(Conversation.class, convId));
        em.persist(conversationMessage);
    }

    @Transactional
    public List<Conversation> getAllConvs() {
        List<Conversation> allConvs = em.createQuery("SELECT c FROM Conversation c").getResultList();

        return allConvs;
    }

    @Transactional //TODO ez faszság left join fetch
    public List<ConversationMessage> getMessageList(Long convId) {
        List<ConversationMessage> oneConversation = em.createQuery("SELECT m FROM ConversationMessage m join fetch m.conversation where m.conversation.id = :convId")
                .setParameter("convId", convId)
                .getResultList();


        return oneConversation; //TODO egy beszélgetéshez.
                     // id alapján üzeneteket összeszed,
                     // időrendbe szed(úgy van alapból?)
    }

    @Transactional
    public void createConv(Conversation conversation) {
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        conversation.setConvStarter(currentUser.getUsername());
        em.persist(conversation);
    }
}
