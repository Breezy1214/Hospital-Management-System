import javax.swing.*;
import java.awt.*;


public class BaseView extends JFrame  {
    protected JPanel mainPanel;
    protected JLabel titleLabel;
    
    public BaseView(String title) {
        // Set up the frame
        setTitle(title);
        setSize(800, 600);
        setLocationRelativeTo(null); // Center on screen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Create main panel with BorderLayout
        mainPanel = new JPanel(new BorderLayout());
        
        // Create title label
        titleLabel = new JLabel(title);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setHorizontalAlignment(JLabel.CENTER);
        titleLabel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Add title to the top of the panel
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Add panel to frame
        add(mainPanel);
    }
    
    /**
     * Creates a standardized button with consistent styling
     * @param text Button text
     * @return The created JButton
     */
    protected JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial", Font.PLAIN, 14));
        button.setFocusable(false);
        return button;
    }
    
    /**
     * Creates a standardized panel for buttons with consistent spacing
     * @return The created JPanel
     */
    protected JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(15, 0, 15, 0));
        return buttonPanel;
    }
    
    /**
     * Creates a standardized content panel with consistent styling
     * @return The created JPanel
     */
    protected JPanel createContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBorder(BorderFactory.createEmptyBorder(10, 30, 10, 30));
        return contentPanel;
    }
    
    /**
     * Creates a panel containing a label and text field with standard styling
     * @param labelText The label text
     * @return A JPanel containing the label and text field
     */
    protected JPanel createFormField(String labelText) {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JLabel label = new JLabel(labelText);
        label.setPreferredSize(new Dimension(150, 25));
        JTextField textField = new JTextField();
        textField.setPreferredSize(new Dimension(200, 25));
        panel.add(label);
        panel.add(textField);
        return panel;
    }
    
    /**
     * Displays an error message to the user
     * @param message The error message
     */
    protected void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
    
    /**
     * Displays an information message to the user
     * @param message The information message
     */
    protected void showInfo(String message) {
        JOptionPane.showMessageDialog(this, message, "Information", JOptionPane.INFORMATION_MESSAGE);
    }
}
