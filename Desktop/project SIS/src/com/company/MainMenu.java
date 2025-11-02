import javax.swing.*;
import java.awt.*;

public class MainMenu extends JFrame {
    private final String username;
    private final String role;

    public MainMenu(String username, String role) {
        this.username = username;
        this.role = role;

        setTitle("Student Management System");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Info Panel
        JPanel infoPanel = new JPanel();
        JLabel infoLabel = new JLabel("Welcome, " + username + " | Role: " + role);
        infoPanel.add(infoLabel);
        add(infoPanel, BorderLayout.NORTH);

        // Buttons for different modules
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(5, 2)); // Adjust layout for buttons
        String[] options;

        // Role-based menu options
        if (role.equalsIgnoreCase("Admin")) {
            options = new String[] {
                    "Manage Users", "View Reports", "System Settings", "Password Management", "Logout"
            };
        } else {
            options = new String[] {
                    "Profile", "Marks", "Fee Details", "Password Management",
                    "Syllabus", "Leave Details", "Hostel", "Course Registration", "Attendance", "Logout"
            };
        }

        // Create buttons for each option
        for (String option : options) {
            JButton button = new JButton(option);
            button.addActionListener(e -> openModule(option)); // Add listener for button actions
            buttonPanel.add(button);
        }

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Open the appropriate module based on the button clicked
    private void openModule(String module) {
        switch (module) {
            case "Profile":
                new Profile().showProfilePopup(username); // Open Profile view
                break;
            case "Marks":
                new Marks().displayMarksGUI(username); // Open Marks view
                break;
            case "Fee Details":
                new FeeTransactionGUI(username).display(); // Open Fee Transaction GUI
                break;
            case "Password Management":
                new PasswordManager(username).showPasswordOptions(); // Open Password Management GUI
                break;
            case "Syllabus":
                new Syllabus().displaySyllabus(); // Display Syllabus
                break;
            case "Leave Details":
                new LeaveDetails().displayLeaveForm(); // Open Leave Details form
                break;
            case "Hostel":
                new Hostel().enterRoomDetails(); // Open Hostel details form
                break;
            case "Course Registration":
                new CourseRegistrationGUI(username); // Open Course Registration GUI
                break;
            case "Attendance":
                new AttendanceGUI(username); // Open Attendance GUI
                break;
            case "Manage Users": // Admin only
                if (role.equalsIgnoreCase("Admin")) {
                    new UserManagementGUI(); // Open the User Management GUI
                } else {
                    JOptionPane.showMessageDialog(this, "Access Denied! Admin Only.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "View Reports": // Admin only
                if (role.equalsIgnoreCase("Admin")) {
                    new ReportsGUI(username); // Open the Reports GUI
                } else {
                    JOptionPane.showMessageDialog(this, "Access Denied! Admin Only.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "System Settings": // Admin only
                if (role.equalsIgnoreCase("Admin")) {
                    new SystemSettingsGUI(); // Open the System Settings GUI
                } else {
                    JOptionPane.showMessageDialog(this, "Access Denied! Admin Only.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
                break;
            case "Logout":
                JOptionPane.showMessageDialog(this, "Logging out...");
                dispose(); // Close the MainMenu window
                new LoginPage(); // Redirect to the login page
                break;
            default:
                JOptionPane.showMessageDialog(this, "Module not implemented yet.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Admin-specific methods
    private void manageUsers() {
        // This method is now handled by the UserManagementGUI class
        JOptionPane.showMessageDialog(this, "Manage Users Module (Admin Only)");
    }

    private void viewReports() {
        // This method is now handled by the ReportsGUI class
        JOptionPane.showMessageDialog(this, "View Reports Module (Admin Only)");
    }

    private void systemSettings() {
        // This method is now handled by the SystemSettingsGUI class
        JOptionPane.showMessageDialog(this, "System Settings Module (Admin Only)");
    }
}