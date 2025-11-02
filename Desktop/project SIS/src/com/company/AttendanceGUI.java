import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AttendanceGUI extends JFrame {
    private final String username; // Username passed dynamically after login
    private final List<String> registeredCourses; // List of courses the student is registered for

    public AttendanceGUI(String username) {
        this.username = username;
        registeredCourses = new ArrayList<>();

        setTitle("Mark Attendance");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to display registered courses and mark attendance
        JPanel attendancePanel = new JPanel();
        attendancePanel.setLayout(new BoxLayout(attendancePanel, BoxLayout.Y_AXIS));

        // Fetch registered courses from the database
        fetchRegisteredCourses(attendancePanel);

        // Add the attendance panel to the frame
        add(attendancePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    private void fetchRegisteredCourses(JPanel attendancePanel) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT course_name FROM course_registration WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                do {
                    String courseName = resultSet.getString("course_name");
                    registeredCourses.add(courseName);

                    // Add a dropdown (JComboBox) to select attendance status for each course
                    addAttendanceOption(attendancePanel, courseName);
                } while (resultSet.next());
            } else {
                JOptionPane.showMessageDialog(this, "No registered courses found for the user.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching registered courses: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addAttendanceOption(JPanel attendancePanel, String courseName) {
        JLabel courseLabel = new JLabel(courseName);
        JComboBox<String> attendanceComboBox = new JComboBox<>(new String[] { "Present", "Absent" });
        JButton markButton = new JButton("Mark Attendance");

        markButton.addActionListener(e -> {
            String status = (String) attendanceComboBox.getSelectedItem();
            markAttendance(courseName, status);
        });

        attendancePanel.add(courseLabel);
        attendancePanel.add(attendanceComboBox);
        attendancePanel.add(markButton);
    }

    private void markAttendance(String courseName, String status) {
        java.sql.Date currentDate = new java.sql.Date(System.currentTimeMillis());

        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO attendance (username, course_name, date, status) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, courseName);
            statement.setDate(3, currentDate);
            statement.setString(4, status);

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated > 0) {
                JOptionPane.showMessageDialog(this,
                        "Attendance marked successfully for " + courseName + " as " + status);
            } else {
                JOptionPane.showMessageDialog(this, "Failed to mark attendance.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error marking attendance: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}