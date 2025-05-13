import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

/**
 * HospitalApp serves as the main GUI application for the Hospital Management System.
 * It provides a graphical interface for users to interact with the system.
 */
public class HospitalApp extends JFrame {
    private DoctorManager doctorManager;
    private PatientManager patientManager;
    private AppointmentManager appointmentManager;
    private EmergencyManager emergencyManager;
    private BedManager bedManager;
    private ShiftManager shiftManager;
    private UserManager userManager;
    
    private JPanel loginPanel;
    private JPanel mainPanel;
    private JTextField usernameField;
    private JPasswordField passwordField;
    private CardLayout cardLayout;
    private User currentUser;      public HospitalApp() {
        // Load all data
        loadAllData();
        
        // Initialize the application UI
        setTitle("Hospital Management System");
        setSize(900, 700);
        setMinimumSize(new Dimension(800, 600));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Add window listener to save data on close
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                saveAllData();
            }
        });
        
        // Initialize card layout for switching between panels
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);
        
        // Create and add login panel
        createLoginPanel();
        mainPanel.add(loginPanel, "Login");
        
        // Show login panel initially
        add(mainPanel);
        cardLayout.show(mainPanel, "Login");
    }
    
    /**
     * Creates the login panel with username and password fields
     */
    private void createLoginPanel() {
        loginPanel = new JPanel(new BorderLayout());
        
        // Create title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Hospital Management System");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        loginPanel.add(titlePanel, BorderLayout.NORTH);
        
        // Create form panel
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        formPanel.setBorder(BorderFactory.createEmptyBorder(50, 100, 50, 100));
        
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setPreferredSize(new Dimension(100, 25));
        usernameField = new JTextField();
        usernameField.setPreferredSize(new Dimension(200, 25));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 25));
        passwordField = new JPasswordField();
        passwordField.setPreferredSize(new Dimension(200, 25));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JPanel buttonPanel = new JPanel();
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> handleLogin());
        buttonPanel.add(loginButton);
        
        // Add components to form panel
        formPanel.add(Box.createVerticalGlue());
        formPanel.add(usernamePanel);
        formPanel.add(Box.createVerticalStrut(10));
        formPanel.add(passwordPanel);
        formPanel.add(Box.createVerticalStrut(20));
        formPanel.add(buttonPanel);
        formPanel.add(Box.createVerticalGlue());
        
        loginPanel.add(formPanel, BorderLayout.CENTER);
    }
      /**
     * Handles the login logic when the login button is clicked
     */    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username and password cannot be empty", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
          if (userManager.authenticate(username, password)) {
            currentUser = userManager.getUserByUsername(username);
            Role role = currentUser.getRole();
            
            // Create and show the appropriate dashboard based on role
            JPanel dashboard = createDashboardByRole(role);
            mainPanel.add(dashboard, role.toString());
            cardLayout.show(mainPanel, role.toString());
            
            // Clear login fields
            usernameField.setText("");
            passwordField.setText("");
        } else {
            JOptionPane.showMessageDialog(this, "Invalid username or password", 
                    "Login Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Creates the appropriate dashboard panel based on user role
     * @param role The user's role
     * @return A JPanel containing the role-specific dashboard
     */
    private JPanel createDashboardByRole(Role role) {
        JPanel panel = new JPanel(new BorderLayout());
        JPanel buttonPanel = new JPanel(new GridLayout(0, 1, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 50, 20, 50));
        
        // Header panel with welcome message and logout button
        JPanel headerPanel = new JPanel(new BorderLayout());
        JLabel welcomeLabel = new JLabel("Welcome, " + currentUser.getUsername() + " (" + role + ")");
        welcomeLabel.setFont(new Font("Arial", Font.BOLD, 18));
        welcomeLabel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 0));
        
        JButton logoutButton = new JButton("Logout");
        logoutButton.addActionListener(e -> {
            cardLayout.show(mainPanel, "Login");
            currentUser = null;
        });
        
        JPanel logoutPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        logoutPanel.add(logoutButton);
        
        headerPanel.add(welcomeLabel, BorderLayout.WEST);
        headerPanel.add(logoutPanel, BorderLayout.EAST);
        
        panel.add(headerPanel, BorderLayout.NORTH);
        
        // Add role-specific buttons
        switch (role) {
            case ADMIN:
                addAdminButtons(buttonPanel);
                break;
            case DOCTOR:
                addDoctorButtons(buttonPanel);
                break;
            case NURSE:
                addNurseButtons(buttonPanel);
                break;
            case RECEPTIONIST:
                addReceptionistButtons(buttonPanel);
                break;
            case PATIENT:
                addPatientButtons(buttonPanel);
                break;
        }
        
        JScrollPane scrollPane = new JScrollPane(buttonPanel);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        return panel;
    }
    
    /**
     * Adds admin-specific buttons to the given panel
     * @param panel The panel to add buttons to
     */
    private void addAdminButtons(JPanel panel) {
        JButton addUserButton = new JButton("Add User");
        addUserButton.addActionListener(e -> showAddUserDialog());
        
        JButton viewUsersButton = new JButton("View All Users");
        viewUsersButton.addActionListener(e -> showAllUsers());
        
        JButton removeUserButton = new JButton("Remove User");
        removeUserButton.addActionListener(e -> showRemoveUserDialog());
        
        panel.add(addUserButton);
        panel.add(viewUsersButton);
        panel.add(removeUserButton);
    }
    
    /**
     * Adds doctor-specific buttons to the given panel
     * @param panel The panel to add buttons to
     */
    private void addDoctorButtons(JPanel panel) {
        JButton viewPatientsButton = new JButton("View All Patients");
        viewPatientsButton.addActionListener(e -> showAllPatients());
        
        JButton updateDiagnosisButton = new JButton("Update Patient Diagnosis");
        updateDiagnosisButton.addActionListener(e -> showUpdateDiagnosisDialog());
        
        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.addActionListener(e -> showAllAppointments());
        
        panel.add(viewPatientsButton);
        panel.add(updateDiagnosisButton);
        panel.add(viewAppointmentsButton);
    }
    
    /**
     * Adds nurse-specific buttons to the given panel
     * @param panel The panel to add buttons to
     */
    private void addNurseButtons(JPanel panel) {
        JButton viewPatientsButton = new JButton("View All Patients");
        viewPatientsButton.addActionListener(e -> showAllPatients());
        
        JButton updateStatusButton = new JButton("Update Patient Status");
        updateStatusButton.addActionListener(e -> showUpdateStatusDialog());
        
        JButton viewBedsButton = new JButton("View Bed Assignments");
        viewBedsButton.addActionListener(e -> showAllBeds());
        
        panel.add(viewPatientsButton);
        panel.add(updateStatusButton);
        panel.add(viewBedsButton);
    }
    
    /**
     * Adds receptionist-specific buttons to the given panel
     * @param panel The panel to add buttons to
     */
    private void addReceptionistButtons(JPanel panel) {
        JButton scheduleButton = new JButton("Schedule Appointment");
        scheduleButton.addActionListener(e -> showScheduleAppointmentDialog());
        
        JButton viewAppointmentsButton = new JButton("View Appointments");
        viewAppointmentsButton.addActionListener(e -> showAllAppointments());
        
        JButton cancelButton = new JButton("Cancel Appointment");
        cancelButton.addActionListener(e -> showCancelAppointmentDialog());
        
        panel.add(scheduleButton);
        panel.add(viewAppointmentsButton);
        panel.add(cancelButton);
    }
    
    /**
     * Adds patient-specific buttons to the given panel
     * @param panel The panel to add buttons to
     */
    private void addPatientButtons(JPanel panel) {
        JButton viewDetailsButton = new JButton("View My Details");
        viewDetailsButton.addActionListener(e -> {
            if (currentUser instanceof Patient) {
                showPatientDetails((Patient) currentUser);
            }
        });
          JButton viewAppointmentsButton = new JButton("View My Appointments");
        viewAppointmentsButton.addActionListener(e -> {
            if (currentUser instanceof Patient) {
                showPatientAppointments(((Patient) currentUser).getId());
            }
        });
        
        JButton updateStatusButton = new JButton("Update My Status");
        updateStatusButton.addActionListener(e -> {
            if (currentUser instanceof Patient) {
                showUpdateMyStatusDialog((Patient) currentUser);
            }
        });
        
        panel.add(viewDetailsButton);
        panel.add(viewAppointmentsButton);
        panel.add(updateStatusButton);
    }
      // Dialog and display methods for each functionality
    
    private void showAddUserDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField usernameField = new JTextField(20);
        JPasswordField passwordField = new JPasswordField(20);
        JComboBox<Role> roleComboBox = new JComboBox<>(Role.values());
        
        JPanel usernamePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setPreferredSize(new Dimension(100, 25));
        usernamePanel.add(usernameLabel);
        usernamePanel.add(usernameField);
        
        JPanel passwordPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setPreferredSize(new Dimension(100, 25));
        passwordPanel.add(passwordLabel);
        passwordPanel.add(passwordField);
        
        JPanel rolePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel roleLabel = new JLabel("Role:");
        roleLabel.setPreferredSize(new Dimension(100, 25));
        rolePanel.add(roleLabel);
        rolePanel.add(roleComboBox);
        
        panel.add(usernamePanel);
        panel.add(passwordPanel);
        panel.add(rolePanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add User", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            Role role = (Role) roleComboBox.getSelectedItem();
            
            if (username.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Username and password cannot be empty", 
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            
            // Create the user based on role
            User newUser;
            switch (role) {
                case DOCTOR:
                    showAddDoctorDialog(username, password);
                    return;
                case PATIENT:
                    showAddPatientDialog(username, password);
                    return;
                default:
                    newUser = userManager.createUser(username, password, role);
                    userManager.addUser(newUser);
                    JOptionPane.showMessageDialog(this, "User added successfully", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }
    
    private void showAddDoctorDialog(String username, String password) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField doctorIdField = new JTextField(20);
        JTextField specializationField = new JTextField(20);
        
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Doctor ID:");
        idLabel.setPreferredSize(new Dimension(100, 25));
        idPanel.add(idLabel);
        idPanel.add(doctorIdField);
        
        JPanel specPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel specLabel = new JLabel("Specialization:");
        specLabel.setPreferredSize(new Dimension(100, 25));
        specPanel.add(specLabel);
        specPanel.add(specializationField);
        
        panel.add(idPanel);
        panel.add(specPanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Doctor Details", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int doctorId = Integer.parseInt(doctorIdField.getText());
                String specialization = specializationField.getText();
                
                if (specialization.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Specialization cannot be empty", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                ArrayList<TimeSlot> availableHours = new ArrayList<>();
                Doctor doctor = new Doctor(doctorId, username, password, specialization, availableHours);
                doctorManager.addDoctor(doctor);
                userManager.addUser(doctor);
                
                JOptionPane.showMessageDialog(this, "Doctor added successfully", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid doctor ID. Please enter a valid number.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAddPatientDialog(String username, String password) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField patientIdField = new JTextField(20);
        JTextField diagnosisField = new JTextField(20);
        JTextField priorityField = new JTextField(20);
        JTextField statusField = new JTextField(20);
        
        JPanel idPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel idLabel = new JLabel("Patient ID:");
        idLabel.setPreferredSize(new Dimension(100, 25));
        idPanel.add(idLabel);
        idPanel.add(patientIdField);
        
        JPanel diagnosisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel diagnosisLabel = new JLabel("Diagnosis:");
        diagnosisLabel.setPreferredSize(new Dimension(100, 25));
        diagnosisPanel.add(diagnosisLabel);
        diagnosisPanel.add(diagnosisField);
        
        JPanel priorityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priorityLabel = new JLabel("Priority:");
        priorityLabel.setPreferredSize(new Dimension(100, 25));
        priorityPanel.add(priorityLabel);
        priorityPanel.add(priorityField);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("Status:");
        statusLabel.setPreferredSize(new Dimension(100, 25));
        statusPanel.add(statusLabel);
        statusPanel.add(statusField);
        
        panel.add(idPanel);
        panel.add(diagnosisPanel);
        panel.add(priorityPanel);
        panel.add(statusPanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Add Patient Details", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int patientId = Integer.parseInt(patientIdField.getText());
                String diagnosis = diagnosisField.getText();
                int priority = Integer.parseInt(priorityField.getText());
                String status = statusField.getText();
                
                if (diagnosis.isEmpty() || status.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Diagnosis and status cannot be empty", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Patient patient = new Patient(patientId, username, password, diagnosis, priority, status);
                patientManager.addPatient(patient);
                userManager.addUser(patient);
                
                JOptionPane.showMessageDialog(this, "Patient added successfully", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid ID or priority. Please enter valid numbers.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllUsers() {
        String[] columnNames = {"Username", "Role"};
        Object[][] data = new Object[userManager.getAllUsers().size()][2];
        
        int i = 0;
        for (User user : userManager.getAllUsers()) {
            data[i][0] = user.getUsername();
            data[i][1] = user.getRole();
            i++;
        }
        
        showDataInTable("All Users", columnNames, data);
    }
    
    private void showRemoveUserDialog() {
        String username = JOptionPane.showInputDialog(this, "Enter username to remove:");
        if (username != null && !username.isEmpty()) {
            userManager.removeUser(username);
            JOptionPane.showMessageDialog(this, "User removed successfully", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void showAllPatients() {
        String[] columnNames = {"ID", "Name", "Diagnosis", "Priority", "Status"};
        Object[][] data = new Object[patientManager.getAllPatients().size()][5];
        
        int i = 0;        for (Patient patient : patientManager.getAllPatients().values()) {
            data[i][0] = patient.getId();
            data[i][1] = patient.getUsername();
            data[i][2] = patient.getDiagnosis();
            data[i][3] = patient.getPriority();
            data[i][4] = patient.getStatus();
            i++;
        }
        
        showDataInTable("All Patients", columnNames, data);
    }
      private void showUpdateDiagnosisDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField patientIdField = new JTextField(20);
        JTextField diagnosisField = new JTextField(20);
        JTextField priorityField = new JTextField(20);
        
        JPanel patientIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setPreferredSize(new Dimension(100, 25));
        patientIdPanel.add(patientIdLabel);
        patientIdPanel.add(patientIdField);
        
        JPanel diagnosisPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel diagnosisLabel = new JLabel("New Diagnosis:");
        diagnosisLabel.setPreferredSize(new Dimension(100, 25));
        diagnosisPanel.add(diagnosisLabel);
        diagnosisPanel.add(diagnosisField);
        
        JPanel priorityPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel priorityLabel = new JLabel("New Priority:");
        priorityLabel.setPreferredSize(new Dimension(100, 25));
        priorityPanel.add(priorityLabel);
        priorityPanel.add(priorityField);
        
        panel.add(patientIdPanel);
        panel.add(diagnosisPanel);
        panel.add(priorityPanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Update Patient Diagnosis", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int patientId = Integer.parseInt(patientIdField.getText());
                String diagnosis = diagnosisField.getText();
                int priority = Integer.parseInt(priorityField.getText());
                
                if (diagnosis.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Diagnosis cannot be empty", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Patient patient = patientManager.getPatient(patientId);
                if (patient != null) {
                    patientManager.updatePatient(patientId, diagnosis, priority);
                    JOptionPane.showMessageDialog(this, "Patient diagnosis updated successfully", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Patient with ID " + patientId + " not found", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid patient ID or priority. Please enter valid numbers.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllAppointments() {
        String[] columnNames = {"ID", "Patient ID", "Doctor ID", "Date", "Time Slot"};
        Object[][] data = new Object[appointmentManager.getAllAppointments().size()][5];
        
        int i = 0;
        for (Appointment appointment : appointmentManager.getAllAppointments()) {
            data[i][0] = appointment.getAppointmentId();
            data[i][1] = appointment.getPatientId();
            data[i][2] = appointment.getDoctorId();
            data[i][3] = appointment.getDate();
            data[i][4] = appointment.getTimeSlot();
            i++;
        }
        
        showDataInTable("All Appointments", columnNames, data);
    }
      private void showUpdateStatusDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField patientIdField = new JTextField(20);
        JTextField statusField = new JTextField(20);
        
        JPanel patientIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setPreferredSize(new Dimension(100, 25));
        patientIdPanel.add(patientIdLabel);
        patientIdPanel.add(patientIdField);
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel statusLabel = new JLabel("New Status:");
        statusLabel.setPreferredSize(new Dimension(100, 25));
        statusPanel.add(statusLabel);
        statusPanel.add(statusField);
        
        panel.add(patientIdPanel);
        panel.add(statusPanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Update Patient Status", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int patientId = Integer.parseInt(patientIdField.getText());
                String status = statusField.getText();
                
                if (status.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Status cannot be empty", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                Patient patient = patientManager.getPatient(patientId);
                if (patient != null) {
                    patientManager.updatePatientStatus(patientId, status);
                    JOptionPane.showMessageDialog(this, "Patient status updated successfully", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Patient with ID " + patientId + " not found", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid patient ID. Please enter a valid number.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showAllBeds() {        String[] columnNames = {"Bed ID", "Occupied"};
        Object[][] data = new Object[bedManager.getAllBeds().size()][2];
        
        int i = 0;
        for (Bed bed : bedManager.getAllBeds()) {
            data[i][0] = bed.getBedId();
            data[i][1] = bed.isOccupied() ? "Yes" : "No";
            i++;
        }
        
        showDataInTable("All Beds", columnNames, data);
    }
      private void showScheduleAppointmentDialog() {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        
        JTextField patientIdField = new JTextField(20);
        JTextField doctorIdField = new JTextField(20);
        JTextField dateField = new JTextField(20);
        JTextField startTimeField = new JTextField(20);
        JTextField endTimeField = new JTextField(20);
        
        JPanel patientIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel patientIdLabel = new JLabel("Patient ID:");
        patientIdLabel.setPreferredSize(new Dimension(100, 25));
        patientIdPanel.add(patientIdLabel);
        patientIdPanel.add(patientIdField);
        
        JPanel doctorIdPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel doctorIdLabel = new JLabel("Doctor ID:");
        doctorIdLabel.setPreferredSize(new Dimension(100, 25));
        doctorIdPanel.add(doctorIdLabel);
        doctorIdPanel.add(doctorIdField);
        
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel dateLabel = new JLabel("Date (MM/dd/yyyy):");
        dateLabel.setPreferredSize(new Dimension(100, 25));
        datePanel.add(dateLabel);
        datePanel.add(dateField);
        
        JPanel startTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel startTimeLabel = new JLabel("Start Time (HH:mm):");
        startTimeLabel.setPreferredSize(new Dimension(100, 25));
        startTimePanel.add(startTimeLabel);
        startTimePanel.add(startTimeField);
        
        JPanel endTimePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel endTimeLabel = new JLabel("End Time (HH:mm):");
        endTimeLabel.setPreferredSize(new Dimension(100, 25));
        endTimePanel.add(endTimeLabel);
        endTimePanel.add(endTimeField);
        
        panel.add(patientIdPanel);
        panel.add(doctorIdPanel);
        panel.add(datePanel);
        panel.add(startTimePanel);
        panel.add(endTimePanel);
        
        int result = JOptionPane.showConfirmDialog(this, panel, "Schedule Appointment", 
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        
        if (result == JOptionPane.OK_OPTION) {
            try {
                int patientId = Integer.parseInt(patientIdField.getText());
                int doctorId = Integer.parseInt(doctorIdField.getText());
                String dateStr = dateField.getText();
                String startTimeStr = startTimeField.getText();
                String endTimeStr = endTimeField.getText();
                
                if (dateStr.isEmpty() || startTimeStr.isEmpty() || endTimeStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Date and time cannot be empty", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate patientId
                Patient patient = patientManager.getPatient(patientId);
                if (patient == null) {
                    JOptionPane.showMessageDialog(this, "Patient with ID " + patientId + " not found", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                // Validate doctorId
                Doctor doctor = doctorManager.getDoctor(doctorId);
                if (doctor == null) {
                    JOptionPane.showMessageDialog(this, "Doctor with ID " + doctorId + " not found", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
                
                try {
                    // Parse date
                    java.text.SimpleDateFormat dateFormat = new java.text.SimpleDateFormat("MM/dd/yyyy");
                    Date date = dateFormat.parse(dateStr);
                    
                    // Parse time
                    java.time.LocalTime startTime = java.time.LocalTime.parse(startTimeStr);
                    java.time.LocalTime endTime = java.time.LocalTime.parse(endTimeStr);
                    
                    // Create TimeSlot
                    TimeSlot timeSlot = new TimeSlot(startTime, endTime);
                    
                    // Generate appointmentId and create appointment
                    int appointmentId = appointmentManager.generateAppointmentId();
                    appointmentManager.createAppointment(appointmentId, patientId, doctorId, date, timeSlot);
                    
                    JOptionPane.showMessageDialog(this, "Appointment scheduled successfully", 
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (java.text.ParseException e) {
                    JOptionPane.showMessageDialog(this, "Invalid date format. Please use MM/dd/yyyy", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                } catch (java.time.format.DateTimeParseException e) {
                    JOptionPane.showMessageDialog(this, "Invalid time format. Please use HH:mm (24-hour format)", 
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid patient ID or doctor ID. Please enter valid numbers.", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showCancelAppointmentDialog() {
        String appointmentIdStr = JOptionPane.showInputDialog(this, "Enter appointment ID to cancel:");
        if (appointmentIdStr != null && !appointmentIdStr.isEmpty()) {
            try {
                int appointmentId = Integer.parseInt(appointmentIdStr);
                appointmentManager.cancelAppointment(appointmentId);
                JOptionPane.showMessageDialog(this, "Appointment canceled successfully", 
                        "Success", JOptionPane.INFORMATION_MESSAGE);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid appointment ID", 
                        "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
      private void showPatientDetails(Patient patient) {
        JOptionPane.showMessageDialog(this, 
                "ID: " + patient.getId() + "\n" +
                "Name: " + patient.getUsername() + "\n" +
                "Diagnosis: " + patient.getDiagnosis() + "\n" +
                "Priority: " + patient.getPriority() + "\n" +
                "Status: " + patient.getStatus(),
                "Patient Details", JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void showPatientAppointments(int patientId) {
        String[] columnNames = {"ID", "Doctor ID", "Date", "Time Slot"};
        
        // Count patient appointments first
        int count = 0;
        for (Appointment appointment : appointmentManager.getAllAppointments()) {
            if (appointment.getPatientId() == patientId) {
                count++;
            }
        }
        
        Object[][] data = new Object[count][4];
        
        int i = 0;
        for (Appointment appointment : appointmentManager.getAllAppointments()) {
            if (appointment.getPatientId() == patientId) {
                data[i][0] = appointment.getAppointmentId();
                data[i][1] = appointment.getDoctorId();
                data[i][2] = appointment.getDate();
                data[i][3] = appointment.getTimeSlot();
                i++;
            }
        }
        
        showDataInTable("My Appointments", columnNames, data);
    }
      private void showUpdateMyStatusDialog(Patient patient) {
        String status = JOptionPane.showInputDialog(this, "Enter new status:");
        if (status != null && !status.isEmpty()) {
            patientManager.updatePatientStatus(patient.getId(), status);
            JOptionPane.showMessageDialog(this, "Status updated successfully", 
                    "Success", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    /**
     * Displays data in a table format
     * @param title The title of the dialog
     * @param columnNames Array of column names
     * @param data 2D array of data
     */
    private void showDataInTable(String title, String[] columnNames, Object[][] data) {
        JTable table = new JTable(data, columnNames);
        JScrollPane scrollPane = new JScrollPane(table);
        table.setFillsViewportHeight(true);
        
        JDialog dialog = new JDialog(this, title, true);
        dialog.setSize(600, 400);
        dialog.setLocationRelativeTo(this);
        dialog.add(scrollPane);
        dialog.setVisible(true);
    }    /**
     * Loads all data from files
     */
    public void loadAllData() {
        doctorManager = (DoctorManager) FileHandler.loadState("doctorManager");
        if (doctorManager == null) doctorManager = new DoctorManager();

        patientManager = (PatientManager) FileHandler.loadState("patientManager");
        if (patientManager == null) patientManager = new PatientManager();

        appointmentManager = (AppointmentManager) FileHandler.loadState("appointmentManager");
        if (appointmentManager == null) appointmentManager = new AppointmentManager();

        emergencyManager = (EmergencyManager) FileHandler.loadState("emergencyManager");
        if (emergencyManager == null) emergencyManager = new EmergencyManager();

        bedManager = (BedManager) FileHandler.loadState("bedManager");
        if (bedManager == null) bedManager = new BedManager();

        shiftManager = (ShiftManager) FileHandler.loadState("shiftManager");
        if (shiftManager == null) shiftManager = new ShiftManager();

        userManager = (UserManager) FileHandler.loadState("userManager");
        if (userManager == null) userManager = new UserManager();
    }
    
    /**
     * Saves all data to files
     */
    public void saveAllData() {
        FileHandler.saveState(doctorManager, "doctorManager");
        FileHandler.saveState(patientManager, "patientManager");
        FileHandler.saveState(appointmentManager, "appointmentManager");
        FileHandler.saveState(emergencyManager, "emergencyManager");
        FileHandler.saveState(bedManager, "bedManager");
        FileHandler.saveState(shiftManager, "shiftManager");
        FileHandler.saveState(userManager, "userManager");
    }
      /**
     * Main method to start the application
     */
    public static void main(String[] args) {
        // Set look and feel to system default
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        // Load data and start application on the EDT
        SwingUtilities.invokeLater(() -> {
            HospitalApp app = new HospitalApp();
            app.setVisible(true);
        });
    }
}
