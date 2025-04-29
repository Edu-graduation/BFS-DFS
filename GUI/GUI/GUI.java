package GUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.LinkedList;
import java.util.Queue;

public class GUI extends JFrame {
    private JTextArea resultArea; // displays algorithm results
    private JTextField rootField; // input for root value
    private JTextField goalField; // input for goal value
    private Node root; // root of the tree

    public GUI() {
        initComponents();
    }

    // Initialize UI components
    private void initComponents() {
        // Window setup
        setTitle("BFS/DFS Solver");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        // Input Panel (Root & Goal)
        JPanel inputPanel = new JPanel(new GridLayout(3, 2, 5, 5));
        rootField = new JTextField();
        goalField = new JTextField();

        inputPanel.add(new JLabel("Root Value:"));
        inputPanel.add(rootField);
        inputPanel.add(new JLabel("Goal Value:"));
        inputPanel.add(goalField);

        // Buttons
        JButton buildTreeButton = new JButton("Build Tree");
        JButton bfsButton = new JButton("Run BFS");
        JButton dfsButton = new JButton("Run DFS");

        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(buildTreeButton);
        buttonPanel.add(bfsButton);
        buttonPanel.add(dfsButton);

        // Result Area
        resultArea = new JTextArea();
        resultArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(resultArea);
        scrollPane.setPreferredSize(new Dimension(400, 300)); // The size of the results area

        // Add components to frame
        add(inputPanel, BorderLayout.NORTH);
        add(buttonPanel, BorderLayout.CENTER);
        add(scrollPane, BorderLayout.SOUTH);

        // Window settings
        setSize(600, 500);
        setLocationRelativeTo(null);

        // Event listeners
        buildTreeButton.addActionListener(this::buildTreeAction);
        bfsButton.addActionListener(this::runBFS);
        dfsButton.addActionListener(this::runDFS);
    }

    // Build tree from user input
    private void buildTreeAction(ActionEvent e) {
        try {
            int rootValue = Integer.parseInt(rootField.getText());
            this.root = buildTreeInteractive(rootValue);
            resultArea.append("Tree built successfully!\n");
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    // Interactive tree builder using dialogs
    private Node buildTreeInteractive(int rootValue) {
        Node root = new Node(rootValue);
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node current = queue.poll();

            // Ask for number of children
            String input = JOptionPane.showInputDialog(
                    this, "How many children for node " + current.getValue() + "?", "Children Input",
                    JOptionPane.QUESTION_MESSAGE);

            if (input == null)
                break;

            try {
                int numChildren = Integer.parseInt(input);
                for (int i = 0; i < numChildren; i++) {
                    // Get child value
                    String childInput = JOptionPane.showInputDialog(
                            this, "Enter child #" + (i + 1) + " of " + current.getValue() + ":", "Child Input",
                            JOptionPane.QUESTION_MESSAGE);

                    int childValue = Integer.parseInt(childInput);
                    Node child = new Node(childValue);
                    current.addChild(child);
                    queue.add(child);
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid input! Skipping node...");
            }
        }
        return root;
    }

    // Run BFS algorithm
    private void runBFS(ActionEvent e) {
        try {
            if (root == null) {
                JOptionPane.showMessageDialog(this, "Build tree first!");
                return;
            }
            int goal = Integer.parseInt(goalField.getText());
            String result = Main.bfs(root, goal);
            resultArea.append("BFS Result:\n" + result + "\n\n");
            resultArea.setCaretPosition(resultArea.getDocument().getLength()); // scroll
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid goal value!");
        }
    }

    // Run DFS algorithm
    private void runDFS(ActionEvent e) {
        try {
            if (root == null) {
                JOptionPane.showMessageDialog(this, "Build tree first!");
                return;
            }
            int goal = Integer.parseInt(goalField.getText());
            String result = Main.dfs(root, goal);
            resultArea.append("DFS Result:\n" + result + "\n\n");
            resultArea.setCaretPosition(resultArea.getDocument().getLength()); // scroll
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Invalid goal value!");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            GUI gui = new GUI();
            gui.setVisible(true);
        });
    }
}