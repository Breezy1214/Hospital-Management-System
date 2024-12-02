import java.util.Map;
import java.util.HashMap;

public class DoctorManagement {
    private Map<Integer, Doctor> doctors = new HashMap<>();

    public void addDoctor(Doctor doctor) {
        doctors.put(doctor.id, doctor);
    }
}
