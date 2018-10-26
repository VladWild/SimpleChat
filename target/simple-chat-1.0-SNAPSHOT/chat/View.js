function View(model) {
    this.model = model;

    this.username = $("ins")[0];

    this.init = function(){
        let that = this;

        /*отправки наблюдателям подписанных элементов*/
        function subscription(){
            that.model.onShowUserName.subscribe(function (username) {
                that.showUserName(username);
            })
        }

        /*добавление событий элементам*/
        function event(){

        }

        subscription();
        event();
    }
}

/*отображение данных*/
View.prototype = {
    showUserName: function (username) {
        this.username.innerHTML = username;
    }
};

