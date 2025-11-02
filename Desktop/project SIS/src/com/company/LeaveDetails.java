import javax.swing.*;
import java.awt.*;

public class LeaveDetails {
    private String applyDate;
    private String leaveDateTime;
    private String leaveReason;

    public void displayLeaveForm() {
        JFrame frame = new JFrame("Leave Details");
        frame.setSize(400, 300);
        frame.setLayout(new GridLayout(4, 2));

        JTextField applyDateField = new JTextField();
        JTextField leaveDateTimeField = new JTextField();
        JTextField leaveReasonField = new JTextField();

        frame.add(new JLabel("Apply Date (dd/mm/yyyy):"));
        frame.add(applyDateField);
        frame.add(new JLabel("Leave Date & Time (dd/mm/yyyy hh:mm):"));
        frame.add(leaveDateTimeField);
        frame.add(new JLabel("Reason:"));
        frame.add(leaveReasonField);

        JButton submitButton = new JButton("Submit");
        submitButton.addActionListener(e -> {
            applyDate = applyDateField.getText();
            leaveDateTime = leaveDateTimeField.getText();
            leaveReason = leaveReasonField.getText();

            JOptionPane.showMessageDialog(frame, "Leave applied successfully!" +
                    "\nApply Date: " + applyDate +
                    "\nLeave Date & Time: " + leaveDateTime +
                    "\nReason: " + leaveReason, "Leave Confirmation", JOptionPane.INFORMATION_MESSAGE);
        });

        frame.add(new JLabel());
        frame.add(submitButton);
        frame.setVisible(true);
    }
}