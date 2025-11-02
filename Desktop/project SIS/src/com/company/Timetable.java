import javax.swing.*;

public class Timetable {
    public void displayTimetable() {
        String[][] schedule = {
                { "Monday - 08:25 AM - 09:20 AM", "Digital Image Processing", "Dr. DIPTI MISHRA" },
                { "Monday - 09:25 AM - 10:20 AM", "Computer Networks (Lecture)", "Mr. Mahesh Chowdary Kongara" },
                { "Monday - 10:35 AM - 11:30 AM", "Foundations of NLP", "Ms. Nidhi Goyal" },
                { "Monday - 01:35 PM - 02:30 PM", "Exploring the Tangible Heritage of India", "Dr. Paromita DasGupta" },
                { "Monday - 02:35 PM - 04:30 PM", "Programming Workshop", "Ms. Nartkannai K, Mrs. Swapna Satha" },
                { "Monday - 04:35 PM - 06:30 PM", "Operating Systems", "Mr. Arun Avinash Chauhan" },
                { "Tuesday - 08:25 AM - 09:20 AM", "Digital Image Processing", "Dr. DIPTI MISHRA" },
                { "Tuesday - 10:35 AM - 12:30 PM", "Computer Networks ", "Mr. Mahesh Chowdary Kongara" },
                { "Tuesday - 02:35 PM - 04:30 PM", "Object Oriented Programming", "Dr. Yayati Gupta" },
                { "Wednesday - 08:25 AM - 09:20 AM", "Operating Systems", "Mr. Arun Avinash Chauhan" },
                { "Wednesday - 09:25 AM - 10:20 AM", "Database Management System", "Prafulla Kalapatapu" },
                { "Wednesday - 10:35 AM - 11:30 AM", "Foundations of NLP", "Ms. Nidhi Goyal" },
                { "Wednesday - 11:35 AM - 12:30 PM", "Object Oriented Programming", "Dr. Yayati Gupta" },
                { "Thursday - 08:25 AM - 09:20 AM", "Operating Systems", "Mr. Arun Avinash Chauhan" },
                { "Thursday - 09:25 AM - 10:20 AM", "Database Management System", "Prafulla Kalapatapu" },
                { "Thursday - 10:35 AM - 11:30 AM", "Foundations of NLP", "Ms. Nidhi Goyal" },
                { "Thursday - 11:35 AM - 12:30 PM", "Object Oriented Programming", "Dr. Yayati Gupta" },
                { "Thursday - 03:35 PM - 05:30 PM", "Digital Image Processing", "Dr. DIPTI MISHRA" },
                { "Thursday - 05:35 PM - 06:30 PM", "Exploring the Tangible Heritage of India",
                        "Dr. Paromita DasGupta" },
                { "Friday - 08:25 AM - 09:20 AM", "Operating Systems", "Mr. Arun Avinash Chauhan" },
                { "Friday - 09:25 AM - 10:20 AM", "Database Management System", "Mrs.Prafulla Kalapatapu" },
                { "Friday - 10:35 AM - 12:30 PM", "Database Management System", "Mrs.Prafulla Kalapatapu" }
        };

        StringBuilder timetable = new StringBuilder("Day\tTime\tCourse\tFaculty\n");
        for (String[] row : schedule) {
            timetable.append(String.join("\t", row)).append("\n");
        }

        JTextArea textArea = new JTextArea(timetable.toString());
        textArea.setEditable(false);
        JOptionPane.showMessageDialog(null, new JScrollPane(textArea), "Timetable", JOptionPane.INFORMATION_MESSAGE);
    }
}