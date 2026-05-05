import guru.nidi.graphviz.model.MutableNode;
import java.util.*;

class BFS extends GraphSearchTemplate{
    private Queue<MutableNode> queue = new LinkedList<>();

    public BFS(Graph graph) {
        super(graph);
    }

    @Override
    protected void init(MutableNode src) {
        queue.add(src);
        visited.add(src);
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
