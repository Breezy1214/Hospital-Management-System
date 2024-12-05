import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ShiftManager implements Serializable {
    private List<Shift> shifts;

    public ShiftManager() {
        this.shifts = new ArrayList<>();
    }

    public void scheduleShift(int shiftId, int employeeId, LocalDateTime startTime, LocalDateTime endTime) {
        Shift newShift = new Shift(shiftId, employeeId, startTime, endTime);
        shifts.add(newShift);
    }

    public void updateShift(int shiftId, LocalDateTime newStartTime, LocalDateTime newEndTime) {
        for (Shift shift : shifts) {
            if (shift.getShiftId() == shiftId) {
                shift.setStartTime(newStartTime);
                shift.setEndTime(newEndTime);
                break;
            }
        }
    }

    public List<Shift> viewShifts() {
        return shifts;
    }

    public Shift getShift(int shiftId) {
        for (Shift shift : shifts) {
            if (shift.getShiftId() == shiftId) {
                return shift;
            }
        }
        return null;
    }
}