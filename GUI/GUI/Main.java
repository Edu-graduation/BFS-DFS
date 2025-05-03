package GUI;
import java.util.*;

public class Main {
    private static class NodePath {
        Node node;
        List<String> path;  

        NodePath(Node node, List<String> path) {
            this.node = node;
            this.path = new ArrayList<>(path);
            this.path.add(node.getValue());
        }
    }

    // Breadth-First Search implementation
    public static String bfs(Node root, String goal) {
        if (root == null) return "Tree not built yet!";     // Input validation
        
        List<String> visited = new ArrayList<>();
        List<String> finalPath = new ArrayList<>();
        Queue<NodePath> queue = new LinkedList<>();
        queue.add(new NodePath(root, new ArrayList<>()));
        boolean found = false;

        while (!queue.isEmpty()) {
            NodePath current = queue.poll();
            visited.add(current.node.getValue());   // Record the visited node

            // Check if current node is the goal
            if (current.node.getValue().equals(goal)) {  // Use .equals() for String comparison
                found = true;
                finalPath = current.path;
                break;
            }

            // Add all children to the queue
            for (Node child : current.node.getChildren()) {
                queue.add(new NodePath(child, current.path));
            }
        }

        return formatResult(visited, finalPath, found, goal, "BFS");
    }

    // Depth-First Search implementation
    public static String dfs(Node root, String goal) {  // Goal type changed
        if (root == null) return "Tree not built yet!";
        
        List<String> visited = new ArrayList<>();
        List<String> finalPath = new ArrayList<>();
        Stack<NodePath> stack = new Stack<>();
        stack.push(new NodePath(root, new ArrayList<>()));
        boolean found = false;

        while (!stack.isEmpty()) {
            NodePath current = stack.pop();
            visited.add(current.node.getValue());

            // Check if current node is the goal
            if (current.node.getValue().equals(goal)) {  // Use .equals()
                found = true;
                finalPath = current.path;
                break;
            }

            // Add all children to the stack in reverse order
            List<Node> children = current.node.getChildren();
            for (int i = children.size()-1; i >= 0; i--) {
                stack.push(new NodePath(children.get(i), current.path));
            }
        }

        return formatResult(visited, finalPath, found, goal, "DFS");
    }

    // Format results for display
    private static String formatResult(List<String> visited, List<String> path, 
                                      boolean found, String goal, String algorithm) {
        String status = found ? "Found" : "Not Found";
        return String.format(
            "[%s Results]\nVisited Nodes: %s\nFinal Path: %s\nGoal '%s': %s\n-----------------\n",
            algorithm,
            visited.toString(),
            found ? path.toString() : "N/A",
            goal,
            status
        );
    }
}