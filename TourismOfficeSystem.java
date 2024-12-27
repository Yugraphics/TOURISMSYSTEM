// Tourism Office Management System
// Project: Java Program for Tourism Office

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.table.DefaultTableModel;

// Parent Class: TourismEntity
abstract class TourismEntity {
    protected String name;

    public TourismEntity(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public abstract void displayInfo();
}

// Child Class: TouristSpot
class TouristSpot extends TourismEntity {
    private String description;
    private String location;
    private String openingHours;
    private String imagePath;

    public TouristSpot(String name, String description, String location, String openingHours, String imagePath) {
        super(name);
        this.description = description;
        this.location = location;
        this.openingHours = openingHours;
        this.imagePath = imagePath;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(String openingHours) {
        this.openingHours = openingHours;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public void displayInfo() {
        System.out.println("Name: " + name + ", Description: " + description + ", Location: " + location + ", Opening Hours: " + openingHours + ", Image Path: " + imagePath);
    }
}

// Main Program with Enhanced GUI
public class TourismOfficeSystem {
    private ArrayList<TouristSpot> touristSpots;
    private JFrame frame;

    public TourismOfficeSystem() {
        touristSpots = new ArrayList<>();
        initGUI();
    }

    private void initGUI() {
        // Set Look and Feel for a modern look
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create frame
        frame = new JFrame("Tourism Office Management System");
        frame.setSize(900, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Header Section
        JLabel headerLabel = new JLabel("Tabuelan Tourism Office Management", SwingConstants.CENTER);
        headerLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        headerLabel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
        frame.add(headerLabel, BorderLayout.NORTH);

        // Center Section with Table
        String[] columnNames = {"Name", "Description", "Location", "Opening Hours", "Image Path"};
        DefaultTableModel model = new DefaultTableModel(null, columnNames);
        JTable table = new JTable(model);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Footer Section with Buttons
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));

        JButton addButton = createButton("Add", "Add new tourist spot");
        JButton viewButton = createButton("View", "View tourist spots");
        JButton updateButton = createButton("Update", "Update tourist spot");
        JButton deleteButton = createButton("Delete", "Delete tourist spot");

        buttonPanel.add(addButton);
        buttonPanel.add(viewButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        frame.add(buttonPanel, BorderLayout.SOUTH);

        frame.setVisible(true);

        // Button Actions
        addButton.addActionListener(e -> addTouristSpot());
        viewButton.addActionListener(e -> viewTouristSpots(table, model));
        updateButton.addActionListener(e -> updateTouristSpot());
        deleteButton.addActionListener(e -> deleteTouristSpot());
    }

    private JButton createButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(100, 40));
        button.setToolTipText(tooltip);
        return button;
    }

    private void addTouristSpot() {
        JTextField nameField = new JTextField();
        JTextField descriptionField = new JTextField();
        JTextField locationField = new JTextField();
        JTextField hoursField = new JTextField();
        JTextField imagePathField = new JTextField();

        Object[] fields = {
            "Name:", nameField,
            "Description:", descriptionField,
            "Location:", locationField,
            "Opening Hours:", hoursField,
            "Image Path:", imagePathField
        };

        int option = JOptionPane.showConfirmDialog(frame, fields, "Add Tourist Spot", JOptionPane.OK_CANCEL_OPTION);
        if (option == JOptionPane.OK_OPTION) {
            if (validateFields(nameField, descriptionField, locationField, hoursField, imagePathField)) {
                TouristSpot spot = new TouristSpot(
                    nameField.getText(),
                    descriptionField.getText(),
                    locationField.getText(),
                    hoursField.getText(),
                    imagePathField.getText()
                );
                touristSpots.add(spot);
                JOptionPane.showMessageDialog(frame, "Tourist Spot added successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private boolean validateFields(JTextField... fields) {
        for (JTextField field : fields) {
            if (field.getText().trim().isEmpty()) {
                JOptionPane.showMessageDialog(frame, "All fields must be filled out.", "Validation Error", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return true;
    }

    private void viewTouristSpots(JTable table, DefaultTableModel model) {
        Object[][] data = new Object[touristSpots.size()][5];
        for (int i = 0; i < touristSpots.size(); i++) {
            TouristSpot spot = touristSpots.get(i);
            data[i][0] = spot.getName();
            data[i][1] = spot.getDescription();
            data[i][2] = spot.getLocation();
            data[i][3] = spot.getOpeningHours();
            data[i][4] = spot.getImagePath();
        }

        model.setDataVector(data, new Object[]{"Name", "Description", "Location", "Opening Hours", "Image Path"});
    }

    private void updateTouristSpot() {
        String name = JOptionPane.showInputDialog("Enter Name of Tourist Spot to Update:");
        for (TouristSpot spot : touristSpots) {
            if (spot.getName().equals(name)) {
                JTextField nameField = new JTextField(spot.getName());
                JTextField descriptionField = new JTextField(spot.getDescription());
                JTextField locationField = new JTextField(spot.getLocation());
                JTextField hoursField = new JTextField(spot.getOpeningHours());
                JTextField imagePathField = new JTextField(spot.getImagePath());

                Object[] fields = {
                    "Name:", nameField,
                    "Description:", descriptionField,
                    "Location:", locationField,
                    "Opening Hours:", hoursField,
                    "Image Path:", imagePathField
                };

                int option = JOptionPane.showConfirmDialog(frame, fields, "Update Tourist Spot", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.OK_OPTION) {
                    if (validateFields(nameField, descriptionField, locationField, hoursField, imagePathField)) {
                        spot.setName(nameField.getText());
                        spot.setDescription(descriptionField.getText());
                        spot.setLocation(locationField.getText());
                        spot.setOpeningHours(hoursField.getText());
                        spot.setImagePath(imagePathField.getText());
                        JOptionPane.showMessageDialog(frame, "Tourist Spot updated successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    }
                }
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Tourist Spot not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    private void deleteTouristSpot() {
        String name = JOptionPane.showInputDialog("Enter Name of Tourist Spot to Delete:");
        Iterator<TouristSpot> iterator = touristSpots.iterator();

        while (iterator.hasNext()) {
            TouristSpot spot = iterator.next();
            if (spot.getName().equals(name)) {
                iterator.remove();
                JOptionPane.showMessageDialog(frame, "Tourist Spot deleted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                return;
            }
        }
        JOptionPane.showMessageDialog(frame, "Tourist Spot not found!", "Error", JOptionPane.ERROR_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(TourismOfficeSystem::new);
    }
}
