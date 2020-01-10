var appVM = new Vue({
    el: '#app',
    data: {
        edit_user: {
            username: null,
            password: null,
            password_confirm: null
        },
        message: "",
        showMessage: false,
        errorMessage: false
    },
    // special function will be automatcially invoked when you create Vue instance.  
    created: function() {},
    computed: {},
    methods: {
        adduser: async function() {
            if (this.edit_user.username == null || this.edit_user.username.length == 0) {
                this.displayMessage("Error: username is required.", "error");
                return;
            }
            if (this.edit_user.password == null || this.edit_user.password.length == 0) {
                this.displayMessage("Error: password is required.", "error");
                return;
            }
            if (this.edit_user.password_confirm == null) {
                this.displayMessage("Error: password confirmation is required.", "error");
                return;
            }
            if (this.edit_user.password.localeCompare(this.edit_user.password_confirm) != 0) {
                this.displayMessage("Error: password does NOT match.", "error");
                return;
            }

            // create new airport
            let response = await fetch("/user", {
                method: 'POST', // *GET, POST, PUT, DELETE, etc.
                mode: 'cors', // no-cors, *cors, same-origin
                cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                credentials: 'same-origin', // include, *same-origin, omit
                headers: {
                    'Content-Type': 'application/json'
                        // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: 'follow', // manual, *follow, error
                referrer: 'no-referrer', // no-referrer, *client
                body: JSON.stringify(this.edit_user) // body data type must match "Content-Type" header
            });
            await response.json();
            if (response.status != 200) {
                this.displayMessage("Error: this username already exists, choose another username.", "error");
            } else {
                window.location.href = "/login";
            }
        },
        displayMessage: function(message, error) {
            this.message = message;
            this.showMessage = true;
            if (error)
                this.errorMessage = true;
            else
                this.errorMessage = false;
            setTimeout(this.hideMessage, 3000);
        },
        hideMessage: function() {
            this.showMessage = false;
        },
    }
});