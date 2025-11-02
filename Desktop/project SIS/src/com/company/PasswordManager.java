import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordManager {
    private final String username;

    public PasswordManager(String username) {
        this.username = username;
    }

    // Display the password management options
    public void showPasswordOptions() {
        Object[] options = { "Change Password", "Forgot Password" };
        int choice = JOptionPane.showOptionDialog(
                null,
                "Select an option:",
                "Password Management",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.INFORMATION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) {
            changePassword();
        } else if (choice == 1) {
            resetPasswordToDefault();
        }
    }

    // Method to change the password
    private void changePassword() {
        JPasswordField oldPasswordField = new JPasswordField();
        JPasswordField newPasswordField = new JPasswordField();
        JPasswordField confirmNewPasswordField = new JPasswordField();

        Object[] message = {
                "Enter Old Password:", oldPasswordField,
                "Enter New Password:", newPasswordField,
                "Confirm New Password:", confirmNewPasswordField
        };

        int option = JOptionPane.showConfirmDialog(null, message, "Change Password", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            String oldPassword = new String(oldPasswordField.getPassword());
            String newPassword = new String(newPasswordField.getPassword());
            String confirmNewPassword = new String(confirmNewPasswordField.getPassword());

            if (newPassword.equals(confirmNewPassword)) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    // Verify the old password
                    String verifyQuery = "SELECT * FROM profiles WHERE username = ? AND password = ?";
                    PreparedStatement verifyStmt = connection.prepareStatement(verifyQuery);
                    verifyStmt.setString(1, username);
                    verifyStmt.setString(2, oldPassword);

                    ResultSet resultSet = verifyStmt.executeQuery();
                    if (resultSet.next()) {
                        // Update the password
                        String updateQuery = "UPDATE profiles SET password = ? WHERE username = ?";
                        PreparedStatement updateStmt = connection.prepareStatement(updateQuery);
                        updateStmt.setString(1, newPassword);
                        updateStmt.setString(2, username);

                        int rowsUpdated = updateStmt.executeUpdate();
                        if (rowsUpdated > 0) {
                            JOptionPane.showMessageDialog(null, "Password successfully updated!");
                        } else {
                            JOptionPane.showMessageDialog(null, "Failed to update password.", "Error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "Incorrect old password.", "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(null, "New passwords do not match.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    // Method to reset password to default
    private void resetPasswordToDefault() {
        int confirm = JOptionPane.showConfirmDialog(
                null,
                "Are you sure you want to reset your password to the default?",
                "Reset Password",
                JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            try (Connection connection = DatabaseConnection.getConnection()) {
                String defaultPassword = "default123"; // Set the default password
                String resetQuery = "UPDATE profiles SET password = ? WHERE username = ?";
                PreparedStatement resetStmt = connection.prepareStatement(resetQuery);
                resetStmt.setString(1, defaultPassword);
                resetStmt.setString(2, username);

                int rowsUpdated = resetStmt.executeUpdate();
                if (rowsUpdated > 0) {
                    JOptionPane.showMessageDialog(null, "Password has been reset to the default.");
                } else {
                    JOptionPane.showMessageDialog(null, "Failed to reset password.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}