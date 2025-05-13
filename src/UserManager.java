import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class UserManager implements Serializable {
    private Map<String, User> users;

    public UserManager() {
        this.users = new HashMap<>();

        users.put("admin", new User("admin", "admin123", Role.ADMIN));
        users.put("doctor", new Doctor(1, "doctor", "doctor123", "Cardiology", new ArrayList<>()));
        users.put("nurse", new User("nurse", "nurse123", Role.NURSE));
        users.put("receptionist", new User("receptionist", "receptionist123", Role.RECEPTIONIST));
        users.put("patient", new Patient(1, "patient", "patient123", "Flu", 1, "Stable"));
    }

    public User createUser(String username, String password, Role role) {
        switch (role) {
            case ADMIN:
                return new User(username, password, Role.ADMIN);
            case DOCTOR:
                return new Doctor(1, username, password, "General", new ArrayList<>());
            case NURSE:
                return new User(username, password, Role.NURSE);
            case RECEPTIONIST:
                return new User(username, password, Role.RECEPTIONIST);
            case PATIENT:
                return new Patient(1, username, password, "Unknown", 1, "Unknown");
            default:
                throw new IllegalArgumentException("Invalid role: " + role);
        }
    }

    public boolean authenticate(String username, String password) {
        User user = users.get(username);
        return user != null && user.getPassword().equals(password);
    }

    public Role getUserRole(String username) {
        User user = users.get(username);
        return user != null ? user.getRole() : null;
    }
    
    public User getUserByUsername(String username) {
        return users.get(username);
    }

    public void addUser(User user) {
        users.put(user.getUsername(), user);
    }

    public void removeUser(String username) {
        users.remove(username);
    }

    public Collection<User> getAllUsers() {
        return users.values();
    }
}