package datalayer.data.massage;

import lombok.ToString;

import java.text.SimpleDateFormat;
import java.util.Date;

@ToString
public class Message {
    private static final String FORMAT = "hh:mm:ss";
    private static final SimpleDateFormat formatDate = new SimpleDateFormat(FORMAT);

    private String text;
    private TypeMessage type;
    private String userName;
    private String date;

    public Message(String text, TypeMessage type, String userName, Date date) {
        this.text = text;
        this.type = type;
        this.userName = userName;
        this.date = formatDate.format(date);
    }

    public String getText() {
        return text;
    }

    public TypeMessage getType() {
        return type;
    }

    public String getUserName() {
        return userName;
    }

    public String getDate() {
        return date;
    }
}

