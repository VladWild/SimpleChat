function View(model) {
    this.model = model;

    this.username = $("ins")[0];
    this.messages = $("textarea")[0];
    this.users = $(".users")[0];
    this.send = $("button")[0];
    this.field = $("input")[0];

    this.init = function(){
        let that = this;

        /*отправки наблюдателям подписанных элементов*/
        function subscription(){
            that.model.onShowUserName.subscribe(function (username) {
                that.showUserName(username);
            });
            that.model.onShowMessages.subscribe(function (messages) {
                that.showMessages(messages);
            });
            that.model.onShowUsers.subscribe(function (usernames) {
                that.showUsers(usernames);
            });
            that.model.onSendMessage.subscribe(function (messages, currentUser) {
                that.showMessages(messages);
            });
            that.model.onSendMessage.subscribe(function () {
                that.clearFieldInput();
            });
            that.model.onClickUser.subscribe(function (clickUserName) {
                that.addUserNameInField(clickUserName);
            });
        }

        /*добавление событий элементам*/
        function event(){
            that.send.onclick = function () {
                let message = that.field.value;
                that.model.sendMessage(message);
            };
            that.users.onclick = function (event) {
                let target = event.target;
                if (target.tagName === 'A'){
                    let clickUserName = target.innerHTML;
                    that.model.clickUserName(clickUserName);
                }
                if (target.tagName === 'SPAN') {
                    let user = target.getAttribute("aria-valuetext");
                    that.model.kickUser(user);
                    console.log(user);
                }
                if (target.tagName === 'BUTTON'){
                    let user = target.getAttribute("aria-label");
                    that.model.kickUser(user);
                    console.log(user);
                }
            };
        }

        subscription();
        event();
    }
}

/*отображение данных*/
View.prototype = {
    showUserName: function (username) {
        this.username.innerHTML = username;
    },
    showMessages: function (messages) {
        let lines = messages.map(function (message) {
            return `${message.date} ${message.type} "${message.userName}": ${message.text}`;
        });
        this.messages.value = lines.join("\n");
    },
    showUsers: function (users) {
        this.users.innerHTML = '';
        users.forEach(user =>
            this.users.innerHTML += Tags.getUser(user));
    },
    clearFieldInput: function () {
        this.field.value = '';
    },
    addUserNameInField(userName){
        this.field.value += `@${userName} `;
        console.log('addUserNameInField');
    }
};

