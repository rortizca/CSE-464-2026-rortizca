import guru.nidi.graphviz.model.MutableNode;
import java.util.*;

public class RandomWalk extends GraphSearchTemplate implements SearchStrategy {
    private Queue<MutableNode> queue = new LinkedList<>();
    private Random rand = new Random();

    public RandomWalk(Graph graph) {
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

    @Override
    protected void processNeighbors(MutableNode current, List<MutableNode> neighbors) {
        List<MutableNode> unvisited = new ArrayList<>();

        for (MutableNode n : neighbors) {
            if (!visited.contains(n)) {
                unvisited.add(n);
            }
        }

        if (unvisited.isEmpty()) {
            System.out.println("Reached dead end at node: " + current.name());
            return;
        }

        // pick ONE random neighbor
        MutableNode next = unvisited.get(rand.nextInt(unvisited.size()));

        visited.add(next);
        parent.put(next, current);
        addNode(next);

        graph.printVisitHistory(graph.buildPath(next, parent));
    }

    @Override
    public Graph.Path search(MutableNode src, MutableNode dst) {
        return super.search(src, dst);
    }
}