import java.io.Serializable;
import java.util.Comparator;
import java.util.PriorityQueue;

public class EmergencyManager implements Serializable {
    private PriorityQueue<Emergency> emergencyQueue;

    public EmergencyManager() {
        emergencyQueue = new PriorityQueue<>(new EmergencyComparator());
    }

    public void addEmergency(Emergency emergency) {
        emergencyQueue.add(emergency);
    }

    public Emergency processEmergency() {
        return emergencyQueue.poll();
    }

    public boolean hasEmergencies() {
        return !emergencyQueue.isEmpty();
    }

    private static class EmergencyComparator implements Comparator<Emergency>, Serializable {
        @Override
        public int compare(Emergency e1, Emergency e2) {
            return Integer.compare(e2.getSeverity(), e1.getSeverity());
        }
    }
}