import guru.nidi.graphviz.model.MutableNode;
import java.util.*;

public class BFS extends GraphSearchTemplate implements SearchStrategy {
    private Queue<MutableNode> queue = new LinkedList<>();

    public BFS(Graph graph) {
        super(graph);
    }

    @Override
    protected void init(MutableNode src) {
        queue.clear();
        visited.clear();
        parent.clear();

        queue.add(src);
        visited.add(src);
        parent.put(src, null);
    }

    @Override
    protected boolean isEmpty() {
        return queue.isEmpty();
    }

    @Override
    protected MutableNode getNext() {
        return queue.poll();
    }

    @Override
    protected void addNode(MutableNode node) {
        queue.add(node);
    }
}