/*

This file is part of OTN-Simulation.

OTN-Simulation is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.

OTN-Simulation is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details.

You should have received a copy of the GNU General Public License along with OTN-Simulation. If not, see <https://www.gnu.org/licenses/>. 

*/

package OTN.Simulation;

import javax.swing.*;

import OTN.System.Devices.Cards.WSS.WSS;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Graphical User Interface (GUI) for the WSS Simulation.
 * Replaces the original terminal-based input/output with Swing components.
 */
public class WSS_Simulation extends JFrame {

    private static final int WIDTH = 900;
    private static final int HEIGHT = 700;

    // GUI Components
    private JPanel mainContentPanel; // Will hold the current step panel
    
    // Step 1: Input Fields
    private JTextField wavelengthField;
    private JTextField portNumField;
    
    // Step 2: Site Name Fields
    private List<JTextField> siteNameFields;
    private JTextField destinationPortField;

    // Data Storage
    private double wavelength;
    private int portCount;
    private String[] sites;
    private WSS demoWSS;

    public WSS_Simulation() {
        // Frame Setup
        setTitle("WSS Simulation - Optical Transport Network");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE); // Close only this window
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // Initialize main content area with dynamic panel switching
        mainContentPanel = new JPanel(new BorderLayout());
        add(mainContentPanel, BorderLayout.CENTER);

