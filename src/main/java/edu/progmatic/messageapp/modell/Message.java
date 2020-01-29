package edu.progmatic.messageapp.modell;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

//    @Size(max = 500, min = 3 , message = "Not between {2} and {1} characters!")
//    @NotNull
    //@Pattern(regexp ="^[A-Z]\\w* [A-Z]\\w*", message = "Not a valid name!")
    private String author;

    @NotNull
    @NotBlank
    private String text;

    private boolean isDeleted;

    @ManyToOne
    private Topic topic;

    @DateTimeFormat(pattern = "yyyy/MMMM/dd HH:mm")
    private LocalDateTime creationDate;

    @ManyToOne
    private Message parent;

    @JsonIgnore
    @OneToMany(mappedBy = "parent")
    private List<Message> comments;

    public Message() {
    }

    public Message(String author, String text, LocalDateTime creationDate) {
        this.author = author;
        this.text = text;
        this.creationDate = creationDate;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setDeleted(boolean deleted) {
        isDeleted = deleted;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public Topic getTopic() {
        return topic;
    }

    public void setTopic(Topic topic) {
        this.topic = topic;
    }

    public Message getParent() {
        return parent;
    }

    public void setParent(Message parent) {
        this.parent = parent;
    }

    public List<Message> getComments() {
        return comments;
    }

    public void setComments(List<Message> comments) {
        this.comments = comments;
    }
}
