class Tags {
    static getUser(username){
        return `<div class="user">
                    <a href="javascript:void(0);">${username}</a>
                    <button aria-label="${username}" class="kick">
                        <span aria-valuetext="${username}">kick</span>
                    </button>
                </div>`;
    }
}