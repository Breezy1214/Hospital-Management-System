import java.io.Serializable;

public class Patient extends User implements Serializable {
    private int id;
    private String diagnosis;
    private int priority;
    private String status;

    public Patient(int id, String username, String password, String diagnosis, int priority, String status) {
        super(username, password, Role.PATIENT);
        this.id = id;
        this.diagnosis = diagnosis;
        this.priority = priority;
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public String getDiagnosis() {
        return diagnosis;
    }

    public int getPriority() {
        return priority;
    }

    public String getStatus() {
        return status;
    }

    public void setDiagnosis(String diagnosis) {
        this.diagnosis = diagnosis;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Patient{" +
                "id=" + id +
                ", username='" + getUsername() + '\'' +
                ", diagnosis='" + diagnosis + '\'' +
                ", priority=" + priority +
                ", status='" + status + '\'' +
                '}';
    }
}