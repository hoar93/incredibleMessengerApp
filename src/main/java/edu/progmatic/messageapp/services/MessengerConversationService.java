package edu.progmatic.messageapp.services;

import edu.progmatic.messageapp.modell.Conversation;
import edu.progmatic.messageapp.modell.ConversationMessage;
import edu.progmatic.messageapp.modell.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MessengerConversationService {

    @PersistenceContext
    EntityManager em;

    @Transactional
    public List<User> allUser() {
        List<User> userList = em.createQuery("select u from User u").getResultList();
        return userList;
    }

    @Transactional
    public Conversation getConversation(Long convId) {
        Conversation oneConversation = em.find(Conversation.class, convId);
        return oneConversation;
    }

    @Transactional //TODO már nem szar
    public void createConvMessage(Long convId, ConversationMessage conversationMessage) {

        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();

        conversationMessage.setAuthor(loggedInUserName);
        String partner = getConversation(convId).getConvPartner();

        if (loggedInUserName.equals(conversationMessage.getAuthor())) {
            conversationMessage.setPartner(partner);
        }
        if(loggedInUserName.equals(partner)) {
            conversationMessage.setPartner(getConversation(convId).getConvStarter());
        }

        conversationMessage.setCreationDate(LocalDateTime.now());
        conversationMessage.setConversation(em.find(Conversation.class, convId));
        em.persist(conversationMessage);
    }

    @Transactional
    public List<Conversation> gettAllForThem() {
        List<Conversation> all = getAllConvs();
        List<Conversation> them = new ArrayList<>();
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        for (Conversation conversation : all) {
            if (conversation.getConvStarter().equals(loggedInUserName)) {
                them.add(conversation);
            } else if(conversation.getConvPartner().equals(loggedInUserName)) {
                them.add(conversation);
            }
        }
        return them;
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
    public void createConv(Conversation conversation) { //TODO dto 1.3
        User currentUser = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        conversation.setConvStarter(currentUser.getUsername());
        em.persist(conversation);
    }
}
