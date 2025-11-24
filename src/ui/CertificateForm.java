package ui;

import controller.CertificateController;
import controller.CoursesController;
import controller.StudentController;
import database.CoursesDatabase;
import database.Database;
import models.Certificate;
import models.Student;
import models.UserSession;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class CertificateForm extends JFrame {
    private Student currentStudent;
    private StudentController studentController;
    private CertificateController certificateController;
    private JList<String> certificateList;
    private DefaultListModel<String> listModel;
    private String currentUserId = UserSession.getLoggedInUserId();
    CoursesDatabase coursesDB = new CoursesDatabase();
    Database usersDB = new Database();
    CoursesController coursesController = new CoursesController(coursesDB, usersDB);
    StudentController controller = new StudentController(coursesController, coursesDB, usersDB);

    public CertificateForm(Student student /*, StudentController studentController,
                             CertificateController certificateController*/) {
        this.certificateController = new CertificateController(coursesController ,studentController ,usersDB);
        this.currentStudent = controller.getStudentById(currentUserId);
//        this.studentController = studentController;

        //Certificate certificate = certificateController.getCertificate(currentUserId,)
        initializeFrame();
        setupUI();
        loadCertificates();
    }

    private void initializeFrame() {
        setTitle("My Certificates - Skill Forge");
        setSize(500, 400);
        setLocationRelativeTo(null); // Center the window
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setResizable(false);
    }

    private void setupUI() {
        // Main panel with BorderLayout
        setLayout(new BorderLayout(10, 10));

        // === NORTH: Title ===
        JLabel titleLabel = new JLabel("My Certificates", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        titleLabel.setForeground(Color.DARK_GRAY);
        add(titleLabel, BorderLayout.NORTH);

        // === CENTER: Certificate List ===
        listModel = new DefaultListModel<>();
        certificateList = new JList<>(listModel);
        certificateList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        certificateList.setFont(new Font("Arial", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(certificateList);
        scrollPane.setBorder(BorderFactory.createTitledBorder("Earned Certificates"));
        add(scrollPane, BorderLayout.CENTER);

        // === SOUTH: Buttons Panel ===
        JPanel buttonPanel = new JPanel(new FlowLayout());

        JButton viewButton = new JButton("View Details");
        JButton downloadButton = new JButton("Download JSON");
        JButton closeButton = new JButton("Close");

        // Add action listeners
        viewButton.addActionListener(e -> viewSelectedCertificate());
        downloadButton.addActionListener(e -> downloadSelectedCertificate());
        closeButton.addActionListener(e -> dispose()); // Close this window

        buttonPanel.add(viewButton);
        buttonPanel.add(downloadButton);
        buttonPanel.add(closeButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    private void loadCertificates() {
        listModel.clear();
        List<Certificate> certificates = currentStudent.getCertificates();

        if (certificates.isEmpty()) {
            listModel.addElement("No certificates earned yet.");
            return;
        }

        for (Certificate cert : certificates) {
            listModel.addElement(cert.getDisplayString());
        }
    }

    private void viewSelectedCertificate() {
        int selectedIndex = certificateList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a certificate to view.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        Certificate selectedCert = currentStudent.getCertificates().get(selectedIndex);

        // Show certificate details in a dialog
        JTextArea textArea = new JTextArea(selectedCert.getDetailedInfo());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));

        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 200));

        JOptionPane.showMessageDialog(this, scrollPane,
                "Certificate Details", JOptionPane.INFORMATION_MESSAGE);
    }

    private void downloadSelectedCertificate() {
        int selectedIndex = certificateList.getSelectedIndex();
        if (selectedIndex == -1) {
            JOptionPane.showMessageDialog(this,
                    "Please select a certificate to download.",
                    "No Selection",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }

        // For now, show a message that download is available
        Certificate selectedCert = currentStudent.getCertificates().get(selectedIndex);
        JOptionPane.showMessageDialog(this,
                "Download functionality would save: " + selectedCert.getCertificateId() + ".json\n" +
                        "File is already generated in the 'certificates' folder.",
                "Download Info",
                JOptionPane.INFORMATION_MESSAGE);
    }
}