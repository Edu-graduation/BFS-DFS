package BFS_DFS;

import java.util.*;

public class Main {

    private static int goal; // The target value to search for

    public static Node buildTree() {
        Scanner input = new Scanner(System.in);

        // Create root node
        System.out.print("\n\nEnter the root value: ");
        int rootValue = input.nextInt();
        Node root = new Node(rootValue);

        // Queue for level-order tree construction
        Queue<Node> queue = new LinkedList<>();
        queue.add(root);

        int level = 1; // Tracks current tree depth

        // Process each node level by level
        while (!queue.isEmpty()) {
            Node currentNode = queue.poll();

            // Get number of children for current node
            System.out.print("How many children for node " + currentNode.getValue() + " (Level " + level + ")? ");
            int numChildren = input.nextInt();

            // Add children to current node
            for (int i = 0; i < numChildren; i++) {
                System.out.print("Enter child #" + (i + 1) + " of " + currentNode.getValue() + ": ");
                int childValue = input.nextInt();
                Node childNode = new Node(childValue);
                currentNode.addChild(childNode);
                queue.add(childNode); // Add child to queue for processing
            }

            level++;
        }

        // Get goal value after tree construction
        System.out.print("Enter the goal value to search for: ");
        goal = input.nextInt();

        return root;
    }

    // Implementation Breadth-First Strategy (BFS)
    public static void bfs(Node root) {

        Queue<Node> queue = new LinkedList<>(); // BFS queue
        queue.add(root);

        boolean found = false;
        StringBuilder result = new StringBuilder(); // Stores traversal path

        // Process nodes level by level
        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.append(current.getValue()).append(" ");

            // Check for goal match
            if (current.getValue() == goal) {
                found = true;
                break;
            }

            // Add children to queue
            for (Node child : current.getChildren()) {
                queue.add(child);
            }
        }

        // Output results
        System.out.println("\nBFS Result:");
        if (found) {
            System.out.println(result.toString().trim());
            System.out.println("Goal " + goal + " found in BFS.");
        } else {
            System.out.println("Goal " + goal + " not found in BFS.");
        }
    }

    // Implementation Depth-First Strategy (DFS)
    public static void dfs(Node root) {

        Stack<Node> stack = new Stack<>(); // DFS stack
        stack.push(root);

        boolean found = false;
        StringBuilder result = new StringBuilder(); // Stores traversal path

        // Process nodes depth-first
        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.append(current.getValue()).append(" ");

            if (current.getValue() == goal) {
                found = true;
                break;
            }

            // Add children in reverse order to maintain left-to-right traversal
            List<Node> children = current.getChildren();
            for (int i = children.size() - 1; i >= 0; i--) {
                stack.push(children.get(i));
            }
        }

        // Output results
        System.out.println("\nDFS Result:");
        if (found) {
            System.out.println(result.toString().trim());
            System.out.println("Goal " + goal + " found in DFS!");
        } else {
            System.out.println("Goal " + goal + " not found in DFS.");
        }
    }

    public static void main(String[] args) {

        Node root = buildTree(); // Build tree and perform searches

        bfs(root);

        dfs(root);

    }
}