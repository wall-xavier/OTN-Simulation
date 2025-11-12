package OTN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.io.IOException;
import javax.imageio.ImageIO;

public class WelcomeScreen extends JFrame {

    private static final int WIDTH = 800;
    private static final int HEIGHT = 600;

    public WelcomeScreen() {
        // 1. Frame Setup
        setTitle("OTN Simulation");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        // Use BorderLayout to position the image in the center and buttons at the bottom
        setLayout(new BorderLayout());
        
        // Ensure the frame starts centered on the screen
        setLocationRelativeTo(null);

        // 2. Custom Panel for Background Image
        ImagePanel imagePanel;
        
        try {
            URL imageUrl = getClass().getResource("background.jpg");
            
            if (imageUrl == null) {
                // Throw an exception if the resource is not found in the classpath
                throw new IOException("Resource 'background.jpg' not found in classpath. Ensure it is next to WelcomeScreen.class.");
            }

            // Load the image from the URL provided by the class loader
            Image backgroundImage = ImageIO.read(imageUrl);
            imagePanel = new ImagePanel(backgroundImage);

        } catch (IOException e) {
            // Fallback if image loading fails
            System.err.println("Could not load image. Displaying error message.");
            // Print stack trace for better debugging, showing why it failed
            e.printStackTrace(); 
            
            imagePanel = new ImagePanel(null); 
            imagePanel.setLayout(new GridBagLayout());
            JLabel errorLabel = new JLabel("Image Not Found. Check File Path and Permissions.");
            errorLabel.setForeground(Color.RED);
            errorLabel.setFont(new Font("Arial", Font.BOLD, 20));
            imagePanel.add(errorLabel);
        }

        add(imagePanel, BorderLayout.CENTER);

        // 3. Button Panel Setup (SOUTH)
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 40, 20)); // Center buttons with 40px spacing
        buttonPanel.setBackground(new Color(30, 41, 59));

        // 4. Create and Configure Buttons
        JButton continueButton = new JButton("Continue");
        JButton quitButton = new JButton("Quit");

        // Style the buttons
        Font buttonFont = new Font("SansSerif", Font.BOLD, 18);
        continueButton.setFont(buttonFont);
        continueButton.setBackground(new Color(52, 211, 153));
        continueButton.setForeground(Color.BLACK);
        continueButton.setFocusPainted(false);
        continueButton.setPreferredSize(new Dimension(150, 45));
        quitButton.setFont(buttonFont);
        quitButton.setBackground(new Color(251, 113, 133));
        quitButton.setForeground(Color.BLACK);
        quitButton.setFocusPainted(false);
        quitButton.setPreferredSize(new Dimension(150, 45));

        // 5. Add Action Listeners
        continueButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new TerminalGUI().setVisible(true);
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Exit the application
                System.exit(0);
            }
        });

        // 6. Add components to the panel and panel to the frame
        buttonPanel.add(continueButton);
        buttonPanel.add(quitButton);
        add(buttonPanel, BorderLayout.SOUTH);

        // Make the frame visible
        setVisible(true);
    }

    /**
     * Custom JPanel implementation to draw a background image that scales to fit the panel size.
     */
    private class ImagePanel extends JPanel {
        private Image img;

        public ImagePanel(Image img) {
            this.img = img;
            // Set preferred size if no image is loaded, just in case
            if (img == null) {
                setPreferredSize(new Dimension(WIDTH, HEIGHT - 100)); 
            }
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            if (img != null) {
                // Draw the image, scaling it to fill the entire panel size
                g.drawImage(img, 0, 0, this.getWidth(), this.getHeight(), this);
            } else {
                // Draw a fallback background if the image failed to load
                g.setColor(Color.LIGHT_GRAY);
                g.fillRect(0, 0, getWidth(), getHeight());
            }
        }
    }
}
