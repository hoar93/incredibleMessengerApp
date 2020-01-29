package edu.progmatic.messageapp.services;

import edu.progmatic.messageapp.dto.MessageAllDto;
import edu.progmatic.messageapp.dto.MessageDto;
import edu.progmatic.messageapp.exceptions.MessageNotFoundException;
import edu.progmatic.messageapp.modell.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.security.access.prepost.PostFilter;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MessageService {

    @PersistenceContext
    EntityManager em;

    @Autowired
    private MessageService self;
    @Autowired
    private TopicService topicService;


    private static final Logger LOGGER = LoggerFactory.getLogger(MessageService.class);

//    private List<Message> messages = new ArrayList<>();
//
//    {
//        messages.add(new Message("Aladár", "Mz/x jelkezz, jelkezz", LocalDateTime.now().minusDays(10)));
//        messages.add(new Message("Kriszta", "Bemutatom lüke Aladárt", LocalDateTime.now().minusDays(5)));
//        messages.add(new Message("Blöki", "Vauuu", LocalDateTime.now()));
//        messages.add(new Message("Maffia", "miauuu", LocalDateTime.now()));
//        messages.add(new Message("Aladár", "Kapcs/ford", LocalDateTime.now().plusDays(5)));
//        messages.add(new Message("Aladár", "Adj pénzt!", LocalDateTime.now().plusDays(10)));
//    }

    @PostFilter("(hasRole('ADMIN') and #showDeleted == true) or filterObject.deleted == false")
    public List<Message> filterMessages(Long id, String author, String text, LocalDateTime from, LocalDateTime to, Integer limit, String orderBy, String order, boolean showDeleted) {
        LOGGER.info("filterMessages method started");
        LOGGER.debug("id: {}, author: {}, text: {}", id, author, text);

        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Message> cQuery = cb.createQuery(Message.class);
        Root<Message> m = cQuery.from(Message.class);
        //TODO predicate listával megcsinálni ue-zt, akármi is az

        if (!StringUtils.isEmpty(author)) {
            cQuery.select(m).where(cb.equal(m.get(Message_.AUTHOR), author));
        }
        if (!StringUtils.isEmpty(text)) {
            cQuery.select(m).where(cb.equal(m.get(Message_.TEXT), text));
        }
        //TODO proba
        /*if (!id.equals(null)) {
            cQuery.select(m).where(cb.equal(m.get(Message_.id), id));
        }*/

        /*  TODO creationdate hogy?
        if (!from.equals(null)) {
            cQuery.select(m).where(m.get(Message_.creationDate.))
        }*/


        //TODO ETTŐL predicate séma
        /*
        List<Predicate> predicates = new ArrayList<>();
        //..
        if (!StringUtils.isEmpty(author)) {
            predicates.add(cb.equal(m.get(Message_.author), author));
        }
        //..
        cQuery.select(m).where(cb.and(predicates.toArray(new Predicate[0])));
        */
        //TODO EDDIG predicate séma




        if (orderBy.equals("author")){
            if(order.equals("desc")) {
                cQuery.orderBy(cb.desc(m.get(Message_.author)));
            } else {
                cQuery.orderBy(cb.asc(m.get(Message_.author)));
            }
        }

        //TODO minden alapján szűrjön

        /*
        List<Message> messages = em.createQuery("SELECT m FROM Message m", Message.class).getResultList();
        Comparator<Message> msgComp = Comparator.comparing((Message::getCreationDate));
        LOGGER.debug("filterMessages is going to compare...");
        switch (orderBy) { //TODO RENDEZÉS (nem todo)
            case "text":
                LOGGER.trace("comparing by text");
                msgComp = Comparator.comparing((Message::getText));
                break;
            case "id":
                LOGGER.trace("comparing by id");
                msgComp = Comparator.comparing((Message::getId));
                break;
            case "author":
                LOGGER.trace("comparing by author");
                msgComp = Comparator.comparing((Message::getAuthor));
                break;
            default:
                break;
        }
        if (order.equals("desc")) {
            msgComp = msgComp.reversed();
        }

        List<Message> msgs = messages.stream() //TODO filterezés (nem todo)
                .filter(m -> id == null ? true : m.getId().equals(id))
                .filter(m -> StringUtils.isEmpty(author) ? true : m.getAuthor().contains(author))
                .filter(m -> StringUtils.isEmpty(text) ? true : m.getText().contains(text))
                .filter(m -> from == null ? true : m.getCreationDate().isAfter(from))
                .filter(m -> to == null ? true : m.getCreationDate().isBefore(to))
                .sorted(msgComp)
                .limit(limit).collect(Collectors.toList());


        return msgs;

                    */

        List<Message> msgs = em.createQuery(cQuery).getResultList();
        return msgs;
    }


    @Transactional
    public MessageDto getMessageToModify(Long msgId) {
        Message newM = em.find(Message.class, msgId);
        MessageDto message = self.mToDto(newM);

        return message;
    }
    @Transactional
    public void modifyMessage(Long id, String text) throws InterruptedException {
        //MessageDto m = self.getMessageToModify(id);
        self.getMessageM(id).setText(text);
        //m.setText(text);

    }

    @Transactional
    public Message getMessageM(Long msgId) {
        Message newM = em.find(Message.class, msgId);

        return newM;
    }

    @Transactional
    public MessageAllDto getMessage(Long msgId) throws MessageNotFoundException {
        Message newM = em.find(Message.class, msgId);
        if (newM == null) {
            throw new MessageNotFoundException();
        }
        MessageAllDto message = self.mToAllDto(newM);

        return message;
    }

    private MessageDto mToDto(Message m) {
        MessageDto newM = new MessageDto();
        newM.setText(m.getText());
        newM.setTopicName(m.getTopic().getTitle());
        return newM;
    }

    private MessageAllDto mToAllDto(Message m) {
        MessageAllDto newM = new MessageAllDto();
        newM.setAuthor(m.getAuthor());
        newM.setId(m.getId());
        newM.setText(m.getText());
        newM.setTopicName(m.getTopic().getTitle());
        return newM;
    }

    private Message allDtoToM(MessageAllDto m) {
        Message newM = new Message();
        newM.setTopic(topicService.getTopicByTitle(m.getTopicName()));
        newM.setText(m.getText());
        newM.setAuthor(m.getAuthor());
        return newM;
    }
    @Transactional
    public List<MessageAllDto> getAllMessages() {
        List<Message> allMs= em.createQuery("SELECT m FROM Message m").getResultList();
        List<MessageAllDto> allDtoMs = new ArrayList<>();
        for (Message m : allMs) {
            allDtoMs.add(mToAllDto(m));
        }
        return allDtoMs;
    }

    @Transactional
    public Message createMessage(@Valid MessageDto x) {
        Message m = new Message();
        m.setTopic(topicService.getTopicByTitle(x.getTopicName()));
        m.setText(x.getText());
        String loggedInUserName = SecurityContextHolder.getContext().getAuthentication().getName();
        m.setAuthor(loggedInUserName);
        m.setCreationDate(LocalDateTime.now());
//        m.setId((long) messages.size());
        //m.setTopic(topic);
        em.persist(m);
        return m;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @Transactional
    public Boolean deleteMessage(long messageId) {

        Message message = em.find(Message.class, messageId);
        if (message != null) {
            message.setDeleted(true);
            return true;
        } else {
            return false;
        }
    }
/*
    @Transactional
    public void addNewComment(long parentMessageId, Message comment) {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        Message parentMessage = getMessage(parentMessageId);
        comment.setAuthor(user.getUsername());
        comment.setParent(parentMessage);
        comment.setTopic(parentMessage.getTopic());
        comment.setCreationDate(LocalDateTime.now());

        em.persist(comment);
    }*/
}
