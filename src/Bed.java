import java.io.Serializable;

// Bed.java
public class Bed implements Serializable {
    private int bedId;
    private boolean isOccupied;

    public Bed(int bedId) {
        this.bedId = bedId;
        this.isOccupied = false;
    }

    public int getBedId() {
        return bedId;
    }

    public boolean isOccupied() {
        return isOccupied;
    }

    public void setOccupied(boolean occupied) {
        isOccupied = occupied;
    }

    @Override
    public String toString() {
        return "Bed{" +
                "bedId=" + bedId +
                ", isOccupied=" + isOccupied +
                '}';
    }
}