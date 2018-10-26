function Model(host) {
    this.host = host;

    this.onShowUserName = new EventEmitter();

    this.init = function(){
        this.getUserName();
    }
}

/*обработка данных в модели и проход по всем слушателям (изменяющимся элементам на странице) этими данными*/
Model.prototype = {
    getUserName: function(){
        let that = this;
        $.get( this.host + "/username",
            {}, function (data) {
                let username = data.replace(/"/g, "");
                that.onShowUserName.notify(username);
        });
    }
};



