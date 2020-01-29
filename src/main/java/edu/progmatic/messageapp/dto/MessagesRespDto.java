package edu.progmatic.messageapp.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class MessagesRespDto {

    @NotBlank
    @NotNull
    private String text;

    @NotBlank
    @NotNull
    private String topicName;


    public MessagesRespDto() {
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getTopicName() {
        return topicName;
    }

    public void setTopicName(String topicName) {
        this.topicName = topicName;
    }
}