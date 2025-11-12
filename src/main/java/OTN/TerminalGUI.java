package OTN;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.Date;

public class TerminalGUI extends JFrame implements ActionListener {

    private JTextArea outputArea; // Top: Results/Output display
    private JTextField inputField; // Bottom: Command input

    private final String PROMPT = "> ";
    private int commandCount = 1;

    public TerminalGUI() {
        
        setTitle("Java Split-Screen Terminal App");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null); 

      
        outputArea = new JTextArea();
        outputArea.setEditable(false);
     
        outputArea.setFont(new Font("Monospaced", Font.PLAIN, 14));
        outputArea.setLineWrap(true);
        outputArea.setWrapStyleWord(true);
        outputArea.setBackground(new Color(245, 245, 245)); 
        outputArea.setForeground(new Color(30, 30, 30)); 
        outputArea.setText("--- Welcome to the Simple Java Terminal App ---\n\nType 'help' and press Enter to begin.\n\n");

        inputField = new JTextField();
        inputField.setFont(new Font("Monospaced", Font.PLAIN, 14));
        inputField.addActionListener(this); 

      
        JScrollPane outputScrollPane = new JScrollPane(outputArea);
        outputScrollPane.setBorder(BorderFactory.createTitledBorder("Program Output / Results"));
        

        JPanel inputPanel = new JPanel(new BorderLayout(5, 5));
        inputPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); 
        
        JLabel promptLabel = new JLabel("Command " + PROMPT);
        promptLabel.setFont(new Font("SansSerif", Font.BOLD, 14));
        
        inputPanel.add(promptLabel, BorderLayout.WEST);
        inputPanel.add(inputField, BorderLayout.CENTER);

       
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, outputScrollPane, inputPanel);
        splitPane.setDividerLocation(0.8); 
        splitPane.setResizeWeight(1.0); 
        splitPane.setOneTouchExpandable(true);

      
        add(splitPane);


        outputScrollPane.setMinimumSize(new Dimension(100, 100));
        inputPanel.setMinimumSize(new Dimension(100, 50));

    
        SwingUtilities.invokeLater(() -> inputField.requestFocusInWindow());
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == inputField) {
            String command = inputField.getText().trim();
            inputField.setText(""); 

            if (!command.isEmpty()) {
                processCommand(command);
            }
        }
    }

    private void processCommand(String command) {
        String result;
        String lowerCommand = command.toLowerCase();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

   
        appendToOutput(String.format("[$%d] > %s\n", commandCount, command));

        switch (lowerCommand.split(" ")[0]) { 
            case "help":
                result = "Available commands:\n" +
                         "  help           - Show this help message.\n" +
                         "  date           - Display the current time.\n" +
                         "  clear          - Clear the output area.\n" +
                         "  calculate 2+2  - Simulate a simple calculation.\n" +
                         "  greet <name>   - Greet the specified name (e.g., 'greet World').\n" +
                         "  exit           - Close the application.";
                break;
            case "date":
                result = "Current time is: " + sdf.format(new Date());
                break;
            case "clear":
                outputArea.setText("");
                commandCount = 0;
                result = "Output cleared.";
                break;
            case "exit":
                result = "Exiting application...";
                appendToOutput(result + "\n");
                try {
                    Thread.sleep(300); 
                } catch (InterruptedException ex) {
                    Thread.currentThread().interrupt();
                }
                System.exit(0);
                return;
            case "greet":
                if (command.length() > 6) {
                    String name = command.substring(6).trim();
                    result = "Hello, " + name + "! Command processed successfully.";
                } else {
                    result = "Error: Please provide a name (e.g., 'greet Jane').";
                }
                break;
            case "calculate":
                result = "Input calculation: '" + command.substring(10).trim() + "'\n" +
                         "Simulated result: 42 (The Answer to Life, the Universe, and Everything).";
                break;
            default:
                result = "Error: Unknown command or invalid format: '" + command + "'. Type 'help' for options.";
                break;
        }

        appendToOutput("Result:\n" + result + "\n\n");
        commandCount++;
    }

    private void appendToOutput(String text) {
        outputArea.append(text);
        outputArea.setCaretPosition(outputArea.getDocument().getLength());
    }
}