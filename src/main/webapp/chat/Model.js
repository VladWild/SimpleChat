function Model(uri, update) {
    this.uri = uri;
    this.update = update;

    this.onShowUserName = new EventEmitter();
    this.onShowMessages = new EventEmitter();
    this.onShowUsers = new EventEmitter();
    this.onSendMessage = new EventEmitter();
    this.onClickUser = new EventEmitter();
    this.onClickLogout = new EventEmitter();

    this.init = function(){
        this.initData();
        /*setInterval(function () {
            this.dynamicData();
        }.bind(this), this.update);*/
    }
}

/*обработка данных в модели и проход по всем слушателям (изменяющимся элементам на странице) этими данными*/
Model.prototype = {
    initData: function(){
        let that = this;
        $.get( this.uri, {command: "login"}, function (initData) {
            let data = JSON.parse(initData);
            that.onShowUserName.notify(data.userName);
            that.onShowMessages.notify(data.messages);
            that.onShowUsers.notify(data.users);
        });
    },
    dynamicData: function(){
        let that = this;
        $.get( this.uri, {command: "data"}, function (dynamicData) {
            let data = JSON.parse(dynamicData);
            that.onShowMessages.notify(data.messages);
            that.onShowUsers.notify(data.users);
        });
    },
    sendMessage: function (message) {
        let that = this;
        console.log(message);
        $.get( this.uri, {command: "sendmessage", text: message}, function (messagesData) {
            console.log(messagesData);
            let messages = JSON.parse(messagesData);
            that.onSendMessage.notify(messages);
        });
    },
    clickUserName: function (clickUserName) {
        this.onClickUser.notify(clickUserName);
    },
    kickUser: function (kickUserName) {
        let that = this;
        $.get( this.uri, {command: "kick", kickUserName: kickUserName}, function (newData) {
            let data = JSON.parse(newData);
            that.onShowMessages.notify(data.messages);
            that.onShowUsers.notify(data.users);
        });
    },
    logout: function () {
        let that = this;
        $.get( this.uri, {command: "logout"});
    }
};




