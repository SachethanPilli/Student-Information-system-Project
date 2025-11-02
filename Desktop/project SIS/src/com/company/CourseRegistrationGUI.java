import javax.swing.*;
import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CourseRegistrationGUI extends JFrame {
    private final String username; // Username passed dynamically after login
    private final List<String> availableCourses;
    private final List<String> registeredCourses;

    public CourseRegistrationGUI(String username) {
        this.username = username;
        availableCourses = new ArrayList<>();
        registeredCourses = new ArrayList<>();

        setTitle("Course Registration");
        setSize(500, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1, 2));

        // Panels for available and registered courses
        JTextArea availableCoursesArea = new JTextArea();
        JTextArea registeredCoursesArea = new JTextArea();
        availableCoursesArea.setEditable(false);
        registeredCoursesArea.setEditable(false);

        // Initialize and fetch courses
        fetchCourses(availableCoursesArea, registeredCoursesArea);

        mainPanel.add(new JScrollPane(availableCoursesArea));
        mainPanel.add(new JScrollPane(registeredCoursesArea));

        JPanel inputPanel = new JPanel();
        JTextField courseInputField = new JTextField(10);
        JButton registerButton = new JButton("Register");

        registerButton.addActionListener(e -> {
            try {
                int courseIndex = Integer.parseInt(courseInputField.getText()) - 1;
                if (courseIndex >= 0 && courseIndex < availableCourses.size()) {
                    String selectedCourse = availableCourses.get(courseIndex);
                    registerCourse(selectedCourse, registeredCoursesArea);
                } else {
                    JOptionPane.showMessageDialog(this, "Invalid course number.");
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Enter a valid number.");
            }
        });

        inputPanel.add(new JLabel("Enter Course Number:"));
        inputPanel.add(courseInputField);
        inputPanel.add(registerButton);

        add(mainPanel, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);
        setVisible(true);
    }

    private void fetchCourses(JTextArea availableCoursesArea, JTextArea registeredCoursesArea) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            // Fetch available courses
            String query = "SELECT course_name FROM courses";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                availableCourses.add(resultSet.getString("course_name"));
            }

            // Fetch registered courses
            query = "SELECT course_name FROM course_registration WHERE username = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, username);
            resultSet = statement.executeQuery();
            while (resultSet.next()) {
                registeredCourses.add(resultSet.getString("course_name"));
            }

            // Update text areas
            updateTextArea(availableCoursesArea, availableCourses, "Available Courses");
            updateTextArea(registeredCoursesArea, registeredCourses, "Registered Courses");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching courses: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void updateTextArea(JTextArea textArea, List<String> courses, String title) {
        StringBuilder content = new StringBuilder(title + ":\n");
        for (int i = 0; i < courses.size(); i++) {
            content.append(i + 1).append(". ").append(courses.get(i)).append("\n");
        }
        textArea.setText(content.toString());
    }

    private void registerCourse(String selectedCourse, JTextArea registeredCoursesArea) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO course_registration (username, course_name) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, selectedCourse);
            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                registeredCourses.add(selectedCourse);
                updateTextArea(registeredCoursesArea, registeredCourses, "Registered Courses");
                JOptionPane.showMessageDialog(this, "Successfully registered for " + selectedCourse + ".");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error registering course: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}