import java.io.Serializable;
import java.util.Date;

public class Appointment implements Serializable {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private Date date;
    private TimeSlot timeSlot;

    public Appointment(int appointmentId, int patientId, int doctorId, Date date, TimeSlot timeSlot) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.date = date;
        this.timeSlot = timeSlot;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public Date getDate() {
        return date;
    }

    public TimeSlot getTimeSlot() {
        return timeSlot;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "appointmentId=" + appointmentId +
                ", patientId=" + patientId +
                ", doctorId=" + doctorId +
                ", date=" + date +
                ", timeSlot=" + timeSlot +
                '}';
    }
}