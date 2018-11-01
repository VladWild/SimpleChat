package dto;

import datalayer.data.message.Message;
import lombok.Getter;

public class InitializationData extends DynamicData{
    @Getter private String userName;

    public InitializationData(String userName, Message[] messages, String[] users) {
        super(messages, users);
        this.userName = userName;
    }
}

