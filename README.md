# Hospital Management System

## Overview

The Hospital Management System is a simple Java school project that allows administrators, doctors, nurses, receptionists, and patients to manage various hospital operations such as user management, appointments, patient records, and more.

## Features

- **Admin Operations**: Add, view, and remove users.
- **Doctor Operations**: View all patients, update patient diagnosis, and view appointments.
- **Nurse Operations**: View all patients, update patient status, and view bed assignments.
- **Receptionist Operations**: Schedule, view, and cancel appointments.
- **Patient Operations**: View personal details, view appointments, and update status.

## Getting Started

### Prerequisites

- Java Development Kit (JDK) 21 or higher
- IntelliJ IDEA or any other Java IDE

### Installation

1. Clone the repository:
    ```sh
    git clone https://github.com/Breezy1214/hospital-management-system.git
    ```
2. Open the project in IntelliJ IDEA.
3. Build the project to resolve dependencies.

### Running the Application

1. Run the `Main` class located in the `src` directory.
2. Follow the on-screen prompts to log in and perform various operations.

## Project Structure

- `src/Main.java`: Entry point of the application.
- `src/UserManager.java`: Manages user-related operations.
- `src/DoctorManager.java`: Manages doctor-related operations.
- `src/PatientManager.java`: Manages patient-related operations.
- `src/Doctor.java`: Represents a doctor.
- `src/Patient.java`: Represents a patient.
- `src/User.java`: Represents a generic user.

## Usage

### Admin Operations

- **Add User**: Add a new user by providing username, password, and role.
- **View All Users**: Display all users in the system.
- **Remove User**: Remove a user by username.

### Doctor Operations

- **View All Patients**: Display all patients.
- **Update Patient Diagnosis**: Update the diagnosis and priority of a patient.
- **View Appointments**: Display all appointments.

### Nurse Operations

- **View All Patients**: Display all patients.
- **Update Patient Status**: Update the status of a patient.
- **View Bed Assignments**: Display all bed assignments.

### Receptionist Operations

- **Schedule Appointment**: Schedule a new appointment.
- **View Appointments**: Display all appointments.
- **Cancel Appointment**: Cancel an appointment by ID.

### Patient Operations

- **View My Details**: Display personal details.
- **View My Appointments**: Display all appointments for the logged-in patient.
- **Update My Status**: Update the status of the logged-in patient.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request for review.

## License

This project is licensed under the MIT License. See the `LICENSE` file for details.
