import java.util.LinkedList;
import java.util.List;

public class Patient extends Person {
    long id;
    String diagnosis;
    List<String> treatmentHistory;

    public Patient(long id, String firstName, String lastName, int age, String diagnosis) {
        super(firstName, lastName, age);
        this.id = id;
        this.diagnosis = diagnosis;
        this.treatmentHistory = new LinkedList<>();
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
                ", diagnosis='" + diagnosis + '\'' +
                ", treatmentHistory=" + treatmentHistory +
                '}';
    }
}
