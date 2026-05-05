import guru.nidi.graphviz.model.MutableNode;
import java.util.*;

abstract class GraphSearchTemplate {
    protected Graph graph;

    protected Set<MutableNode> visited = new HashSet<>();
    protected Map<MutableNode, MutableNode> parent = new HashMap<>();

    public GraphSearchTemplate(Graph graph) {
        this.graph = graph;
    }

    public Graph.Path search(MutableNode src, MutableNode dst) {
        init(src);

        graph.printVisitHistory(graph.buildPath(src, parent));

        while (!isEmpty()) {
            MutableNode current = getNext();

            if (current.equals(dst)) {
                System.out.println("Found target node: " + dst.name());
                return new Graph.Path(
                        graph.buildPath(current, parent).toArray(new MutableNode[0])
                );
            }

            List<MutableNode> neighbors = graph.getSortedNeighbors(current);
            processNeighbors(current, neighbors);
        }

        return new Graph.Path(new MutableNode[0]);
    }

    protected abstract void init(MutableNode src);
    protected abstract boolean isEmpty();
    protected abstract MutableNode getNext();
    protected abstract void addNode(MutableNode node);

    protected void processNeighbors(MutableNode current, List<MutableNode> neighbors) {
        for (MutableNode neighbor : neighbors) {
            if (!visited.contains(neighbor)) {
                visited.add(neighbor);
                parent.put(neighbor, current);
                addNode(neighbor);

                graph.printVisitHistory(graph.buildPath(neighbor, parent));
            }
        }
    }
}