        // Header
        JLabel header = new JLabel("WSS Simulation: Setup and Results", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, 24));
        header.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        header.setOpaque(true);
        header.setBackground(new Color(183, 43, 38)); // Dark background
        header.setForeground(new Color(0,0,0)); // Light green text
        add(header, BorderLayout.NORTH);

        showStep1();

        setVisible(true);
    }

    /**
     * Shows the first panel: Wavelength and Port Count input.
     */
    private void showStep1() {
        JPanel step1Panel = createBasePanel("1. Network Parameters");
        step1Panel.setLayout(new GridBagLayout());
        GridBagConstraints gbc = createGBC();

        // Title/Description
        JLabel description = new JLabel("Enter the initial parameters to configure the WSS.");
        description.setFont(new Font("SansSerif", Font.ITALIC, 14));
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.insets.bottom = 25;
        step1Panel.add(description, gbc);

        // 1. Wavelength Input
        gbc.gridy = 1;
        gbc.gridwidth = 1;
        gbc.anchor = GridBagConstraints.EAST;
        gbc.insets.bottom = 5;
        step1Panel.add(new JLabel("Wavelength (1530nm-1565nm):"), gbc);
        wavelengthField = new JTextField(15);
        wavelengthField.setText("1550.0"); 
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        step1Panel.add(wavelengthField, gbc);

        // 2. Port Count Input
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.anchor = GridBagConstraints.EAST;
        step1Panel.add(new JLabel("Number of WSS Outgoing Ports (2-16):"), gbc);
        portNumField = new JTextField(15);
        portNumField.setText("8"); 
        gbc.gridx = 1;
        gbc.anchor = GridBagConstraints.WEST;
        step1Panel.add(portNumField, gbc);

        // Continue Button
        JButton nextButton = new JButton("Next: Define Site Names");
        nextButton.addActionListener(e -> processStep1());
        nextButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        nextButton.setBackground(new Color(52, 211, 153));
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets.top = 30;
        step1Panel.add(nextButton, gbc);

        updateContent(step1Panel);
    }

    /**
     * Processes Step 1 input and validates it before proceeding to Step 2.
     */
    private void processStep1() {
        try {
            wavelength = Double.parseDouble(wavelengthField.getText());
            portCount = Integer.parseInt(portNumField.getText());

            if (wavelength < 1530 || wavelength > 1565) {
                JOptionPane.showMessageDialog(this, "Wavelength must be between 1530nm and 1565nm (C-Band).", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }
            if (portCount < 2 || portCount > 20) { // Increased max ports slightly
                JOptionPane.showMessageDialog(this, "Port count must be between 2 and 20.", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            showStep2();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter valid numbers for wavelength and port count.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Shows the second panel: Site Name input and Destination Port input.
     */
    private void showStep2() {
        JPanel step2Panel = createBasePanel("2. Port Assignments and Routing");
        step2Panel.setLayout(new BorderLayout(10, 10));
        
        // Dynamic Site Name Fields Panel
        JPanel siteFieldsPanel = new JPanel(new GridLayout(0, 2, 10, 10)); // 0 rows, 2 cols
        siteNameFields = new ArrayList<>();
        sites = new String[portCount];
        
        siteFieldsPanel.setBorder(BorderFactory.createTitledBorder("Assign Site Names to Output Ports"));
        
        for (int i = 0; i < portCount; i++) {
            JLabel label = new JLabel(String.format("Port P%d Site Name:", i + 1));
            JTextField field = new JTextField(15);
            field.setText("Site " + (i + 1)); 
            siteNameFields.add(field);
            siteFieldsPanel.add(label);
            siteFieldsPanel.add(field);
        }
        
        JScrollPane scrollPane = new JScrollPane(siteFieldsPanel);
        scrollPane.setPreferredSize(new Dimension(WIDTH - 50, HEIGHT - 250));
        step2Panel.add(scrollPane, BorderLayout.CENTER);

        // Control Panel (Destination Port and Button)
        JPanel controlPanel = new JPanel(new BorderLayout());
        controlPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Destination Port Input
        JPanel destPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        destPanel.add(new JLabel("Destination Port (1 to " + portCount + "):"));
        destinationPortField = new JTextField(5);
        destinationPortField.setText("1"); 
        destPanel.add(destinationPortField);
        
        // Run Button
        JButton runButton = new JButton("Run WSS Simulation");
        runButton.addActionListener(e -> processStep2AndRun());
        runButton.setFont(new Font("SansSerif", Font.BOLD, 14));
        runButton.setBackground(new Color(52, 211, 153));
        
        controlPanel.add(destPanel, BorderLayout.CENTER);
        controlPanel.add(runButton, BorderLayout.EAST);
        
        step2Panel.add(controlPanel, BorderLayout.SOUTH);

        updateContent(step2Panel);
    }

    /**
     * Processes Step 2 input, initializes WSS, and displays results.
     */
    private void processStep2AndRun() {
        try {
            int destinationPort = Integer.parseInt(destinationPortField.getText());
            
            if (destinationPort < 1 || destinationPort > portCount) {
                 JOptionPane.showMessageDialog(this, "Destination port must be between 1 and " + portCount + ".", "Input Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Collect site names
            for (int i = 0; i < portCount; i++) {
                sites[i] = siteNameFields.get(i).getText().trim();
                if (sites[i].isEmpty()) sites[i] = "Unnamed Site " + (i + 1);
            }
            
            // 1. Create WSS object
            demoWSS = new WSS("Example", sites, portCount);

            // 2. Run simulation and show results
            showResults(destinationPort);

        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Please enter a valid number for the destination port.", "Input Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Displays the simulation results in a JTextArea.
     */
    private void showResults(int destinationPort) {
        JPanel resultsPanel = createBasePanel("3. Simulation Results");
        resultsPanel.setLayout(new BorderLayout());

        JTextArea outputArea = new JTextArea();
        outputArea.setEditable(false);
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        
        JScrollPane scrollPane = new JScrollPane(outputArea);
        resultsPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Build the output text using the original formatting
        StringBuilder sb = new StringBuilder();
        sb.append("-------------------------------------------------------\n");
        sb.append("              WSS Simulation Results                   \n");
        sb.append("-------------------------------------------------------\n\n");
        
        // WSS Layout (Now built using the local 'sites' array to avoid dependence
        // on WSS.getSiteLayout() return type)
        sb.append("WSS Site Layout:\n");
        for (int i = 0; i < sites.length; i++) {
            sb.append(String.format("Port P%d: %s\n", (i + 1), sites[i]));
        }
        sb.append("\n");

        sb.append(String.format("Routing %.2f nm to P%d (%s)\n\n", wavelength, destinationPort, sites[destinationPort - 1]));

        // Grating Dispersion Calculations
        sb.append("---- 1: Grating Dispersion (Separating Wavelengths) ----\n");
        sb.append(String.format("-> Input Grating Angle (Grating-In) >> %.2f degrees\n", demoWSS.INPUT_ANGLE_DEG));
        // Cast all return values used with %f to (double) for maximum robustness against IllegalFormatConversionException
        sb.append(String.format("-> Diffraction Angle (Grading-Out) >> %.2f degrees\n", (double) demoWSS.getThetaD(wavelength)));
        sb.append(String.format("-> LCoS landing position (x-position) >> %.2f mm\n\n", (double) demoWSS.getXPosition(wavelength)));

        // LCoS Steering Calculations
        sb.append("---- 2: LCoS Steering (Beam Routing) ----\n");
        sb.append(String.format("-> Target Output (relative to center) >> %.2f mm\n", (double) demoWSS.getTargetPosition(wavelength, destinationPort)));
        sb.append(String.format("-> Required LCoS Steering Angle >> %.2f degrees\n\n", (double) demoWSS.getThetaLcos(wavelength, destinationPort)));

        // LCoS Phase Modulation
        sb.append("---- 3: LCoS Phase Modulation ----\n");
        sb.append(String.format("-> The LCoS must create a phase ramp corresponding to a tilt of >> %.2f degrees\n", (double) demoWSS.getThetaLcos(wavelength,destinationPort)));
        sb.append(String.format("-> This translates to a phase shift of >> %.4f cycles (360) per LCoS pixel.\n", (double) demoWSS.requiredPhaseRampPerPixel(wavelength,destinationPort)));
        sb.append(String.format("-> This phase shift steers the %.2f nm channel from its landing spot to P%d.\n", wavelength, destinationPort));
        
        sb.append("\n-------------------------------------------------------\n");
        sb.append("                Simulation Complete                     \n");
        sb.append("-------------------------------------------------------\n");
        
        outputArea.setText(sb.toString());

        // Back button to rerun simulation
        JButton backButton = new JButton("Run New Simulation");
        backButton.addActionListener(e -> showStep1());
        
        JPanel buttonWrapper = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonWrapper.add(backButton);
        resultsPanel.add(buttonWrapper, BorderLayout.SOUTH);

        updateContent(resultsPanel);
    }
    
    // --- Utility Methods for GUI Layout ---

    private JPanel createBasePanel(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(title),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        return panel;
    }
    
    private GridBagConstraints createGBC() {
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.WEST;
        gbc.insets = new Insets(8, 8, 8, 8);
        return gbc;
    }

    private void updateContent(JPanel newPanel) {
        mainContentPanel.removeAll();
        mainContentPanel.add(newPanel, BorderLayout.CENTER);
        mainContentPanel.revalidate();
        mainContentPanel.repaint();
    }
    
    // Main method for testing (Optional, but useful if running this file directly)
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new WSS_Simulation());
    }
}
