import javax.swing.*;
import java.awt.*;
import java.sql.*;

public class FeeTransactionGUI extends JFrame {
    private final String username; // Username passed dynamically after login
    private int totalFee;
    private int paidFee;

    // Constructor to initialize with the dynamic username
    public FeeTransactionGUI(String username) {
        this.username = username;
        setTitle("Fee Transaction");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel to display fee details and allow payment
        JPanel feePanel = new JPanel();
        feePanel.setLayout(new BoxLayout(feePanel, BoxLayout.Y_AXIS));

        // Fetch fee details from the database
        fetchFeeDetails(feePanel);

        // Add the fee panel to the frame
        add(feePanel, BorderLayout.CENTER);

        setVisible(true);
    }

    // Method to display the FeeTransactionGUI window
    public void display() {
        this.setVisible(true);
    }

    private void fetchFeeDetails(JPanel feePanel) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT total_fee, paid_fee FROM fees WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);

            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                totalFee = resultSet.getInt("total_fee");
                paidFee = resultSet.getInt("paid_fee");
                int remainingFee = totalFee - paidFee;

                // Fee details label
                String feeDetails = "Total Fee: " + totalFee +
                        "\nPaid Fee: " + paidFee +
                        "\nRemaining Fee: " + remainingFee;

                JLabel feeDetailsLabel = new JLabel(feeDetails);
                feePanel.add(feeDetailsLabel);

                // If remaining fee > 0, allow payment
                if (remainingFee > 0) {
                    addPaymentSection(feePanel, remainingFee);
                } else {
                    JLabel noDueLabel = new JLabel("All fees are paid. No dues!");
                    feePanel.add(noDueLabel);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No fee details found for user.", "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error fetching fee details: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void addPaymentSection(JPanel feePanel, int remainingFee) {
        // Input for the payment amount
        JTextField paymentField = new JTextField(10);
        paymentField.setMaximumSize(new Dimension(200, 30));

        // Button to make payment
        JButton payButton = new JButton("Pay");

        payButton.addActionListener(e -> {
            try {
                int payment = Integer.parseInt(paymentField.getText());
                if (payment <= 0) {
                    JOptionPane.showMessageDialog(this, "Please enter a positive payment amount.", "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (payment > remainingFee) {
                    JOptionPane.showMessageDialog(this, "Payment exceeds remaining fee!", "Error",
                            JOptionPane.ERROR_MESSAGE);
                } else {
                    // Update the paid fee in the database
                    updatePaidFee(payment);

                    // Calculate the updated remaining fee
                    int newRemainingFee = remainingFee - payment;
                    paidFee += payment;

                    // Display the updated fee details
                    String updatedDetails = "Total Fee: " + totalFee +
                            "\nPaid Fee: " + paidFee +
                            "\nRemaining Fee: " + newRemainingFee;

                    // Update the fee details label
                    JLabel updatedFeeLabel = new JLabel(updatedDetails);
                    feePanel.add(updatedFeeLabel);
                    feePanel.revalidate(); // Refresh the panel to display updated info
                    JOptionPane.showMessageDialog(this, "Payment successful! Remaining Fee: " + newRemainingFee);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid payment amount!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        // Add payment components to the panel
        feePanel.add(new JLabel("Enter payment amount:"));
        feePanel.add(paymentField);
        feePanel.add(payButton);
    }

    // Update the paid fee in the database
    private void updatePaidFee(int payment) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String updateQuery = "UPDATE fees SET paid_fee = paid_fee + ? WHERE username = ?";
            PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
            updateStatement.setInt(1, payment);
            updateStatement.setString(2, username);
            int rowsUpdated = updateStatement.executeUpdate();
            if (rowsUpdated == 0) {
                JOptionPane.showMessageDialog(this, "Error updating fee details.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error updating fee in database: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

}