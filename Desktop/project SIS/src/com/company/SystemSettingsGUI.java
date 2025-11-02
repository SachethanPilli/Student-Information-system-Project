import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class SystemSettingsGUI extends JFrame {

    public SystemSettingsGUI() {
        setTitle("System Settings");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for different configuration options
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        // Buttons for system settings actions
        JButton manageCoursesButton = new JButton("Manage Courses");
        JButton setStudentLimitButton = new JButton("Set Student Registration Limits");
        JButton otherSettingsButton = new JButton("Other Settings");

        manageCoursesButton.addActionListener(e -> manageCourses());
        setStudentLimitButton.addActionListener(e -> setStudentRegistrationLimit());
        otherSettingsButton.addActionListener(e -> otherSystemSettings());

        buttonPanel.add(manageCoursesButton);
        buttonPanel.add(setStudentLimitButton);
        buttonPanel.add(otherSettingsButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Manage Courses: Add, Edit, Delete courses
    private void manageCourses() {
        // Open a new dialog or panel to manage courses
        String courseName = JOptionPane.showInputDialog(this, "Enter course name to add:");

        if (courseName != null && !courseName.trim().isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "INSERT INTO courses (course_name) VALUES (?)";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setString(1, courseName);
                int rowsInserted = statement.executeUpdate();
                if (rowsInserted > 0) {
                    JOptionPane.showMessageDialog(this, "Course added successfully!");
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error adding course: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Set Student Registration Limit for a course
    private void setStudentRegistrationLimit() {
        String courseName = JOptionPane.showInputDialog(this, "Enter course name to set the limit:");
        String limit = JOptionPane.showInputDialog(this, "Enter the student registration limit:");

        if (courseName != null && !courseName.trim().isEmpty() && limit != null && !limit.trim().isEmpty()) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String query = "UPDATE courses SET student_limit = ? WHERE course_name = ?";
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, Integer.parseInt(limit));
                statement.setString(2, courseName);

                int rowsUpdated = statement.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(this, "Student limit updated successfully!");
                } else {
                    JOptionPane.showMessageDialog(this, "Course not found!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error updating student limit: " + e.getMessage(), "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Other System Settings (Placeholder for future functionality)
    private void otherSystemSettings() {
        JOptionPane.showMessageDialog(this, "Other system settings can be configured here.", "System Settings",
                JOptionPane.INFORMATION_MESSAGE);
    }

    public static void main(String[] args) {
        new SystemSettingsGUI();
    }
}