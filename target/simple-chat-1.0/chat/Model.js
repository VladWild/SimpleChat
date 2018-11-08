function Model(url, uri, update) {

    this.url = url;
    this.uri = uri;
    this.update = update;

    this.onShowUserName = new EventEmitter();
    this.onShowMessages = new EventEmitter();
    this.onShowUsers = new EventEmitter();
    this.onSendMessage = new EventEmitter();
    this.onClickUser = new EventEmitter();

    this.init = function () {
        this.initData();
        setInterval(function () {
            this.dynamicData();
        }.bind(this), this.update);
    }
}

/*обработка данных в модели и проход по всем слушателям (изменяющимся элементам на странице) этими данными*/
Model.prototype = {
    initData: function () {
        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "login"},
            type: 'get',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (initData) {
                let data = JSON.parse(initData);
                that.onShowUserName.notify(data.userName);
                that.onShowMessages.notify(data.messages);
                that.onShowUsers.notify(data.users);
            }
        });
    },
    dynamicData: function () {
        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "data"},
            type: 'get',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (dynamicData) {
                let data = JSON.parse(dynamicData);
                that.onShowMessages.notify(data.messages);
                that.onShowUsers.notify(data.users);
            }
        });
    },
    sendMessage: function (message) {
        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "sendmessage", text: message},
            type: 'post',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (messagesData) {
                let messages = JSON.parse(messagesData);
                that.onSendMessage.notify(messages);
            }
        });
    },
    clickUserName: function (clickUserName) {
        this.onClickUser.notify(clickUserName);
    },
    kickUser: function (kickUserName) {
        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "kick", kickUserName: kickUserName},
            type: 'post',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (initData) {
                let data = JSON.parse(initData);
                that.onShowMessages.notify(data.messages);
                that.onShowUsers.notify(data.users);
            }
        });
    },
    logout: function () {
        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "logout"},
            type: 'get',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            }
        });
    }
};




