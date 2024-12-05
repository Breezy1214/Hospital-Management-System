import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class PatientManager implements Serializable {
    private Map<Integer, Patient> patients;

    public PatientManager() {
        patients = new HashMap<>();
    }

    public void addPatient(Patient patient) {
        patients.put(patient.getId(), patient);
    }

    public void updatePatient(int id, String diagnosis, int priority) {
        Patient patient = patients.get(id);
        if (patient != null) {
            patient.setDiagnosis(diagnosis);
            patient.setPriority(priority);
        }
    }

    public void updatePatientStatus(int id, String status) {
        Patient patient = patients.get(id);
        if (patient != null) {
            patient.setStatus(status);
        }
    }

    public Patient getPatient(int id) {
        return patients.get(id);
    }

    public void removePatient(int id) {
        patients.remove(id);
    }

    public boolean hasPatients() {
        return !patients.isEmpty();
    }

    public Map<Integer, Patient> getAllPatients() {
        return patients;
    }
}