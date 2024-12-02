import java.util.ArrayList;
import java.util.LinkedList;

public class Doctor extends Person {
    int id;
    String specialiazation;
    LinkedList<String> treatmentHistory;
    ArrayList<TimeSlot> availableHours;

    public Doctor(int id, String firstName, String lastName, int age, String specialization, ArrayList<TimeSlot> availableHours) {
        super(firstName, lastName, age);
        this.id = id;
        this.specialiazation = specialization;
        this.treatmentHistory = new LinkedList<>();
        this.availableHours = availableHours;
    }

    public void addTreatment(String treatment) {
        treatmentHistory.add(treatment);
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                ", specialiazation='" + specialiazation + '\'' +
                ", treatmentHistory=" + treatmentHistory +
                '}';
    }
}
