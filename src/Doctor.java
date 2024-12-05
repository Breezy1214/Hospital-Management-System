import java.io.Serializable;
import java.util.ArrayList;
import java.util.LinkedList;

public class Doctor extends User implements Serializable {
    private int id;
    private String specialization;
    private LinkedList<String> treatmentHistory;
    private ArrayList<TimeSlot> availableHours;

    public Doctor(int id, String username, String password, String specialization, ArrayList<TimeSlot> availableHours) {
        super(username, password, Role.DOCTOR);
        this.id = id;
        this.specialization = specialization;
        this.treatmentHistory = new LinkedList<>();
        this.availableHours = availableHours;
    }

    public int getId() {
        return id;
    }

    public ArrayList<TimeSlot> getAvailableHours() {
        return availableHours;
    }

    public void addTreatment(String treatment) {
        treatmentHistory.add(treatment);
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "id=" + id +
                ", username='" + getUsername() + '\'' +
                ", specialization='" + specialization + '\'' +
                ", treatmentHistory=" + treatmentHistory +
                '}';
    }
}