import java.util.Map;
import java.util.HashMap;

public class DoctorManagement {
    private Map<Integer, Doctor> doctors = new HashMap<>();

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.id, doctor);
    }

    public Doctor getDoctor(int id) {
        return doctors.get(id);
    }

    public void removeDoctor(int id) {
        doctors.remove(id);
    }

    public void displayAllDoctors() {
        doctors.forEach((id, doctor) -> System.out.println(doctor));
    }
}
