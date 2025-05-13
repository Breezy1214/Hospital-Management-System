import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

public class AppointmentManager implements Serializable {
    private ArrayList<Appointment> appointments;
    public int appointmentIdCounter = 1;

    public AppointmentManager() {
        this.appointments = new ArrayList<>();
    }

    public int generateAppointmentId() {
        return appointmentIdCounter++;
    }    public void createAppointment(int appointmentId, int patientId, int doctorId, Date date, TimeSlot timeSlot) {
        if (!isConflict(doctorId, date, timeSlot)) {
            Appointment newAppointment = new Appointment(appointmentId, patientId, doctorId, date, timeSlot);
            appointments.add(newAppointment);
        } else {
            // Conflict detected, no appointment created
        }
    }

    public ArrayList<Appointment> getAppointments() {
        return appointments;
    }

    public void cancelAppointment(int appointmentId) {
        appointments.removeIf(appointment -> appointment.getAppointmentId() == appointmentId);
    }

    public Appointment getAppointment(int appointmentId) {
        for (Appointment appointment : appointments) {
            if (appointment.getAppointmentId() == appointmentId) {
                return appointment;
            }
        }
        return null;
    }

    private boolean isConflict(int doctorId, Date date, TimeSlot timeSlot) {
        for (Appointment appointment : appointments) {
            if (appointment.getDoctorId() == doctorId && appointment.getDate().equals(date) && appointment.getTimeSlot().overlaps(timeSlot)) {
                return true;
            }
        }
        return false;
    }

    public ArrayList<Appointment> getAllAppointments() {
        return new ArrayList<>(appointments);
    }
}