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
        message: "",
        showMessage: false,
        currentPage: 'home',
        choices: {
            "airlines": ["UA", "DL"]
        },
        filters: {
            "stops": "100",
            "airlines": ["UA", "DL"],
            "min_price": 0,
            "max_price": 100000,
            "departure_times": ["00-08", "08-18", "18-24"]
        },
        sort: 'price',
        departure_airport_id: 'EWR',
        show_departure_airport: false,
        arrival_airport_id: 'BCN',
        show_arrival_airport: false,
        departure_date: '2019-12-19',
        return_date: '2019-12-24',
        flex_date: true,
        showDetail: -1,
        showFilters: false,
        airports: [],
        airportlist: [],
        airlines: [],
        aircrafts: [],
        flights: [],
        result: [],
        cart: [],
        customer: 'cr',
        seat_class: "first",
        special_meal: "vegetarian",
        number_of_seats: 1,
        reservations: [],
        tickets: {}
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
        // this.fakeData();
        this.initData();
    },
    computed: {
        filteredResult: function() {
            // return this.result;
            let filtered = this.result.filter(
                (c) => {
                    return c.length - 1 <= this.filters.stops &&
                        c.every((f) => { return this.filters.airlines.includes(this.getFlight(f).airline_id) }) &&
                        (c.reduce((sum, f) => sum + parseFloat(this.getFlight(f).price), 0) >= this.filters.min_price) &&
                        (c.reduce((sum, f) => sum + parseFloat(this.getFlight(f).price), 0) <= this.filters.max_price) &&
                        this.filters.departure_times.some((t) => {
                            return (this.getFlight(c[0]).departure_time >= t.split("-")[0]) && (this.getFlight(c[0]).departure_time <= t.split("-")[1]);
                        });
                });
            // sort it
            if (this.sort == 'price') {
                filtered.sort((a, b) => a.reduce((sum, c) => sum + parseFloat(this.getFlight(c).price), 0) - b.reduce((sum, c) => sum + parseFloat(this.getFlight(c).price), 0));
            } else if (this.sort == 'departure') {
                filtered.sort((a, b) => (this.getFlight(a[0]).departure_time.localeCompare(this.getFlight(b[0]).departure_time)));
            } else if (this.sort == 'arrival') {
                filtered.sort((a, b) => (this.getFlight(a[a.length - 1]).arrival_time.localeCompare(this.getFlight(b[b.length - 1]).arrival_time)));
            }
            this.showDetail = -1;
            return filtered;
        },
        filteredDepartureAirport: function() {
            let filtered = this.airportlist;
            if (this.departure_airport_id.length > 2) {
                filtered = this.airportlist.filter(
                    (a) => {
                        return a.airport_name.toUpperCase().includes(this.departure_airport_id) ||
                            a.airport_id.toUpperCase().includes(this.departure_airport_id) ||
                            a.airport_tags.toUpperCase().includes(this.departure_airport_id);
                    });
            }
            return filtered;
        },
        filteredArrivalAirport: function() {
            let filtered = this.airportlist;
            if (this.arrival_airport_id.length > 2) {
                filtered = this.airportlist.filter(
                    (a) => {
                        return a.airport_name.toUpperCase().includes(this.arrival_airport_id) ||
                            a.airport_id.toUpperCase().includes(this.arrival_airport_id) ||
                            a.airport_tags.toUpperCase().includes(this.arrival_airport_id);
                    });
            }
            return filtered;
        }
    },
    methods: {
        initData: async function() {
            console.log("init data");
            console.log(document.referrer);
            let response, data;
            // fetch user
            response = await fetch('/user');
            this.user = await response.json();
            console.log(this.user);

            // if(document.referrer.endsWith('login')) {
            //   if(this.isAdmin()) {
            //     window.location.href = "/admin.html";
            //   } else if(this.isCustomerRep()) {
            //     window.location.href = "/customer_rep.html";
            //   } else {
            //   }
            // }

            // fetch airlines
            let airlines = [];
            response = await fetch('/airline');
            data = await response.json();
            data.forEach((a) => { airlines[a.airline_id] = a.airline_name; });
            this.airlines = airlines;
            console.dir(this.airlines);

            // fetch airports
            let airports = [];
            response = await fetch('/airport');
            data = await response.json();
            this.airportlist = data;
            data.forEach((a) => { airports[a.airport_id] = a; });
            this.airports = airports;
            console.dir(this.airports);

            // fetch aircrafts
            let aircrafts = [];
            response = await fetch('/aircraft');
            data = await response.json();
            data.forEach((a) => { aircrafts[a.aircraft_id] = a; });
            this.aircrafts = aircrafts;
            console.dir(this.aircrafts);

            // fetch flights
            let flights = [];
            response = await fetch('/flight');
            data = await response.json();
            data.forEach((f) => {
                flights[f.airline_id + '-' + f.flight_id + '-' + f.departure_weekday] = f;
            });
            this.flights = flights;

            let cart = localStorage.getItem('myCart');
            console.log(cart);
            if (cart) this.cart = JSON.parse(cart);
            // if (this.cart.length>0)
            //   this.currentPage = 'cart';
            // else
            //   this.currentPage = 'home';
        },
        search: async function() {
            let result = [];
            let airlines = new Set();
            let start = 0;
            let end = 0;
            if (this.flex_date) {
                start = -3;
                end = 3;
            }
            for (let i = start; i <= end; i++) {
                let d = new Date();
                d.setFullYear(this.departure_date.split('-')[0]);
                d.setMonth(parseInt(this.departure_date.split('-')[1]) - 1);
                d.setDate(parseInt(this.departure_date.split('-')[2]) + i);
                let departure_date = yyyymmdd(d);
                let response = await fetch('/search' +
                    '?da=' + this.departure_airport_id +
                    '&aa=' + this.arrival_airport_id +
                    '&dd=' + departure_date +
                    '&nl=' + '60' +
                    '&xl=' + '720');
                let data = await response.json();
                data.forEach((r) => {
                    result.push(
                        r.split(',').map((f) => { return f + '-' + departure_date; })
                    );
                });
                console.log(result);
                result.forEach((r) => {
                    r.forEach((f) => {
                        airlines.add(f.split('-')[0]);
                    })
                });
                // console.log(airlines);
            }

            this.choices.airlines = [...airlines].sort();
            this.filters.airlines = [...airlines];
            this.result = result;
        },
        searchReturn: async function() {
            let result = [];
            let airlines = new Set();
            let start = 0;
            let end = 0;
            if (this.flex_date) {
                start = -3;
                end = 3;
            }
            for (let i = start; i <= end; i++) {
                let d = new Date();
                d.setFullYear(this.return_date.split('-')[0]);
                d.setMonth(parseInt(this.return_date.split('-')[1]) - 1);
                d.setDate(parseInt(this.return_date.split('-')[2]) + i);
                let departure_date = yyyymmdd(d);
                let response = await fetch('/search' +
                    '?aa=' + this.departure_airport_id +
                    '&da=' + this.arrival_airport_id +
                    '&dd=' + departure_date +
                    '&nl=' + '60' +
                    '&xl=' + '90');
                let data = await response.json();
                data.forEach((r) => {
                    result.push(
                        r.split(',').map((f) => { return f + '-' + departure_date; })
                    );
                });
                console.log(result);
                result.forEach((r) => {
                    r.forEach((f) => {
                        airlines.add(f.split('-')[0]);
                    })
                });
                console.log(airlines);
            }

            this.choices.airlines = [...airlines].sort();
            this.filters.airlines = [...airlines];
            this.result = result;
        },
        addFlight: function(route) {
            this.cart.push(route);
            localStorage.setItem('myCart', JSON.stringify(this.cart));
            this.displayMessage("Your selection has been successfully added into your shopping cart.");
        },
        quickCheckout: function(route) {
            this.addFlight(route);
            this.currentPage = 'cart';
        },
        checkout: async function() {
            if (this.cart.length == 0) return;
            let reservation_id = uuid.v4();
            // SQL weekday monday(0) - sunday(6)
            // JS getDay sunday(0) - saturday(6)
            // We convert everything to SQL weekday to make query easier 
            let reservation = {
                "reservation_id": reservation_id,
                "username": this.customer,
                "departure_airport_id": this.getFlight(this.cart[0][0]).departure_airport_id,
                "departure_date": this.departure_date,
                "departure_time": this.getFlight(this.cart[0][0]).departure_time,
                "purchase_date": yyyymmdd(new Date()),
                "purchase_time": (new Date()).toLocaleTimeString('en-GB'),
                "total_fare": 0, // server will calculate it
                "fee": 0, // server will calculate it
                "seat_class": this.seat_class,
                "special_meal": this.special_meal,
                "booking_status": "pending"
            };
            console.dir(reservation);

            // create reservation with pending status
            let url = "";
            if (this.isCustomerRep()) url = url + "/cr";
            let response = await fetch(url + "/reservation", {
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
                body: JSON.stringify(reservation) // body data type must match "Content-Type" header
            });
            await response.json();
            console.log("created reservation: " + reservation_id);

            // add tickets/legs
            let tickets = [];
            for (let i = 0; i < this.number_of_seats; i++) {
                this.cart.forEach((ticket) => {
                    let ticket_id = uuid.v4();
                    ticket.forEach((l, index) => {
                        console.log("leg: " + l);
                        let departure_date = convertDate((this.getRouteDate(l)));
                        let departure_weekday = (departure_date.getDay() + 6) % 7;
                        departure_date.setDate(departure_date.getDate() + (this.getFlight(l).departure_weekday - departure_weekday + 7) % 7);
                        let leg = {
                            "reservation_id": reservation_id,
                            "ticket_id": ticket_id,
                            "leg_id": index,
                            "airline_id": this.getFlight(l).airline_id,
                            "flight_id": this.getFlight(l).flight_id,
                            "departure_weekday": this.getFlight(l).departure_weekday,
                            "departure_date": yyyymmdd(departure_date),
                            "price": 0, // server will calculate it
                            "booking_status": "pending"
                        };
                        console.dir(leg);
                        tickets.push(leg);
                    });
                });
            }

            // book all tickets, some may be waitlisted
            const promises = tickets.map(async(ticket) => {
                response = await fetch(url + "/ticket", {
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
                    body: JSON.stringify(ticket) // body data type must match "Content-Type" header
                });
                console.log("created ticket leg " + ticket.leg_id);
                return response.json();
            });
            await Promise.all(promises);

            response = await fetch(url + "/reservation", {
                method: 'PUT', // *GET, POST, PUT, DELETE, etc.
                mode: 'cors', // no-cors, *cors, same-origin
                cache: 'no-cache', // *default, no-cache, reload, force-cache, only-if-cached
                credentials: 'same-origin', // include, *same-origin, omit
                headers: {
                    'Content-Type': 'application/json'
                        // 'Content-Type': 'application/x-www-form-urlencoded',
                },
                redirect: 'follow', // manual, *follow, error
                referrer: 'no-referrer', // no-referrer, *client
                body: JSON.stringify(reservation) // body data type must match "Content-Type" header
            });
            let completed = await response.json();
            console.log("completed reservation " + completed.booking_status);

            // TODO prompt if it is waitlisted
            // TODO allow waitlisted reservation to be cancelled
            if (completed.booking_status.localeCompare('waitlist') == 0) {
                if (!confirm("Some of flights are out of ticket. Do you want to be placed on the waitlist?")) {
                    response = await fetch(url + "/reservation/" + completed.reservation_id, {
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
                        body: JSON.stringify(reservation) // body data type must match "Content-Type" header
                    });
                    completed = await response.json();
                    alert("Your reservation is cancelled.");
                    completed = null;
                }
            }

            if (completed != null) {
                this.clearCart();
                if (this.isCustomerRep()) {
                    window.location.replace("/customer_rep.html#reservation");
                } else if (this.isAdmin()) {
                    window.location.replace("/admin.html#reservation");
                } else {
                    this.toReservation();
                }
            }
        },
        removeFlight: function(index) {
            this.cart.splice(index, 1);
            localStorage.setItem('myCart', JSON.stringify(this.cart));
        },
        clearCart: function() {
            this.cart = [];
            localStorage.setItem('myCart', JSON.stringify(this.cart));
        },
        toReservation: async function() {
            let response = await fetch('/reservation');
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
            fetch('/tickets/' + id)
                .then((response) => {
                    response.json().then((data) => {
                        this.tickets = data;
                    });
                });
        },
        getFlight: function(f) {
            return this.flights[f.split('-')[0] + '-' + f.split('-')[1] + '-' + f.split('-')[2]];
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
        displayMessage: function(message) {
            this.message = message;
            this.showMessage = true;
            setTimeout(this.hideMessage, 1000);
        },
        hideMessage: function() {
            this.showMessage = false;
        },
        clickaway: function(e) {
            if (this.show_departure_airport && e.target.parentElement.id != 'departure_airport') {
                this.show_departure_airport = false;
            };
            if (this.show_arrival_airport && e.target.parentElement.id != 'arrival_airport') {
                this.show_arrival_airport = false;
            };
        }
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

// Non Vue code
function done() {
    $('.dialog').hide();
    // appVM.application.highschool = $('.dialog > input:checked').val();
};