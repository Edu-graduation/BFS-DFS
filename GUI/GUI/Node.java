package GUI;
import java.util.ArrayList;
import java.util.List;

public class Node {
    private String value;  
    private List<Node> children;

    // Constructor initializes node with value and empty children list
    public Node(String value) {  
        this.value = value;
        this.children = new ArrayList<>();
    }

    // Add a child node
    public void addChild(Node child) {
        children.add(child);
    }

    // Get the value
    public String getValue() {  
        return value;
    }

    // Get the child node
    public List<Node> getChildren() {
        return children;
    }
}