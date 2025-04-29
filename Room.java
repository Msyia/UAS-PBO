public class Room {
    private int roomNumber;
    private String name;
    private String type;
    private boolean isAvailable;

    public Room(int roomNumber, String name, String type) {
        this.roomNumber = roomNumber;
        this.name = name;
        this.type = type;
        this.isAvailable = true;
    }

    public int getRoomNumber() { return roomNumber; }
    public String getName() { return name; }
    public String getType() { return type; }
    public boolean isAvailable() { return isAvailable; }

    public void setAvailable(boolean available) {
        this.isAvailable = available;
    }

    @Override
    public String toString() {
        return "Kamar{" +
                "Nomor Kamar=" + roomNumber +
                ", Nama='" + name + '\'' +
                ", Tipe='" + type + '\'' +
                ", Tersedia=" + isAvailable +
                '}';
    }
}
