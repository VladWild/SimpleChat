package chat;

import chat.commands.Data;
import chat.commands.Kick;
import chat.commands.Login;
import chat.commands.SendMessage;

import javax.servlet.http.HttpServletRequest;

public enum CommandType {
    LOGIN {
        @Override
        protected Command getCommandChat() {
            return new Login();
        }
    },
    DATA {
        @Override
        protected Command getCommandChat() {
            return new Data();
        }
    },
    KICK {
        @Override
        protected Command getCommandChat() {
            return new Kick();
        }
    }, SENDMESSAGE {
        @Override
        protected Command getCommandChat() {
            return new SendMessage();
        }
    }, LOGOUT {
        @Override
        protected Command getCommandChat() {
            return null;
        }
    };

    private static final String COMMAND = "command";

    abstract protected Command getCommandChat();

    public static Command getCommandChat(HttpServletRequest req){
        System.out.println(((String) req.getAttribute(COMMAND)).toUpperCase());
        return valueOf(((String) req.getAttribute(COMMAND)).toUpperCase()).getCommandChat();
    }
}