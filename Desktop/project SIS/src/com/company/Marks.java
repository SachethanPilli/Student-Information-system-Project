import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Marks {
    public void displayMarksGUI(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT subject, marks FROM marks WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            StringBuilder marks = new StringBuilder("Subject | Marks\n");
            marks.append("----------------\n");

            while (resultSet.next()) {
                marks.append(resultSet.getString("subject")).append(" | ").append(resultSet.getInt("marks"))
                        .append("\n");
            }

            JOptionPane.showMessageDialog(null, marks.toString(), "Marks", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching marks: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}