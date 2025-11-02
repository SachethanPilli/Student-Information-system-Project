import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;

public class UserManagementGUI extends JFrame {
    private JTable usersTable;
    private DefaultTableModel tableModel;
    private String[] columnNames = { "Username", "Name", "Email", "Role" };

    public UserManagementGUI() {
        setTitle("User Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Table to display users
        tableModel = new DefaultTableModel(columnNames, 0);
        usersTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(usersTable);
        add(scrollPane, BorderLayout.CENTER);

        // Load users from database
        loadUsers();

        // Panel for buttons (Add, Edit, Delete)
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Add User");
        JButton editButton = new JButton("Edit User");
        JButton deleteButton = new JButton("Delete User");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);

        // Add user button action
        addButton.addActionListener(e -> openAddUserDialog());

        // Edit user button action
        editButton.addActionListener(e -> openEditUserDialog());

        // Delete user button action
        deleteButton.addActionListener(e -> deleteUser());

        setVisible(true);
    }

    // Load users from the database and display them in the table
    private void loadUsers() {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT username, name, email, role FROM profiles";
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery();

            // Clear the table before loading new data
            tableModel.setRowCount(0);

            while (resultSet.next()) {
                String username = resultSet.getString("username");
                String name = resultSet.getString("name"); // Changed from full_name to name
                String email = resultSet.getString("email");
                String role = resultSet.getString("role");

                // Add row to table model
                tableModel.addRow(new Object[] { username, name, email, role });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading users: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Open the dialog to add a new user
    private void openAddUserDialog() {
        JDialog addDialog = new JDialog(this, "Add User", true);
        addDialog.setSize(400, 300);
        addDialog.setLayout(new GridLayout(5, 2));

        // Fields to enter user data
        JTextField usernameField = new JTextField();
        JTextField nameField = new JTextField(); // Changed to nameField
        JTextField emailField = new JTextField();
        JComboBox<String> roleComboBox = new JComboBox<>(new String[] { "Student", "Admin" });
        JTextField passwordField = new JTextField();

        // Add fields to dialog
        addDialog.add(new JLabel("Username:"));
        addDialog.add(usernameField);
        addDialog.add(new JLabel("Name:")); // Changed label to Name
        addDialog.add(nameField); // Changed to nameField
        addDialog.add(new JLabel("Email:"));
        addDialog.add(emailField);
        addDialog.add(new JLabel("Role:"));
        addDialog.add(roleComboBox);
        addDialog.add(new JLabel("Password:"));
        addDialog.add(passwordField);

        // Add buttons to save or cancel
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton
                .addActionListener(e -> addUser(usernameField.getText(), nameField.getText(), emailField.getText(),
                        (String) roleComboBox.getSelectedItem(), passwordField.getText(), addDialog));
        cancelButton.addActionListener(e -> addDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        addDialog.add(buttonPanel, BorderLayout.SOUTH);

        addDialog.setVisible(true);
    }

    // Add a new user to the database
    private void addUser(String username, String name, String email, String role, String password, JDialog dialog) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "INSERT INTO profiles (username, password, role, name, email) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password); // In real-world, hash the password before storing
            statement.setString(3, role);
            statement.setString(4, name); // Using name field
            statement.setString(5, email);

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                JOptionPane.showMessageDialog(this, "User added successfully!");
                loadUsers(); // Refresh the table with new data
                dialog.dispose(); // Close the dialog
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error adding user: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    // Open the dialog to edit an existing user's information
    private void openEditUserDialog() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);
            // Fetch user details from the database and populate the dialog fields
            // Similar to the add user dialog, but prepopulate the fields with existing
            // data.
            JOptionPane.showMessageDialog(this, "Editing user: " + username);
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to edit.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Delete a selected user from the database
    private void deleteUser() {
        int selectedRow = usersTable.getSelectedRow();
        if (selectedRow != -1) {
            String username = (String) tableModel.getValueAt(selectedRow, 0);

            int confirmation = JOptionPane.showConfirmDialog(this,
                    "Are you sure you want to delete user " + username + "?",
                    "Delete User", JOptionPane.YES_NO_OPTION);
            if (confirmation == JOptionPane.YES_OPTION) {
                try (Connection connection = DatabaseConnection.getConnection()) {
                    String query = "DELETE FROM profiles WHERE username = ?";
                    PreparedStatement statement = connection.prepareStatement(query);
                    statement.setString(1, username);

                    int rowsDeleted = statement.executeUpdate();
                    if (rowsDeleted > 0) {
                        JOptionPane.showMessageDialog(this, "User deleted successfully!");
                        loadUsers(); // Refresh the table after deletion
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                    JOptionPane.showMessageDialog(this, "Error deleting user: " + e.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Please select a user to delete.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        new UserManagementGUI();
    }
}