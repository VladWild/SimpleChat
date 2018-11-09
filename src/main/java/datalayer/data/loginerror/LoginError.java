package datalayer.data.loginerror;

import lombok.ToString;

import java.util.Objects;

@ToString
public class LoginError {
    private TypeLoginError typeLoginError;
    private String message;


    public LoginError(TypeLoginError typeLoginError, String message) {
        this.typeLoginError = typeLoginError;
        this.message = message;
    }

    public TypeLoginError getTypeLoginError() {
        return typeLoginError;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        LoginError that = (LoginError) o;
        return typeLoginError == that.typeLoginError;
    }

    @Override
    public int hashCode() {
        return Objects.hash(typeLoginError);
    }
}



