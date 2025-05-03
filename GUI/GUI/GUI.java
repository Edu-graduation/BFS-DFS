package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

public class GUI extends JFrame {
    private JTextArea resultArea;       // Displays algorithm results and messages
    private JTextField rootField;       // Input field for root node value
    private JTextField goalField;       // Input field for search goal value
    private Node root;                  // Root node of the constructed tree

    public GUI() {
        initComponents();
    }

    // Initializes UI components 
    private void initComponents() {
        // window setup
        setTitle("BFS/DFS Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        rootField = new JTextField();
        goalField = new JTextField();

        // Add components to input panel
        inputPanel.add(new JLabel("Root Value:"));
        inputPanel.add(rootField);
        inputPanel.add(new JLabel("Goal Value:"));
        inputPanel.add(goalField);

        // Control Buttons
        JButton buildTreeButton = new JButton("Build Tree");
        JButton bfsButton = new JButton("Run BFS");
        JButton dfsButton = new JButton("Run DFS");

        // Button panel with flow layout
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(buildTreeButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));

        // Add components to main frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Window settings
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Attach event handlers
        buildTreeButton.addActionListener(this::buildTreeAction);
        bfsButton.addActionListener(this::runBFS);
        dfsButton.addActionListener(this::runDFS);
    }

    // Build tree from user input
    private void buildTreeAction(ActionEvent e) {
        try {
            String rootValue = rootField.getText().trim();  
            if(rootValue.isEmpty()) throw new Exception();
            this.root = buildTreeInteractive(rootValue);
            resultArea.append("Tree built successfully!\n");
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid root value!");
        }
    }

    // Interactive tree builder using dialogs
    private Node buildTreeInteractive(String rootValue) {
        Node root = new Node(rootValue);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            // Ask for number of children
            String input = JOptionPane.showInputDialog(
                this, "How many children for node " + current.getValue() + "?", "Children Input",
                JOptionPane.QUESTION_MESSAGE
            );

            if (input == null) break;

            try {
                int numChildren = Integer.parseInt(input);
                for (int i = 0; i < numChildren; i++) {
                    // Get child value
                    String childValue = JOptionPane.showInputDialog(
                        this,"Enter child #" + (i + 1) + " of " + current.getValue() + ":", "Child Input",
                        JOptionPane.QUESTION_MESSAGE).trim();
                    
                    if(childValue.isEmpty()) throw new Exception();
                    Node child = new Node(childValue);
                    current.addChild(child);
                    queue.add(child);                   // Add to queue for processing
                }
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Skipping node...");
            }
        }
        return root;
    }

    // Run BFS Algorithm
    private void runBFS(ActionEvent e) {
        try {
            if (root == null){
                throw new Exception();
            } 

            String goal = goalField.getText().trim();  

            if(goal.isEmpty()){
                throw new Exception();
            } 
            String result = Main.bfs(root, goal);
            resultArea.append(result + "\n");
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input or missing tree!");
        }
    }

    // Run DFS Algorithm
    private void runDFS(ActionEvent e) {
        try {
            if (root == null){
                throw new Exception();
            } 

            String goal = goalField.getText().trim();

            if(goal.isEmpty()){
                throw new Exception();
            } 

            String result = Main.dfs(root, goal);
            resultArea.append(result + "\n");
            resultArea.setCaretPosition(resultArea.getDocument().getLength());
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Invalid input or missing tree!");
        }
    }

    public static void main(String[] args) {
        // Start GUI in event dispatch thread
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}