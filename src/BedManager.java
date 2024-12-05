import java.io.Serializable;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class BedManager implements Serializable {
    private ArrayList<Bed> beds;
    private PriorityQueue<Patient> patientQueue;

    public BedManager() {
        this.beds = new ArrayList<>();
        this.patientQueue = new PriorityQueue<>();
    }

    public void addBed(Bed bed) {
        beds.add(bed);
    }

    public void addPatient(Patient patient) {
        patientQueue.add(patient);
    }

    public ArrayList<Bed> getAllBeds() {
        return new ArrayList<>(beds);
    }

    public void assignBed() {
        for (Bed bed : beds) {
            if (!bed.isOccupied() && !patientQueue.isEmpty()) {
                Patient patient = patientQueue.poll();
                bed.setOccupied(true);
                System.out.println("Assigned bed " + bed.getBedId() + " to patient " + patient);
            }
        }
    }

    public void viewAvailableBeds() {
        for (Bed bed : beds) {
            if (!bed.isOccupied()) {
                System.out.println(bed);
            }
        }
    }

    public void releaseBed(int bedId) {
        for (Bed bed : beds) {
            if (bed.getBedId() == bedId) {
                bed.setOccupied(false);
                System.out.println("Bed " + bedId + " is now available.");
                break;
            }
        }
    }
}