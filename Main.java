import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Hotel hotel = new Hotel("Hotel Moon");

        // Tambahkan kamar
        hotel.addRoom(new Room(101, "Sunset View", "Single"));
        hotel.addRoom(new Room(102, "Ocean Breeze", "Double"));
        hotel.addRoom(new Room(103, "Presidential Suite", "Suite"));

        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n=== Sistem Reservasi Hotel ===");
            System.out.println("1. Lihat Kamar Tersedia");
            System.out.println("2. Pesan Kamar");
            System.out.println("3. Batalkan Pemesanan");
            System.out.println("4. Lihat Semua Reservasi");
            System.out.println("5. Keluar");
            System.out.print("Pilih opsi (1-5): ");
            
            if (!scanner.hasNextInt()) {
                System.out.println("Input tidak valid. Silakan masukkan angka.");
                scanner.next(); 
                continue;
            }

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    hotel.showAvailableRooms();
                    break;
                case 2:
                    System.out.println("\n--- Pemesanan Kamar ---");
                    System.out.print("Masukkan nama Anda: ");
                    String name = scanner.nextLine();
                    System.out.print("Masukkan email Anda: ");
                    String email = scanner.nextLine();
                    Customer customer = new Customer(name, email);

                    hotel.showAvailableRooms();
                    System.out.print("Masukkan nomor kamar yang ingin dipesan: ");
                    if (scanner.hasNextInt()) {
                        int roomNumber = scanner.nextInt();
                        scanner.nextLine();
                        hotel.bookRoom(roomNumber, customer);
                    } else {
                        System.out.println("Nomor kamar tidak valid!");
                        scanner.next();
                    }
                    break;
                case 3:
                    System.out.println("\n--- Pembatalan Pemesanan ---");
                    System.out.print("Masukkan email Anda: ");
                    String emailCancel = scanner.nextLine();
                    System.out.print("Masukkan nomor kamar yang ingin dibatalkan: ");
                    if (scanner.hasNextInt()) {
                        int roomNumberCancel = scanner.nextInt();
                        scanner.nextLine();
                        Customer cancelCustomer = new Customer("", emailCancel);
                        hotel.cancelBooking(roomNumberCancel, cancelCustomer);
                    } else {
                        System.out.println("Nomor kamar tidak valid!");
                        scanner.next();
                    }
                    break;
                case 4:
                    hotel.showAllReservations();
                    break;
                case 5:
                    running = false;
                    System.out.println("Terima kasih telah menggunakan Sistem Reservasi Hotel Moon!");
                    break;
                default:
                    System.out.println("Pilihan tidak valid. Silakan pilih antara 1-5.");
            }
        }

        scanner.close();
    }
}
