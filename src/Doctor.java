import java.util.LinkedList;
import java.util.List;

public class Doctor extends Person {
    long id;
    String specialiazation;
    List<String> treatmentHistory;

    public Doctor(long id, String firstName, String lastName, int age, String specialization) {
        super(firstName, lastName, age);
        this.id = id;
        this.specialiazation = specialization;
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
                ", specialiazation='" + specialiazation + '\'' +
                ", treatmentHistory=" + treatmentHistory +
                '}';
    }
}
