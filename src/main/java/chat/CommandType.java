package chat;

import chat.commands.*;

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
            return new Logout();
        }
    };

    private static final String COMMAND = "command";

    abstract protected Command getCommandChat();

    public static CommandType getTypeCommandByRequest(HttpServletRequest request){
        return valueOf((request.getParameter(COMMAND)).toUpperCase());
    }

    public static Command getCommandChat(CommandType commandType){
        return commandType.getCommandChat();
    }
}
