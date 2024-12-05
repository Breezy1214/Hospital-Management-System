import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static DoctorManager doctorManager;
    private static PatientManager patientManager;
    private static AppointmentManager appointmentManager;
    private static EmergencyManager emergencyManager;
    private static BedManager bedManager;
    private static ShiftManager shiftManager;
    private static UserManager userManager = new UserManager();

    public static void loadAllData() {
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

    public static void saveAllData() {
        FileHandler.saveState(doctorManager, "doctorManager");
        FileHandler.saveState(patientManager, "patientManager");
        FileHandler.saveState(appointmentManager, "appointmentManager");
        FileHandler.saveState(emergencyManager, "emergencyManager");
        FileHandler.saveState(bedManager, "bedManager");
        FileHandler.saveState(shiftManager, "shiftManager");
        FileHandler.saveState(userManager, "userManager");
    }

    private static void adminOperations() throws NumberFormatException {
        while (true) {
            System.out.println("\nAdmin Operations:");
            System.out.println("1. Add User");
            System.out.println("2. View All Users");
            System.out.println("3. Remove User");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    addUser();
                    break;
                case 2:
                    viewAllUsers();
                    break;
                case 3:
                    removeUser();
                    break;
                case 4:
                    saveAllData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void addUser() {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();
        System.out.print("Enter role (ADMIN, DOCTOR, NURSE, RECEPTIONIST, PATIENT): ");
        Role role = Role.valueOf(scanner.nextLine().toUpperCase());

        User newUser;
        switch (role) {
            case DOCTOR:
                System.out.print("Enter doctor ID: ");
                int doctorId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter specialization: ");
                String specialization = scanner.nextLine();
                ArrayList<TimeSlot> availableHours = new ArrayList<>(); // Add logic to input available hours
                newUser = new Doctor(doctorId, username, password, specialization, availableHours);
                doctorManager.addDoctor((Doctor) newUser);
                break;
            case PATIENT:
                System.out.print("Enter patient ID: ");
                int patientId = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter diagnosis: ");
                String diagnosis = scanner.nextLine();
                System.out.print("Enter priority: ");
                int priority = Integer.parseInt(scanner.nextLine());
                System.out.print("Enter status: ");
                String status = scanner.nextLine();
                newUser = new Patient(patientId, username, password, diagnosis, priority, status);
                patientManager.addPatient((Patient) newUser);
                break;
            default:
                newUser = userManager.createUser(username, password, role);
        }

        userManager.addUser(newUser);
        System.out.println("User added successfully.");
    }

    private static void viewAllUsers() {
        System.out.println("\nAll Users:");
        for (User user : userManager.getAllUsers()) {
            System.out.println(user.toString());
        }
    }

    private static void removeUser() {
        System.out.print("Enter username to remove: ");
        String username = scanner.nextLine();
        userManager.removeUser(username);
        System.out.println("User removed successfully.");
    }

    private static void doctorOperations() {
        while (true) {
            System.out.println("\nDoctor Operations:");
            System.out.println("1. View All Patients");
            System.out.println("2. Update Patient Diagnosis");
            System.out.println("3. View Appointments");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewAllPatients();
                    break;
                case 2:
                    updatePatientDiagnosis();
                    break;
                case 3:
                    viewAppointments();
                    break;
                case 4:
                    saveAllData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewAllPatients() {
        System.out.println("\nAll Patients:");
        for (Patient patient : patientManager.getAllPatients().values()) {
            System.out.println(patient.toString());
        }
    }

    private static void updatePatientDiagnosis() {
        System.out.print("Enter patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new diagnosis: ");
        String diagnosis = scanner.nextLine();
        System.out.print("Enter priority: ");
        int priority = Integer.parseInt(scanner.nextLine());

        patientManager.updatePatient(patientId, diagnosis, priority);
        System.out.println("Patient diagnosis updated successfully.");
    }

    private static void viewAppointments() {
        System.out.println("\nAppointments:");
        for (Appointment appointment : appointmentManager.getAllAppointments()) {
            System.out.println(appointment);
        }
    }

    private static void nurseOperations() {
        while (true) {
            System.out.println("\nNurse Operations:");
            System.out.println("1. View All Patients");
            System.out.println("2. Update Patient Status");
            System.out.println("3. View Bed Assignments");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewAllPatients();
                    break;
                case 2:
                    updatePatientStatus();
                    break;
                case 3:
                    viewBedAssignments();
                    break;
                case 4:
                    saveAllData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void updatePatientStatus() {
        System.out.print("Enter patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();

        patientManager.updatePatientStatus(patientId, status);
        System.out.println("Patient status updated successfully.");
    }

    private static void viewBedAssignments() {
        System.out.println("\nBed Assignments:");
        for (Bed bed : bedManager.getAllBeds()) {
            System.out.println(bed);
        }
    }

    private static void receptionistOperations() {
        while (true) {
            System.out.println("\nReceptionist Operations:");
            System.out.println("1. Schedule Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Cancel Appointment");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    scheduleAppointment();
                    break;
                case 2:
                    viewAppointments();
                    break;
                case 3:
                    cancelAppointment();
                    break;
                case 4:
                    saveAllData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void scheduleAppointment() {
        System.out.print("Enter patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter doctor ID: ");
        int doctorId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter appointment date (YYYY-MM-DD): ");
        String dateString = scanner.nextLine();
        System.out.print("Enter appointment start time (HH:mm): ");
        String startTimeString = scanner.nextLine();
        System.out.print("Enter appointment end time (HH:mm): ");
        String endTimeString = scanner.nextLine();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Date date = dateFormat.parse(dateString);
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            LocalTime startTime = LocalTime.parse(startTimeString, timeFormatter);
            LocalTime endTime = LocalTime.parse(endTimeString, timeFormatter);
            TimeSlot timeSlot = new TimeSlot(startTime, endTime);
            int appointmentId = appointmentManager.generateAppointmentId();
            appointmentManager.createAppointment(appointmentId, patientId, doctorId, date, timeSlot);
            System.out.println("Appointment scheduled successfully.");
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use YYYY-MM-DD.");
        } catch (DateTimeParseException e) {
            System.out.println("Invalid time format. Please use HH:mm.");
        }
    }

    private static void cancelAppointment() {
        System.out.print("Enter appointment ID: ");
        int appointmentId = Integer.parseInt(scanner.nextLine());

        appointmentManager.cancelAppointment(appointmentId);
        System.out.println("Appointment canceled successfully.");
    }

    private static void patientOperations() {
        while (true) {
            System.out.println("\nPatient Operations:");
            System.out.println("1. View My Details");
            System.out.println("2. View My Appointments");
            System.out.println("3. Update My Status");
            System.out.println("4. Exit");

            System.out.print("Choose an option: ");
            int choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1:
                    viewMyDetails();
                    break;
                case 2:
                    viewMyAppointments();
                    break;
                case 3:
                    updateMyStatus();
                    break;
                case 4:
                    saveAllData();
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void viewMyDetails() {
        System.out.print("Enter your patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        Patient patient = patientManager.getPatient(patientId);
        if (patient != null) {
            System.out.println(patient);
        } else {
            System.out.println("Patient not found.");
        }
    }

    private static void viewMyAppointments() {
        System.out.print("Enter your patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        for (Appointment appointment : appointmentManager.getAllAppointments()) {
            if (appointment.getPatientId() == patientId) {
                System.out.println(appointment);
            }
        }
    }

    private static void updateMyStatus() {
        System.out.print("Enter your patient ID: ");
        int patientId = Integer.parseInt(scanner.nextLine());
        System.out.print("Enter new status: ");
        String status = scanner.nextLine();
        patientManager.updatePatientStatus(patientId, status);
        System.out.println("Status updated successfully.");
    }

    public static void main(String[] args) {
        try {
            loadAllData();

            System.out.print("Enter username: ");
            String username = scanner.nextLine();
            System.out.print("Enter password: ");
            String password = scanner.nextLine();

            if (userManager.authenticate(username, password)) {
                System.out.println("Authentication successful!");
                Role role = userManager.getUserRole(username);
                System.out.println("Welcome, " + role + "!");

                switch (role) {
                    case ADMIN:
                        adminOperations();
                        break;
                    case DOCTOR:
                        doctorOperations();
                        break;
                    case NURSE:
                        nurseOperations();
                        break;
                    case RECEPTIONIST:
                        receptionistOperations();
                        break;
                    case PATIENT:
                        patientOperations();
                        break;
                    default:
                        System.out.println("Invalid role.");
                }
            } else {
                System.out.println("Authentication failed. Please try again.");
            }

            saveAllData();
        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}