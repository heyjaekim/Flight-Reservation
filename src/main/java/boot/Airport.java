package boot;

public class Airport {
    private String airport_id;
    private String airport_name;
    private String airport_tags;

    public Airport() {
    }

    public Airport(String airport_id, String airport_name, String airport_tags) {
        this.airport_id = airport_id;
        this.airport_name = airport_name;
        this.airport_tags = airport_tags;
    }

    // getter needed for JSON
    public String getAirport_id() {
        return airport_id;
    }

    public String getAirport_name() {
        return airport_name;
    }

    public String getAirport_tags() {
        return airport_tags;
    }
}
