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
    }    public void assignBed() {
        for (Bed bed : beds) {
            if (!bed.isOccupied() && !patientQueue.isEmpty()) {
                Patient patient = patientQueue.poll();
                bed.setOccupied(true);
            }
        }
    }

    public void viewAvailableBeds() {
        // Return available beds without printing
    }

    public void releaseBed(int bedId) {
        for (Bed bed : beds) {
            if (bed.getBedId() == bedId) {
                bed.setOccupied(false);
                break;
            }
        }
    }
}