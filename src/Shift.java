import java.io.Serializable;
import java.time.LocalDateTime;

public class Shift implements Serializable {
    private int shiftId;
    private int employeeId;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    public Shift(int shiftId, int employeeId, LocalDateTime startTime, LocalDateTime endTime) {
        this.shiftId = shiftId;
        this.employeeId = employeeId;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getShiftId() {
        return shiftId;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        return "Shift{" +
                "shiftId=" + shiftId +
                ", employeeId=" + employeeId +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                '}';
    }
}