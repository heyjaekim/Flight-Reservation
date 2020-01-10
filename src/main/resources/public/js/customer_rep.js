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
        errorMessage: false,
        currentPage: 'reservation',
        airports: [], // this is reference data
        airportlist: [], // this is for maintenance
        edit_airport: {}, // edit form
        showAirportEditor: false,
        airlines: [], // this is refernce data
        airlinelist: [], // this is for sys maintenacne
        edit_airline: {}, // edit form
        showAirlineEditor: false,
        aircrafts: [], // this is refernce data
        aircraftlist: [], // this is for sys maintenacne
        edit_aircraft: {}, // edit form
        showAircraftEditor: false,
        flights: [], // this is refernce data
        flightlist: [], // this is for system maintenance
        edit_flight: {}, // edit form
        showFlightEditor: false,
        search_reservation: {
            username: 'cr'
        }, // search form
        reservations: [],
        tickets: {},
        waitlist: [],
        search_waitlist: { // search form
            airline_id: 'UA',
            flight_id: "500",
            departure_date: '2019-12-19'
        }
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
    computed: {},
    methods: {
        initData: async function() {
            console.log("init data");
            let response, data;
            // fetch user
            response = await fetch('/user');
            this.user = await response.json();
            console.log(this.user);

            this.loadAirport();
            this.loadAirline();
            this.loadAircraft();
            this.loadFlight();

            let cart = localStorage.getItem('myCart');
            console.log(cart);
            if (cart) this.cart = JSON.parse(cart);
        },
        toWaitlist: async function() {
            let response = await fetch('/cr/report/waitlist?' +
                'airline_id=' + this.search_waitlist.airline_id +
                '&flight_id=' + this.search_waitlist.flight_id +
                '&departure_date=' + this.search_waitlist.departure_date
            );
            this.waitlist = await response.json();
            this.currentPage = 'waitlist';
        },
        toReservation: async function() {
            let response = await fetch('/cr/reservation?username=' +
                this.search_reservation.username);
            this.reservations = await response.json();
            this.tickets = [];
            this.currentPage = 'reservation';
        },
        deleteReservation: async function(id) {
            let response = await fetch("/cr/reservation/" + id, {
                method: 'DELETE'
            });
            await response.json();
            console.log("deleted reservation: " + id);
            this.toReservation();
        },
        getTickets: async function(id) {
            fetch('/cr/tickets/' + id)
                .then((response) => {
                    response.json().then((data) => {
                        this.tickets = data;
                    });
                });
        },
        getFlight: function(f) {
            // console.log("get flight: "+f);
            let ff = this.flights[f.split('-')[0] + '-' + f.split('-')[1] + '-' + f.split('-')[2]];
            if (!ff) {
                console.log("Error: " + f);
                console.log(JSON.stringify(this.flights));
            }
            return ff;
        },
        loadFlight: async function() {
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
        editFlight: function(f) {
            console.dir(this.edit_flight);
            this.edit_flight = Object.assign({}, f);
            console.dir(this.edit_flight);
            this.showFlightEditor = true;
        },
        upsertFlight: async function() {
            if (this.edit_flight.airline_id == null || this.edit_flight.airline_id.length == 0) {
                this.displayMessage("Error: Airline code is required.", "error");
                return;
            }
            this.edit_flight.airline_id = this.edit_flight.airline_id.toUpperCase();
            if (this.edit_flight.airline_id == null || this.airlines[this.edit_flight.airline_id] == null) {
                this.displayMessage("Error: Invalid or non-existent airline code.", "error");
                return;
            }
            if (this.edit_flight.flight_id == null || this.edit_flight.flight_id.length == 0) {
                this.displayMessage("Error: Flight number is required.", "error");
                return;
            }
            if (this.edit_flight.departure_airport_id == null || this.edit_flight.departure_airport_id.length == 0) {
                this.displayMessage("Error: Departure airport code is required.", "error");
                return;
            }
            this.edit_flight.departure_airport_id = this.edit_flight.departure_airport_id.toUpperCase();
            if (this.edit_flight.departure_airport_id == null || this.airports[this.edit_flight.departure_airport_id] == null) {
                this.displayMessage("Error: Invalid or non-existent departure airport code.", "error");
                return;
            }
            if (this.edit_flight.departure_weekday == null ||
                parseInt(this.edit_flight.departure_weekday) < 0 ||
                parseInt(this.edit_flight.departure_weekday) > 6
            ) {
                this.displayMessage("Error: Departure weekday must be from 0 to 6.", "error");
                return;
            }
            if (this.edit_flight.departure_time == null || this.edit_flight.departure_time.length == 0) {
                this.displayMessage("Error: Departure time is required.", "error");
                return;
            }
            if (this.edit_flight.arrival_airport_id == null || this.edit_flight.arrival_airport_id.length == 0) {
                this.displayMessage("Error: Arrival airport code is required.", "error");
                return;
            }
            this.edit_flight.arrival_airport_id = this.edit_flight.arrival_airport_id.toUpperCase();
            if (this.edit_flight.arrival_airport_id == null || this.airports[this.edit_flight.arrival_airport_id] == null) {
                alert(this.edit_flight.arrival_airport_id);
                this.displayMessage("Error: Invalid or non-existent arrival airport code.", "error");
                return;
            }
            if (this.edit_flight.arrival_weekday == null ||
                parseInt(this.edit_flight.arrival_weekday) < 0 ||
                parseInt(this.edit_flight.arrival_weekday) > 6
            ) {
                this.displayMessage("Error: Departure weekday must be from 0 to 6.", "error");
                return;
            }
            if (this.edit_flight.arrival_time == null || this.edit_flight.arrival_time.length == 0) {
                this.displayMessage("Error: Arrival time is required.", "error");
                return;
            }
            if (this.edit_flight.aircraft_id == null || this.edit_flight.aircraft_id.length == 0) {
                this.displayMessage("Error: Aircraft id is required.", "error");
                return;
            }
            this.edit_flight.aircraft_id = this.edit_flight.aircraft_id.toUpperCase();
            if (this.edit_flight.aircraft_id == null || this.aircrafts[this.edit_flight.aircraft_id] == null) {
                this.displayMessage("Error: Invalid or non-existent aircraft id.", "error");
                return;
            }
            if (this.edit_flight.price == null ||
                this.edit_flight.price.length == 0
            ) {
                this.displayMessage("Error: Price is required.", "error");
                return;
            }
            if (this.edit_flight.price == null ||
                this.edit_flight.price.length == 0 ||
                isNaN(parseFloat(this.edit_flight.price))
            ) {
                this.displayMessage("Error: Price must be a number.", "error");
                return;
            }
            this.edit_flight.price = parseFloat(this.edit_flight.price);

            // create new airport
            let response = await fetch("/cr/flight", {
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
                body: JSON.stringify(this.edit_flight) // body data type must match "Content-Type" header
            });
            await response.json();
            console.dir(this.edit_flight);
            this.loadFlight();
            this.showFlightEditor = false;
        },
        deleteFlight: async function(f) {
            let response = await fetch("/cr/flight", {
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
                body: JSON.stringify(f) // body data type must match "Content-Type" header
            });
            await response.json();
            console.log("deleted flight: " + f);
            this.loadFlight();
            this.showFlightEditor = false;
        },
        loadAirport: async function() {
            response = await fetch('/airport');
            data = await response.json();
            console.log(data);
            this.airportlist = data;

            // setup refernce data
            let airports = [];
            data.forEach((f) => {
                airports[f.airport_id] = f.airport_name;
            });
            this.airports = airports;
        },
        editAirport: function(a) {
            this.edit_airport = Object.assign({}, a);
            this.showAirportEditor = true;
        },
        upsertAirport: async function() {
            if (this.edit_airport.airport_id == null || this.edit_airport.airport_id.length != 3) {
                this.displayMessage("Error: Airport code must be a three-letter value.", "error");
                return;
            }
            if (this.edit_airport.airport_name == null || this.edit_airport.airport_name.length == 0) {
                this.displayMessage("Error: Airport name is required.", "error");
                return;
            }
            if (this.edit_airport.airport_tags == null || this.edit_airport.airport_tags.length == 0) {
                this.displayMessage("Error: Airport tags are required.", "error");
                return;
            }
            this.edit_airport.airport_id = this.edit_airport.airport_id.toUpperCase();

            // create new airport
            let response = await fetch("/cr/airport", {
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
                body: JSON.stringify(this.edit_airport) // body data type must match "Content-Type" header
            });
            await response.json();
            console.log("created airport: " + this.edit_airport);
            this.loadAirport();
            this.showAirportEditor = false;
        },
        deleteAirport: async function(a) {
            let response = await fetch("/cr/airport", {
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
                body: JSON.stringify(a) // body data type must match "Content-Type" header
            });
            await response.json();
            this.loadAirport();
            console.log("deleted airport: " + a);
        },
        loadAirline: async function() {
            // fetch airlines
            response = await fetch('/airline');
            data = await response.json();
            console.log(data);
            this.airlinelist = data;

            let airlines = [];
            data.forEach((a) => { airlines[a.airline_id] = a.airline_name; });
            this.airlines = airlines;
        },
        editAirline: function(a) {
            this.edit_airline = Object.assign({}, a);
            this.showAirlineEditor = true;
        },
        upsertAirline: async function() {
            if (this.edit_airline.airline_id == null || this.edit_airline.airline_id.length != 2) {
                this.displayMessage("Error: Airline code must be a two-letter value.", "error");
                return;
            }
            this.edit_airline.airline_id = this.edit_airline.airline_id.toUpperCase();
            if (this.edit_airline.airline_name == null || this.edit_airline.airline_name.length == 0) {
                this.displayMessage("Error: Airline name is required.", "error");
                return;
            }

            // create new airport
            let response = await fetch("/cr/airline", {
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
                body: JSON.stringify(this.edit_airline) // body data type must match "Content-Type" header
            });
            await response.json();
            console.log("created airline: " + this.edit_airline);
            this.loadAirline();
            this.showAirlineEditor = false;
        },
        deleteAirline: async function(a) {
            let response = await fetch("/cr/airline", {
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
                body: JSON.stringify(a) // body data type must match "Content-Type" header
            });
            await response.json();
            this.loadAirline();
            console.log("deleted airline: " + a);
        },
        loadAircraft: async function() {
            // fetch aircraft
            response = await fetch('/aircraft');
            data = await response.json();
            console.log(data);
            this.aircraftlist = data;

            // fetch aircraft
            let aircrafts = [];
            data.forEach((a) => { aircrafts[a.aircraft_id] = a; });
            this.aircrafts = aircrafts;
        },
        editAircraft: function(a) {
            this.edit_aircraft = Object.assign({}, a);
            this.showAircraftEditor = true;
        },
        upsertAircraft: async function() {
            if (this.edit_aircraft.aircraft_id == null || this.edit_aircraft.aircraft_id.length == 0) {
                this.displayMessage("Error: Aircraft id is required.", "error");
                return;
            }
            this.edit_aircraft.aircraft_id = this.edit_aircraft.aircraft_id.toUpperCase();

            if (this.edit_aircraft.airline_id == null || this.edit_aircraft.airline_id.length == 0) {
                this.displayMessage("Error: Airline code is required.", "error");
                return;
            }
            this.edit_aircraft.airline_id = this.edit_aircraft.airline_id.toUpperCase();
            if (this.edit_aircraft.airline_id == null || this.airlines[this.edit_aircraft.airline_id] == null) {
                this.displayMessage("Error: Invalid or non-existent airline code.", "error");
                return;
            }
            if (this.edit_aircraft.aircraft_model == null || this.edit_aircraft.aircraft_model.length == 0) {
                this.displayMessage("Error: Aircraft model is required.", "error");
                return;
            }

            // create new aircraft
            let response = await fetch("/cr/aircraft", {
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
                body: JSON.stringify(this.edit_aircraft) // body data type must match "Content-Type" header
            });
            await response.json();
            console.log("created aircraft: " + this.edit_aircraft);
            this.loadAircraft();
            this.showAircraftEditor = false;
        },
        deleteAircraft: async function(a) {
            let response = await fetch("/cr/aircraft", {
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
                body: JSON.stringify(a) // body data type must match "Content-Type" header
            });
            await response.json();
            this.loadAircraft();
            console.log("deleted aircraft: " + a);
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