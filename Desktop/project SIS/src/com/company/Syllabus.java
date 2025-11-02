import javax.swing.*;

public class Syllabus {
    public void displaySyllabus() {
        String[] subjects = {
                "Computer Networks", "DBMS", "Digital Image Processing",
                "NLP Foundations", "Operating Systems", "OOP", "Programming Workshop"
        };
        StringBuilder syllabus = new StringBuilder("Syllabus for Semester 5:\n");
        for (String subject : subjects)
            syllabus.append(subject).append("\n");
        JOptionPane.showMessageDialog(null, syllabus.toString(), "Syllabus", JOptionPane.INFORMATION_MESSAGE);
    }
}