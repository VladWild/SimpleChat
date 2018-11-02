package dto.output;

import datalayer.data.message.Message;
import lombok.Getter;
import lombok.ToString;

@ToString
public class DynamicData {
    @Getter private Message[] messages;
    @Getter private String[] users;

    public DynamicData(Message[] messages, String[] users) {
        this.messages = messages;
        this.users = users;
    }
}




