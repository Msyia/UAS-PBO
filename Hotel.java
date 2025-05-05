import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();

    public Hotel(String name) {
        this.name = name;
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public List<Room> getAvailableRooms() {
        List<Room> available = new ArrayList<>();
        for (Room r : rooms) {
            if (r.isAvailable()) {
                available.add(r);
            }
        }
        return available;
    }

    public boolean bookRoom(Room room, Customer customer) {
        if (room.isAvailable()) {
            room.setAvailable(false);
            reservations.add(new Reservation(customer, room));
            return true;
        }
        return false;
    }

    public boolean cancelReservation(Room room, String email) {
        for (Reservation r : reservations) {
            if (r.getRoom().equals(room) && r.getCustomer().getEmail().equals(email)) {
                room.setAvailable(true);
                reservations.remove(r);
                return true;
            }
        }
        return false;
    }

    public List<Reservation> getAllReservations() {
        return reservations;
    }
}
