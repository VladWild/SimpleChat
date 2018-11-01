function Model(url, uri, update) {

    this.url = url;
    this.uri = uri;
    this.update = update;

    this.onShowUserName = new EventEmitter();
    this.onShowMessages = new EventEmitter();
    this.onShowUsers = new EventEmitter();
    this.onSendMessage = new EventEmitter();
    this.onClickUser = new EventEmitter();
    this.onClickLogout = new EventEmitter();

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
        /*let that = this;
        $.get(this.uri, {command: "data"}, function (dynamicData) {
            let data = JSON.parse(dynamicData);
            that.onShowMessages.notify(data.messages);
            that.onShowUsers.notify(data.users);
        });*/
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
            success: function (initData) {
                let data = JSON.parse(initData);
                that.onShowUserName.notify(data.userName);
                that.onShowMessages.notify(data.messages);
                that.onShowUsers.notify(data.users);
            }
        });
    },
    sendMessage: function (message) {
        /*console.log(message);
        $.get(this.uri, {command: "sendmessage", text: message}, function (messagesData) {
            //that.validate.response(this.url, messagesData);
            let messages = JSON.parse(messagesData);
            that.onSendMessage.notify(messages);
        });*/

        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "sendmessage", text: message},
            type: 'get',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (initData) {
                let messages = JSON.parse(messagesData);
                that.onSendMessage.notify(messages);
            }
        });
    },
    clickUserName: function (clickUserName) {
        this.onClickUser.notify(clickUserName);
    },
    kickUser: function (kickUserName) {
        /*$.get(this.uri, {command: "kick", kickUserName: kickUserName}, function (newData) {
            //that.validate.response(this.url, newData);
            let data = JSON.parse(newData);
            that.onShowMessages.notify(data.messages);
            that.onShowUsers.notify(data.users);
        });*/

        let that = this;
        $.ajax({
            url: this.uri,
            data: {command: "sendmessage", kickUserName: kickUserName},
            type: 'get',
            error: function(XMLHttpRequest){
                if (XMLHttpRequest.status === 401) {
                    $(location).attr('href', that.url);
                }
            },
            success: function (initData) {
                let data = JSON.parse(newData);
                that.onShowMessages.notify(data.messages);
                that.onShowUsers.notify(data.users);;
            }
        });
    },
    logout: function () {
        /*$.get(this.uri, {command: "logout"}/!*, function (urn) {
            that.validate.response(this.url, urn);
        }*!/);*/

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




