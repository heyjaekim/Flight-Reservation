package boot;

public class Capacity {
    private String aircraft_id;
    private String seat_class;
    private int capacity;

    public Capacity() {
    }

    public Capacity(String aircraft_id, String seat_class, int capacity) {
        this.aircraft_id = aircraft_id;
        this.seat_class = seat_class;
        this.capacity = capacity;
    }

    // getter needed for JSON
    public String getAircraft_id() {
        return this.aircraft_id;
    }

    public String getSeat_class() {
      return this.seat_class;
    }

    public int getCapacity() {
      return this.capacity;
    }
}
