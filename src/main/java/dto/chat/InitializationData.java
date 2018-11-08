package dto.chat;

import datalayer.data.massage.Message;
import lombok.Getter;
import lombok.ToString;

@ToString
public class InitializationData extends DynamicData {
    @Getter private String userName;

    public InitializationData(String userName, Message[] messages, String[] users) {
        super(messages, users);
        this.userName = userName;
    }
}

