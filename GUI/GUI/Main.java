package GUI;

import java.util.*;

public class Main {

    // Breadth-First Search implementation
    public static String bfs(Node root, int goal) {
        if (root == null)
            return "Tree not built yet!"; // Input validation

        StringBuilder result = new StringBuilder(); // Store the traversal path
        Queue<Node> queue = new LinkedList<>(); // BFS queue (FIFO)
        queue.add(root);
        boolean found = false;

        while (!queue.isEmpty()) {
            Node current = queue.poll();
            result.append(current.getValue()).append(" "); // Record the visited node

            // Check if current node is the goal
            if (current.getValue() == goal) {
                found = true;
                break;
            }

            // Add all children to the queue
            for (Node child : current.getChildren()) {
                queue.add(child);
            }
        }

        return formatResult(result.toString(), found, goal, "BFS");
    }

    // Depth-First Search implementation
    public static String dfs(Node root, int goal) {
        if (root == null) {
            return "Tree not built yet!";
        }

        StringBuilder result = new StringBuilder(); // Store the traversal path
        Stack<Node> stack = new Stack<>(); // DFS stack (LIFO)
        stack.push(root);
        boolean found = false;

        while (!stack.isEmpty()) {
            Node current = stack.pop();
            result.append(current.getValue()).append(" ");

            // Check if current node is the goal
            if (current.getValue() == goal) {
                found = true;
                break;
            }

            // Add all children to the stack in reverse order
            List<Node> children = current.getChildren();
            for (int i = children.size() - 1; i >= 0; i--) {
                stack.push(children.get(i));
            }
        }

        return formatResult(result.toString(), found, goal, "DFS");
    }

    // Format results for display
    private static String formatResult(String path, boolean found, int goal, String algorithm) {
        String status = found ? "Found" : "Not Found";
        return String.format(
                "[%s Results]\nPath: %s\nGoal %d: %s\n-----------------\n",
                algorithm, path.trim(), goal, status);
    }
}