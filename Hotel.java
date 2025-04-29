import java.util.ArrayList;
import java.util.List;

public class Hotel {
    private String name;
    private List<Room> rooms;
    private List<Reservation> reservations;

    public Hotel(String name) {
        this.name = name;
        this.rooms = new ArrayList<>();
        this.reservations = new ArrayList<>();
    }

    public void addRoom(Room room) {
        rooms.add(room);
    }

    public void showAvailableRooms() {
        System.out.println("\n--- Daftar Kamar Tersedia ---");
        boolean found = false;
        for (Room room : rooms) {
            if (room.isAvailable()) {
                System.out.println("Nomor Kamar: " + room.getRoomNumber() +
                                   ", Nama: " + room.getName() +
                                   ", Tipe: " + room.getType());
                found = true;
            }
        }
        if (!found) {
            System.out.println("Tidak ada kamar yang tersedia saat ini.");
        }
    }

    public boolean bookRoom(int roomNumber, Customer customer) {
        for (Room room : rooms) {
            if (room.getRoomNumber() == roomNumber && room.isAvailable()) {
                room.setAvailable(false);
                Reservation reservation = new Reservation(customer, room);
                reservations.add(reservation);
                System.out.println("Pemesanan berhasil untuk " + customer.getName() +
                                   " di kamar " + room.getName() + ".");
                return true;
            }
        }
        System.out.println("Kamar tidak tersedia atau tidak ditemukan!");
        return false;
    }

    public boolean cancelBooking(int roomNumber, Customer customer) {
        Reservation toRemove = null;
        for (Reservation reservation : reservations) {
            if (reservation.getRoom().getRoomNumber() == roomNumber &&
                reservation.getCustomer().getEmail().equals(customer.getEmail())) {
                toRemove = reservation;
                break;
            }
        }

        if (toRemove != null) {
            toRemove.getRoom().setAvailable(true);
            reservations.remove(toRemove);
            System.out.println("Pemesanan berhasil dibatalkan untuk " + customer.getName() + ".");
            return true;
        } else {
            System.out.println("Reservasi tidak ditemukan!");
            return false;
        }
    }

    public void showAllReservations() {
        System.out.println("\n--- Daftar Semua Reservasi ---");
        if (reservations.isEmpty()) {
            System.out.println("Belum ada reservasi yang aktif.");
        } else {
            for (Reservation res : reservations) {
                System.out.println(res);
            }
        }
    }
}
