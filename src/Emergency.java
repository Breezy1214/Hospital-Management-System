import java.io.Serializable;

public class Emergency implements Serializable {
    private int patientId;
    private int severity;
    private String description;

    public Emergency(int patientId, int severity, String description) {
        this.patientId = patientId;
        this.severity = severity;
        this.description = description;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getSeverity() {
        return severity;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return "Emergency{" +
                "patientId=" + patientId +
                ", severity=" + severity +
                ", description='" + description + '\'' +
                '}';
    }
}