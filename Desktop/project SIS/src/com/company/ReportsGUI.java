import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class ReportsGUI extends JFrame {
    private final String username;

    public ReportsGUI(String username) {
        this.username = username;

        setTitle("Reports");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel for displaying report options
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(3, 1));

        // Add buttons for each type of report
        JButton performanceReportButton = new JButton("Student Performance Report");
        JButton attendanceReportButton = new JButton("Attendance Report");
        JButton feeReportButton = new JButton("Fee Status Report");

        performanceReportButton.addActionListener(e -> generateStudentPerformanceReport());
        attendanceReportButton.addActionListener(e -> generateAttendanceReport());
        feeReportButton.addActionListener(e -> generateFeeStatusReport());

        buttonPanel.add(performanceReportButton);
        buttonPanel.add(attendanceReportButton);
        buttonPanel.add(feeReportButton);

        add(buttonPanel, BorderLayout.CENTER);
        setVisible(true);
    }

    // Generate and display Student Performance Report
    private void generateStudentPerformanceReport() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT subject, marks FROM marks WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            StringBuilder report = new StringBuilder("Performance Report for " + username + "\n");

            while (resultSet.next()) {
                report.append("Subject: ").append(resultSet.getString("subject"))
                        .append(" - Marks: ").append(resultSet.getInt("marks")).append("\n");
            }

            JOptionPane.showMessageDialog(this, report.toString(), "Student Performance Report",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching performance data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Generate and display Attendance Report
    private void generateAttendanceReport() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT course_name, date, status FROM attendance WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            StringBuilder report = new StringBuilder("Attendance Report for " + username + "\n");

            while (resultSet.next()) {
                report.append("Course: ").append(resultSet.getString("course_name"))
                        .append(" - Date: ").append(resultSet.getDate("date"))
                        .append(" - Status: ").append(resultSet.getString("status")).append("\n");
            }

            JOptionPane.showMessageDialog(this, report.toString(), "Attendance Report",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching attendance data: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Generate and display Fee Status Report
    private void generateFeeStatusReport() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT total_fee, paid_fee FROM fees WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            StringBuilder report = new StringBuilder("Fee Status Report for " + username + "\n");

            if (resultSet.next()) {
                int totalFee = resultSet.getInt("total_fee");
                int paidFee = resultSet.getInt("paid_fee");
                int remainingFee = totalFee - paidFee;
                report.append("Total Fee: ").append(totalFee)
                        .append(" - Paid Fee: ").append(paidFee)
                        .append(" - Remaining Fee: ").append(remainingFee).append("\n");
            } else {
                report.append("No fee details found for this user.\n");
            }

            JOptionPane.showMessageDialog(this, report.toString(), "Fee Status Report",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching fee status: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}