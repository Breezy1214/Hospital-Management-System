import java.io.Serializable;
import java.util.Map;
import java.util.HashMap;
import java.util.ArrayList;

public class DoctorManager implements Serializable {
    private Map<Integer, Doctor> doctors;

    public DoctorManager() {
        this.doctors = new HashMap<>();
    }

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.getId(), doctor);
    }

    public Doctor getDoctor(int id) {
        return doctors.get(id);
    }

    public void removeDoctor(int id) {
        doctors.remove(id);
    }

    public void displayAllDoctors() {
        doctors.forEach((id, doctor) -> System.out.println(doctor.toString()));
    }

    public void updateDoctor(int id, Doctor updatedDoctor) {
        if (doctors.containsKey(id)) {
            doctors.put(id, updatedDoctor);
        } else {
            System.out.println("Doctor with ID " + id + " not found.");
        }
    }

    public ArrayList<TimeSlot> getAvailableHours(int id) {
        Doctor doctor = doctors.get(id);
        if (doctor != null) {
            return doctor.getAvailableHours();
        } else {
            System.out.println("Doctor with ID " + id + " not found.");
            return null;
        }
    }
}