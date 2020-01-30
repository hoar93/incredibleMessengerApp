package edu.progmatic.messageapp.modell;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Entity
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany
    private List<ConversationMessage> conversationMessages;

    private String convStarter;
    private String convPartner;

    public String getConvStarter() {
        return convStarter;
    }

    public void setConvStarter(String convStarter) {
        this.convStarter = convStarter;
    }

    public String getConvPartner() {
        return convPartner;
    }

    public void setConvPartner(String convPartner) {
        this.convPartner = convPartner;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<ConversationMessage> getConversationMessages() {
        return conversationMessages;
    }

    public void setConversationMessages(List<ConversationMessage> conversationMessages) {
        this.conversationMessages = conversationMessages;
    }

    public Conversation() {
    }
}
