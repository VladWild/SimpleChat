package dto.chat;

import lombok.ToString;

@ToString
public class MassageData {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}



