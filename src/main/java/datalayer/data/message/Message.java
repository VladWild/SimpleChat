package datalayer.data.message;

import datalayer.data.User;

import java.util.Date;

public class Message {
    private String text;
    private TypeMessage type;
    private User user;
    private Date date;

    public Message(String text, TypeMessage type, User user, Date date) {
        this.text = text;
        this.type = type;
        this.user = user;
        this.date = date;
    }

    public String getText() {
        return text;
    }

    public TypeMessage getType() {
        return type;
    }

    public User getUser() {
        return user;
    }

    public Date getDate() {
        return date;
    }
}

