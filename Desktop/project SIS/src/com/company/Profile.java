import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Profile {
    public void showProfilePopup(String username) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM profiles WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                String profileInfo = String.format(
                        "Name: %s%nEmail: %s%nSemester: %s%nBranch: %s%nSection: %s%nRoll No: %s%n" +
                                "Gender: %s%nBlood Group: %s%nMobile: %s%nNationality: %s%nReligion: %s%nAge: %d",
                        resultSet.getString("name"),
                        resultSet.getString("email"),
                        resultSet.getInt("semester"),
                        resultSet.getString("branch"),
                        resultSet.getString("section"),
                        resultSet.getString("roll_no"),
                        resultSet.getString("gender"),
                        resultSet.getString("blood_group"),
                        resultSet.getString("mobile"),
                        resultSet.getString("nationality"),
                        resultSet.getString("religion"),
                        resultSet.getInt("age"));
                JOptionPane.showMessageDialog(null, profileInfo, "Profile Information",
                        JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(null, "Profile not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Error fetching profile: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}