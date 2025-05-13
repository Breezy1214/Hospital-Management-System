import java.io.*;

public class FileHandler {
    private FileHandler() {
    }    private static void createDirectory() {
        File dir = new File("Database");

        try {
            if (!dir.exists()) {
                dir.mkdir();
            }
        } catch (SecurityException e) {
            // Log error silently
        }
    }    public static void saveState(Object data, String fileName) {
        createDirectory();

        try (FileOutputStream fileOut = new FileOutputStream(String.format("Database/%s.dat", fileName));
             ObjectOutputStream out = new ObjectOutputStream(fileOut)) {
            out.writeObject(data);
        } catch (IOException e) {
            // Log error silently
        }
    }

    public static Object loadState(String fileName) {
        createDirectory();

        try (FileInputStream fileIn = new FileInputStream(String.format("Database/%s.dat", fileName));
             ObjectInputStream in = new ObjectInputStream(fileIn);) {
            return in.readObject();
        } catch (IOException | ClassNotFoundException | SecurityException e) {
            return null;
        }
    }
}