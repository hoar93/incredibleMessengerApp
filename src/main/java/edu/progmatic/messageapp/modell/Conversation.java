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

    @NotNull
    @NotBlank
    private Long starterId;

    @NotNull
    @NotBlank
    private Long partnerId;

    @OneToMany
    private List<ConversationMessages> conversationMessages;

}
