import javax.swing.*;

public class Hostel {
    public void enterRoomDetails() {
        String roomNumber = JOptionPane.showInputDialog("Enter your room number:");
        try {
            int roomNo = Integer.parseInt(roomNumber);
            JOptionPane.showMessageDialog(null, "Your room number is: " + roomNo, "Hostel Info",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Invalid room number!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}