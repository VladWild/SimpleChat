package dto.chat;

import lombok.ToString;

@ToString
public class KickUserData {
    private String kickUserName;

    public String getKickUserName() {
        return kickUserName;
    }

    public void setKickUserName(String kickUserName) {
        this.kickUserName = kickUserName;
    }
}
