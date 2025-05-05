import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class HotelReservationApp extends JFrame {
    private Hotel hotel;

    private JPanel mainPanel;
    private CardLayout cardLayout;

    // Komponen booking
    private JComboBox<Room> roomComboBox;
    private JTextField nameField, emailField;
    private JTextArea resultArea;

    // Komponen cancel
    private JTextField cancelNameField;
    private JTextField cancelRoomNumberField;
    private JTextArea cancelResultArea;

    public HotelReservationApp() {
        hotel = new Hotel("Hotel MOON");
        // menambahkan data kamar
        hotel.addRoom(new Room(101, "Sunset View", "Single"));
        hotel.addRoom(new Room(102, "Ocean Breeze", "Double"));
        hotel.addRoom(new Room(103, "Presidential Suite", "Suite"));
        hotel.addRoom(new Room(104, "Garden Room", "Single"));
        hotel.addRoom(new Room(105, "Family Suite", "Double"));

        setTitle("Aplikasi Reservasi Hotel");
        setSize(600, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        mainPanel.add(createBookingPanel(), "Booking");
        mainPanel.add(createCancelPanel(), "Cancel");

        add(mainPanel);

        setLocationRelativeTo(null); // layar tengah
        setVisible(true);
    }

    // ========================== BOOKING PANEL ==========================
    private JPanel createBookingPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 243, 224));

        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pemesanan"));
        formPanel.setBackground(new Color(224, 192, 151));

        nameField = new JTextField();
        emailField = new JTextField();
        roomComboBox = new JComboBox<>();
        updateRoomList();

        formPanel.add(new JLabel("Nama:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Email:"));
        formPanel.add(emailField);
        formPanel.add(new JLabel("Pilih Kamar:"));
        formPanel.add(roomComboBox);

        resultArea = new JTextArea(5, 30);
        resultArea.setEditable(false);
        resultArea.setBackground(new Color(253, 246, 227));
        resultArea.setForeground(new Color(50, 40, 30));
        JScrollPane scroll = new JScrollPane(resultArea);

        JPanel buttonPanel = new JPanel();
        JButton bookBtn = new JButton("Pesan");
        JButton cancelPageBtn = new JButton("Batalkan Kamar");
        JButton showResBtn = new JButton("Lihat Reservasi");
        
        bookBtn.setBackground(new Color(76, 175, 80));      
        bookBtn.setForeground(Color.WHITE);
        
        cancelPageBtn.setBackground(new Color(244, 67, 54));  
        cancelPageBtn.setForeground(Color.WHITE);
        
        showResBtn.setBackground(new Color(33, 150, 243));    
        showResBtn.setForeground(Color.WHITE);
        
        buttonPanel.add(bookBtn);
        buttonPanel.add(cancelPageBtn);
        buttonPanel.add(showResBtn);

        // Aksi tombol
        bookBtn.addActionListener(e -> bookRoom());
        cancelPageBtn.addActionListener(e -> cardLayout.show(mainPanel, "Cancel"));
        showResBtn.addActionListener(e -> showReservations());

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);
        return panel;
    }

    private void bookRoom() {
        String name = nameField.getText().trim();
        String email = emailField.getText().trim();
        Room selectedRoom = (Room) roomComboBox.getSelectedItem();

        if (hotel.getAvailableRooms().isEmpty()) {
            resultArea.setText("❗ Semua kamar sudah dipesan.");
            return;
        }

        if (name.isEmpty() || email.isEmpty() || selectedRoom == null) {
            resultArea.setText("⚠️ Harap isi semua data dan pilih kamar.");
            return;
        }

        Customer customer = new Customer(name, email);
        if (hotel.bookRoom(selectedRoom, customer)) {
            resultArea.setText("✅ Pemesanan berhasil untuk " + selectedRoom);
            updateRoomList();
        } else {
            resultArea.setText("❌ Gagal melakukan pemesanan.");
        }
    }

    // ========================== CANCEL PANEL ==========================
    private JPanel createCancelPanel() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(250, 243, 224));

        JPanel formPanel = new JPanel(new GridLayout(3, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Form Pembatalan"));
        formPanel.setBackground(new Color(224, 192, 151));

        cancelNameField = new JTextField();
        cancelRoomNumberField = new JTextField();

        formPanel.add(new JLabel("Nama:"));
        formPanel.add(cancelNameField);
        formPanel.add(new JLabel("Nomor Kamar:"));
        formPanel.add(cancelRoomNumberField);

        cancelResultArea = new JTextArea(4, 30);
        cancelResultArea.setEditable(false);
        cancelResultArea.setBackground(new Color(253, 246, 227));
        cancelResultArea.setForeground(new Color(50, 40, 30));
        JScrollPane scroll = new JScrollPane(cancelResultArea);

        JPanel buttonPanel = new JPanel();
        JButton cancelBtn = new JButton("Batalkan");
        JButton backBtn = new JButton("Kembali");

        cancelBtn.setBackground(new Color(244, 67, 54));
        cancelBtn.setForeground(Color.WHITE);

        backBtn.setBackground(new Color(120, 100, 85));
        backBtn.setForeground(Color.WHITE);

        cancelBtn.addActionListener(e -> processCancellation());
        backBtn.addActionListener(e -> cardLayout.show(mainPanel, "Booking"));

        buttonPanel.add(cancelBtn);
        buttonPanel.add(backBtn);

        panel.add(formPanel, BorderLayout.NORTH);
        panel.add(buttonPanel, BorderLayout.CENTER);
        panel.add(scroll, BorderLayout.SOUTH);

        return panel;
    }

    private void processCancellation() {
        String name = cancelNameField.getText().trim();
        String roomNumberText = cancelRoomNumberField.getText().trim();

        if (name.isEmpty() || roomNumberText.isEmpty()) {
            cancelResultArea.setText("⚠️ Nama dan nomor kamar harus diisi.");
            return;
        }

        try {
            int roomNumber = Integer.parseInt(roomNumberText);
            boolean found = false;

            for (Reservation r : hotel.getAllReservations()) {
                if (r.getCustomer().getName().equalsIgnoreCase(name) &&
                        r.getRoom().getRoomNumber() == roomNumber) {
                    hotel.cancelReservation(r.getRoom(), r.getCustomer().getEmail());
                    cancelResultArea.setText("✅ Reservasi atas nama " + name + " dibatalkan.");
                    updateRoomList();
                    found = true;
                    break;
                }
            }

            if (!found) {
                cancelResultArea.setText("❌ Reservasi tidak ditemukan.");
            }

        } catch (NumberFormatException e) {
            cancelResultArea.setText("❗ Nomor kamar harus berupa angka.");
        }
    }

    // ========================== UTILITIES ==========================

    private void updateRoomList() {
        roomComboBox.removeAllItems();
        for (Room r : hotel.getAvailableRooms()) {
            roomComboBox.addItem(r);
        }
    }

    private void showReservations() {
        StringBuilder sb = new StringBuilder("📋 Daftar Reservasi:\n");
        for (Reservation r : hotel.getAllReservations()) {
            sb.append("- ").append(r).append("\n");
        }
        if (sb.toString().equals("📋 Daftar Reservasi:\n")) {
            sb.append("Belum ada reservasi.");
        }
        resultArea.setText(sb.toString());
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(HotelReservationApp::new);
    }
}
