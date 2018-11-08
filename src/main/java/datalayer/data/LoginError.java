package datalayer.data;

import lombok.ToString;

@ToString
public class LoginError {
    private String key;
    private String value;

    public LoginError(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}



