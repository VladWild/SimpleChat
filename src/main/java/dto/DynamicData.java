package dto;

import datalayer.data.message.Message;
import lombok.Getter;

public class DynamicData {
    @Getter private Message[] messages;
    @Getter private String[] users;

    public DynamicData(Message[] messages, String[] users) {
        this.messages = messages;
        this.users = users;
    }
}


