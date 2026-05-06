import guru.nidi.graphviz.model.MutableNode;
import java.util.*;

public class DFS extends GraphSearchTemplate implements SearchStrategy {

    private Stack<MutableNode> stack = new Stack<>();

    public DFS(Graph graph) {
        super(graph);
    }

    @Override
    protected void init(MutableNode src) {
        stack.push(src);
        visited.add(src);
    }

    @Override
    protected boolean isEmpty() {
        return stack.isEmpty();
    }

    @Override
    protected MutableNode getNext() {
        return stack.pop();
    }

    @Override
    protected void addNode(MutableNode node) {
        stack.push(node);
    }

    @Override
    protected void processNeighbors(MutableNode current, List<MutableNode> neighbors) {
        // IMPORTANT: reverse to preserve original DFS alphabetical behavior
        Collections.reverse(neighbors);
        super.processNeighbors(current, neighbors);
    }

    @Override
    public Graph.Path search(MutableNode src, MutableNode dst) {
        return super.search(src, dst);
    }
}