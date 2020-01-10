// Use Vue plugin - optional
Vue.use(VeeValidate);

// the page is data driven using v-* attributes

var appVM = new Vue({
    el: '#app',
    data: {
        user: {
            "username": null,
            "role": null
        },
        users: [],
        edit_user: {},
        showUserEditor: false,
        flights: [], // refernce data
        search_flight: { // maintenace list
            airport_id: null,
            airport_id_filter: null
        }, // search form
        flightlist: [],
        search_reservation: {
            username: null,
            airline_id: null,
            flight_id: null,
            departure_date: null
        }, // search form
        reservations: [],
        tickets: {},
        reports: {
            top_customers: [],
            top_flights: [],
            revenue_by: [{ "revenue": 0 }],
        },
        search_report: {
            month: null,
            airline: null,
            flight: null,
            username: null,
        },
        message: "",
        showMessage: false,
        errorMessage: false,
        currentPage: 'report',
    },
    // special function will be automatcially invoked when you create Vue instance.  
    created: function() {
        if (window.location.hash) {
            let page = window.location.hash.split("#")[1];
            console.log("page: " + page);
            if (page.length > 0)
                this.currentPage = page;
            else
                this.currentPage = 'home';
        }
        window.addEventListener('hashchange', () => {
            this.currentPage = window.location.hash.split("#")[1];
        });
        this.initData();
    },
    computed: {
        filteredFlightlist: function() {
            // return this.flightlist;
            if (this.search_flight.airport_id_filter) {
                this.search_flight.airport_id_filter = this.search_flight.airport_id_filter.toUpperCase();
                return this.flightlist.filter((f) => {
                    return f.departure_airport_id.localeCompare(this.search_flight.airport_id_filter) == 0 ||
                        f.arrival_airport_id.localeCompare(this.search_flight.airport_id_filter) == 0
                });
            } else {
                return this.flightlist;
            }
        }
    },
    methods: {
        initData: async function() {
            console.log("init data");
            let this_month = ((new Date().getYear()) + 1900) + '-' + ((new Date().getMonth()) + 1);
            this.search_report.month = this_month;
            this.getRevenueByMonth();
            console.log(this_month);
            let response, data;
            // fetch user
            response = await fetch('/user');
            this.user = await response.json();
            console.log(this.user);
            this.loadUser();
            this.loadFlight();
            this.getReports();
        },
        getReports: async function() {
            response = await fetch('/admin/report/top_customers');
            this.reports.top_customers = await response.json();
            response = await fetch('/admin/report/top_flights');
            this.reports.top_flights = await response.json();
        },
        getRevenueByMonth: async function() {
            response = await fetch('/admin/report/revenue_by_month?month=' +
                this.search_report.month);
            this.reports.revenue_by = await response.json();
        },
        getRevenueByAirline: async function() {
            response = await fetch('/admin/report/revenue_by_airline?airline_id=' +
                this.search_report.airline_id);
            this.reports.revenue_by = await response.json();
        },
        getRevenueByFlight: async function() {
            response = await fetch('/admin/report/revenue_by_flight?airline_id=' +
                this.search_report.airline_id +
                '&flight_id=' + this.search_report.flight_id);
            this.reports.revenue_by = await response.json();
        },
        getRevenueByCustomer: async function() {
            response = await fetch('/admin/report/revenue_by_customer?username=' +
                this.search_report.username);
            this.reports.revenue_by = await response.json();
        },
        loadUser: async function() {
            response = await fetch('/admin/user');
            this.users = await response.json();
        },
        editUser: function(user) {
            let u = Object.assign({}, user);
            this.edit_user = u;
            this.showUserEditor = true;
        },
        upsertUser: async function() {
            if (this.edit_user.username == null) {
                this.displayMessage("Error: A username is required.", "error");
                return;
            }
            if (this.edit_user.password == null) {
                this.displayMessage("Error: A password is required.", "error");
                return;
            }

            // create new airport
            let response = await fetch("/admin/user", {
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
            console.dir(this.edit_user);
            this.loadUser();
            this.showUserEditor = false;
        },
        deleteUser: async function(u) {
            let response = await fetch("/admin/user", {
                method: 'DELETE', // *GET, POST, PUT, DELETE, etc.
                mode: 'cors', // no-cors, *cors, same-origin
                cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                credentials: 'same-origin', // include, *same-origin, omit
                headers: {
                    'Content-Type': 'application/json'
                        // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: 'follow', // manual, *follow, error
                referrer: 'no-referrer', // no-referrer, *client
                body: JSON.stringify(u) // body data type must match "Content-Type" header
            });
            await response.json();
            this.loadUser();
            console.log("deleted user: " + u);
        },
        loadFlight: async function() {
            // fetch airlines
            let airports = [];
            response = await fetch('/flight');
            data = await response.json();
            this.flightlist = data;
            console.log(data);

            // setup refernce data
            let flights = [];
            data.forEach((f) => {
                flights[f.airline_id + '-' + f.flight_id + '-' + f.departure_weekday] = f;
            });
            this.flights = flights;
        },
        getFlight: function(f) {
            console.log("get flight: " + f);
            let ff = this.flights[f.split('-')[0] + '-' + f.split('-')[1] + '-' + f.split('-')[2]];
            if (!ff) {
                console.log("Error: " + f);
                console.log(JSON.stringify(this.flights));
            }
            return ff;
        },
        toReservation: async function() {
            let url = '/admin/reservation?';
            if (this.search_reservation.username != null &&
                this.search_reservation.username.length > 0
            ) {
                url += 'username=' + this.search_reservation.username;
            } else if (this.search_reservation.airline_id != null &&
                this.search_reservation.airline_id.length > 0 &&
                this.search_reservation.flight_id != null &&
                this.search_reservation.flight_id.length > 0 &&
                this.search_reservation.departure_date != null &&
                this.search_reservation.departure_date.length > 0
            ) {
                url += 'airline_id=' + this.search_reservation.airline_id;
                url += '&flight_id=' + this.search_reservation.flight_id;
                url += '&departure_date=' + this.search_reservation.departure_date;
            } else {
                this.displayMessage("Must include customer username or flight information to search reservations!");
                return;
            }
            let response = await fetch(url);
            this.reservations = await response.json();
            this.tickets = [];
            this.currentPage = 'reservation';
        },
        deleteReservation: async function(id) {
            let response = await fetch("/reservation/" + id, {
                method: 'DELETE'
            });
            await response.json();
            console.log("deleted reservation: " + id);
            this.toReservation();
        },
        getTickets: async function(id) {
            fetch('/admin/tickets/' + id)
                .then((response) => {
                    response.json().then((data) => {
                        this.tickets = data;
                    });
                });
        },
        getDepartureDate: function(f) {
            let departure_date = convertDate(this.getRouteDate(f));
            let departure_weekday = (departure_date.getDay() + 6) % 7;
            let flight = this.flights[f.split('-')[0] + '-' + f.split('-')[1] + '-' + f.split('-')[2]];
            departure_date.setDate(departure_date.getDate() + (flight.departure_weekday - departure_weekday + 7) % 7);
            return yyyymmdd(departure_date);
        },
        getArrivalDate: function(f) {
            let departure_date = convertDate(this.getDepartureDate(f));
            let departure_weekday = (departure_date.getDay() + 6) % 7;
            let flight = this.flights[f.split('-')[0] + '-' + f.split('-')[1] + '-' + f.split('-')[2]];
            departure_date.setDate(departure_date.getDate() + (flight.arrival_weekday - departure_weekday + 7) % 7);
            return yyyymmdd(departure_date);
        },
        getRouteDate: function(f) {
            return f.split('-')[3];
        },
        clearCart: function() {
            this.cart = [];
            localStorage.setItem('myCart', JSON.stringify(this.cart));
        },
        logout: function() {
            this.clearCart();
        },
        isAdmin: function() {
            return this.user && this.user.roles &&
                this.user.roles.includes('admin');
        },
        isCustomerRep: function() {
            return this.user && this.user.roles &&
                this.user.roles.includes('customer_representative');
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

function yyyymmdd(date) {
    return date.getFullYear() + "/" + (date.getMonth() + 1) + "/" + date.getDate();
};

function convertDate(date) {
    // console.log("orignal date: "+date);
    let d = new Date();
    d.setFullYear(date.split('/')[0]);
    d.setMonth(parseInt(date.split('/')[1]) - 1);
    d.setDate(parseInt(date.split('/')[2]));
    // console.log("converted departure date: "+d);
    return d;
};